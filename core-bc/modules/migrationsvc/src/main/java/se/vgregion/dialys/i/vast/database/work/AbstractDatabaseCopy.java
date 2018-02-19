package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;
import se.vgregion.arbetsplatskoder.db.migration.sql.meta.Schema;
import se.vgregion.arbetsplatskoder.db.migration.sql.meta.Table;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.nio.file.Files.readAllBytes;

public abstract class AbstractDatabaseCopy {

    protected ConnectionExt source;

    protected ConnectionExt target;

    public void init() {
        try {
            source = getSourceConnection();
            target = getTargetConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void addAllTuplesFromLegacyIntoTarget() {
        List<String> sourceTableNames = source.getAllTables().stream().map(t -> t.getTableName().toLowerCase()).collect(Collectors.toList());
        for (Schema schema : target.getSchemas("public")) {
            for (Table table : schema.getTables()) {
                if (!sourceTableNames.contains(table.getTableName())) {
                    continue;
                }
                List<Map<String, Object>> items = source.query(
                        "select * from " + table.getTableName(),
                        0,
                        1_000_000
                );
                System.out.println("Inserts " + items.size() + " into " + table.getTableName() + ".");
                if (items.isEmpty()) {
                    continue;
                }
                Map<String, Object> first = items.get(0);
                if (table.getPrimaries().size() == 1 && table.getPrimaries().get(0).getColumnName().toLowerCase().equals("id")
                        && !first.keySet().stream().map(k -> k.toLowerCase()).collect(Collectors.toSet()).contains("id")) {
                    insertWithBrandNewPrimaryKeys(table.getTableName(), items);
                } else {
                    if ("users".equals(table.getTableName().toLowerCase())) {
                        Map<String, Map<String, Object>> userName2user = new HashMap<>();
                        for (Map<String, Object> item : items) {
                            item.remove("ID");
                            userName2user.put(item.get("UserName").toString(), item);
                        }
                        insert(table.getTableName(), new ArrayList<>(userName2user.values()));
                    } else {
                        insert(table.getTableName(), items);
                    }

                }
                target.commit();
            }
        }
    }

    private void insertWithBrandNewPrimaryKeys(String table, List<Map<String, Object>> items) {
        int c = 0;
        for (Map<String, Object> item : items) {
            item.put("id", c);
            target.insert(table, item);
            c++;
        }
    }

    private void insert(String table, List<Map<String, Object>> items) {
        for (Map<String, Object> item : items) {
            target.insert(table, item);
        }
    }

    public void dropAllTablesInTargetDatabase() {
        for (Schema schema : target.getSchemas("public")) {
            for (Table table : schema.getTables()) {
                int r = target.update("drop table if exists " + table.getTableName() + " cascade");
                target.commit();
                System.out.println("Dropped " + table.getTableName() + " " + r);
            }
        }
    }

    static ConnectionExt getTargetConnection() throws IOException {
        Properties prop = getTargetJdbcInfo();
        return new ConnectionExt(
                prop.getProperty("jdbc.url"),
                prop.getProperty("jdbc.user"),
                prop.getProperty("jdbc.password"),
                prop.getProperty("jdbc.driver")
        );
    }

    static ConnectionExt getSourceConnection() throws IOException {
        Properties prop = getSourceJdbcInfo();
        return new ConnectionExt(
                prop.getProperty("jdbc.url"),
                prop.getProperty("jdbc.user"),
                prop.getProperty("jdbc.password"),
                prop.getProperty("jdbc.driver")
        );
    }

    static Properties getTargetJdbcInfo() throws IOException {
        Path mainJdbcPropPath = Paths.get(System.getProperty("user.home"), ".app", "dialys-i-vast");
        return getPropertiesOrFailWritingDefaultFileFirst(
                mainJdbcPropPath,
                "main.jdbc.properties",
                "jdbc.url=jdbc:postgresql://localhost:5432/dialys_i_vast\n" +
                        "jdbc.driver=org.postgresql.Driver\n" +
                        "jdbc.user=liferay\n" +
                        "jdbc.password=scott\n" +
                        "jdbc.database.platform=org.hibernate.dialect.PostgreSQL9Dialect");
    }

    static Properties getSourceJdbcInfo() throws IOException {
        Path mainJdbcPropPath = Paths.get(System.getProperty("user.home"), ".app", "dialys-i-vast");
        return getPropertiesOrFailWritingDefaultFileFirst(
                mainJdbcPropPath,
                "legacy.jdbc.properties",
                "jdbc.url=jdbc:jtds:sqlserver://localhost:1433;databaseName=pd\n" +
                        "jdbc.driver=org.postgresql.Driver\n" +
                        "jdbc.user=sa\n" +
                        "jdbc.password=scott\n");
    }

    static public Properties getApplicationInfo() {
        try {
            Path mainJdbcPropPath = Paths.get(System.getProperty("user.home"), ".app", "dialys-i-vast");
            return getPropertiesOrFailWritingDefaultFileFirst(
                    mainJdbcPropPath,
                    "application.properties",
                    "ldap.service.user.dn=\n" +
                            "ldap.service.user.password=\n" +
                            "ldap.search.base=\n" +
                            "ldap.url=\n" +
                            "\n" +
                            "impersonate.enabled=true\n" +
                            "\n" +
                            "jwt.sign.secret=\n" +
                            "\n" +
                            "default.admin.username=\n" +
                            "default.admin.encoded.password=\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Properties getPropertiesOrFailWritingDefaultFileFirst(Path atThatDirectory, String withFilename, String defaultFileContent) throws IOException {
        Path fullPath = Paths.get(atThatDirectory.toString(), withFilename);
        try {
            return getProperties(fullPath);
        } catch (Exception e) {
            if (!Files.exists(atThatDirectory)) {
                Files.createDirectories(atThatDirectory);
            }
            if (Files.isDirectory(fullPath)) {
                throw new RuntimeException("This path: %s ends with an directory. It´s supposed to be a *.property-file!".format(fullPath.toString()));
            }
            if (!Files.exists(fullPath)) {
                Files.createFile(fullPath);
                Files.write(fullPath, defaultFileContent.getBytes());
            }
            throw new RuntimeException(
                    "Could not, at first, find a file at " + fullPath + ". " +
                            "\n Created that and initialized it with some text.\n " +
                            " Go to it and fill in the correct information! \nThen try again"
            );
        }

    }

    public static Properties getProperties(Path fromThatFile) throws IOException {
        Properties r = new Properties();
        try (ByteArrayInputStream bais = new ByteArrayInputStream(readAllBytes(fromThatFile))) {
            r.load(bais);
        }
        return r;
    }

    void createTablesInTarget() {
        try {
            List<String> rows = Files.readAllLines(
                    Paths.get(AbstractDatabaseCopy.class.getResource("/dialys_i_vast.ddl").toURI()), Charset.defaultCharset());
            StringBuilder sb = new StringBuilder();
            for (String row : rows) {
                row = row.trim();
                if (!row.startsWith("--")) {
                    sb.append(row);
                    sb.append("\n");
                }
            }
            String[] statements = sb.toString().split(Pattern.quote(";"));
            for (String statement : statements) {
                if (statement.trim().startsWith("CREATE TABLE") || statement.trim().contains("PRIMARY KEY")) {
                    System.out.println(
                            "Runs " +
                                    statement.replace("\n", "").replace("\r", "") +
                                    " " + target.update(statement)
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void connectUsersWithAnsvarig() {
        List<Map<String, Object>> users = target.query("select * from users", 0, 100_000);
        List<Map<String, Object>> ansvariga = target.query("select * from ansvarig", 0, 100_000);
        Map<String, Map<String, Object>> namedUsers = new HashMap<>();
        for (Map<String, Object> user : users) {
            namedUsers.put((String) user.get("name"), user);
        }
        for (Map<String, Object> ansvarig : ansvariga) {
            Map<String, Object> user = namedUsers.get(ansvarig.get("namn"));
            if (user != null) {
                System.out.println("Sets user responsibility:s user-name for " + user.get("name"));
                target.update("update ansvarig set username = ? where namn = ?", user.get("username"), user.get("name"));
            }
        }
        target.commit();
    }

    public static void main(String[] args) throws IOException {
        ConnectionExt source = getSourceConnection();
        List<Map<String, Object>> items = source.query(
                "select * from (select username, count(*) as c from users group by username) t where t.c > 1",
                0,
                100
        );
        for (Map<String, Object> item : items) {
            System.out.println(item);
            List<Map<String, Object>> doublets = source.query(
                    "select * from users where username = ?",
                    0,
                    100,
                    item.get("username")
            );
            for (Map<String, Object> doublet : doublets) {
                System.out.println(" " + doublet);
            }
        }
    }

}
