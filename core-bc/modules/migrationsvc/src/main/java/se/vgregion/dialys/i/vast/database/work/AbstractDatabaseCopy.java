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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.nio.file.Files.readAllBytes;

public abstract class AbstractDatabaseCopy {

    public static final List<String> oldTables = initOldTables();

    private static List<String> initOldTables() {
        return Arrays.asList(("BestallningOLD\n" +
                "BestLakemedelRad\n" +
                "Dosering\n" +
                "Dtproperties\n" +
                "DtpropertiesPK\n" +
                "PatientLakemedel\n" +
                "Ticket\n" +
                "Placering\n" +
                "Lakemedel\n" +
                "Apotek\n" +
                "Dummy").split(Pattern.quote("\n")));
    }

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
                System.out.println("\nInserts " + items.size() + " into " + table.getTableName());
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
        int c = 1;
        for (Map<String, Object> item : items) {
            item.put("id", c);
            target.insert(table, item);
            if (c % 10000 == 0) {
                target.commit();
                System.out.print(" " + (c / 1000) + "t");
            }
            c++;
        }
    }

    private void insert(String table, List<Map<String, Object>> items) {
        int c = 1;
        try {
            for (Map<String, Object> item : items) {
                target.insert(table, item);
                if (c % 10000 == 0) {
                    target.commit();
                    System.out.print(" " + (c / 1000) + "t");
                }
                c++;
            }
            target.commit();
        } catch (Exception e) {
            System.out.println("Failed to insert. Stopped after " + c + " items.");
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
                target.update(
                        "update ansvarig set username = ? where lower(namn) = ?",
                        user.get("username"),
                        (user.get("name") + "").toLowerCase().trim()
                );
            }
        }
        target.commit();
    }

    public void obfuscateUserPasswords() {
        List<Map<String, Object>> users = target.query(
                "select * from users where password_encrypted_flag = ?",
                0,
                100_000,
                false
        );

        System.out.println("Found " + users.size() + " with unencrypted passwords. Starts encryption: ");

        Table table = target.getSchemas("public").get(0).getTable("users");

        String password = "test";
        // password = PasswordEncoder.getInstance().encodePassword(password);

        int i = 0;
        for (Map<String, Object> user : users) {
            /*String password = (String) user.get("password");
            password = PasswordEncoder.getInstance().encodePassword(password);*/

            Map<String, Object> where = new HashMap<>(), set = new HashMap<>();
            set.put("password", password);
            set.put("password_encrypted_flag", false);
            where.put("username", user.get("username"));
            target.update(table, set, where);

            if (i % 10 == 0) {
                System.out.print(" " + i);
            }
            if (i % 200 == 0) {
                System.out.println();
                System.out.print(" ");
            }
            i++;
        }
        target.commit();
    }

    public void miscPatientsUpdates() {
        target.execute("update users set typ = 'LA' where typ = 'La'");
        target.commit();
    }

    public void obfuscatePatients() throws IOException, ParseException {
        String string = "1700-01-01";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
        DateFormat tighter = new SimpleDateFormat("yyyyMMdd", Locale.GERMAN);
        Date date = format.parse(string);
        System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010

        List<Map<String, Object>> patients = target.query(
                "select * from patient",
                0,
                100_000
        );

        Table table = target.getSchemas("public").get(0).getTable("patient");

        // [fornamn, pas, epost, utdeltext, samtycke, efternamn, utdelvecka, adress, isdeleted,
        // mobil, postnr, pnr, telefon, id, utdeldag, portkod, postort]

        List<String> firstNames = new ArrayList<>();
        List<String> lastNames = new ArrayList<>();

        for (Map<String, Object> patient : patients) {
            firstNames.add((String) patient.get("fornamn"));
            lastNames.add((String) patient.get("efternamn"));
        }

        Collections.sort(firstNames);
        Collections.sort(lastNames);

        System.out.print("Obfuscating the patients (fields pnr, fornamn and efternamn) (" + patients.size() + "): ");
        int i = 0;
        for (Map<String, Object> patient : patients) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.YEAR, 1);
            c.add(Calendar.DAY_OF_MONTH, 10);
            date = c.getTime();

            String fooishPnr = (tighter.format(date) + "-" + String.format("%04d", i));
            Map<String, Object> where = new HashMap<>(), set = new HashMap<>();
            set.put("pnr", fooishPnr);
            set.put("fornamn", firstNames.get(i));
            set.put("efternamn", lastNames.get(i));
            where.put("id", patient.get("id"));
            target.update(table, set, where);
            i++;
            if (i % 10 == 0) {
                System.out.print(" " + i);
            }
            if (i % 100 == 0) {
                System.out.println();
            }
        }

        target.commit();

        // System.out.println(source.query("select * from usersroles where usersid = 50", 0 , 100));


        /*System.out.println(source.query("select * from users where id = 50", 0 , 100));

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

        ConnectionExt target = getTargetConnection();
        for (Schema schema : target.getSchemas("public")) {
            for (Table table : schema.getTables()) {
                System.out.println(table.getTableName());
            }
        }*/

    }

    public void removeOrphanPdAndBestInfoAndBestPDRad() {
        int r = target.update("delete from pd where patientid not in (select id from patient)");
        System.out.println("Deleted orphan pd, " + r + " items.");
        r = target.update("delete from bestinfo bi where bi.pdid not in (select id from pd)");
        System.out.println("Deleted orphan bestinfo, " + r + " items.");
        r = target.update("delete from bestpdrad brad where brad.bestid not in (select id from bestinfo)");
        System.out.println("Deleted orphan bestpdrad, " + r + " items.");

        target.update(
                "delete from BestPDRad where id in (" +
                        "select brad.id from BestPDRad brad, pdartikel part " +
                        "where brad.pdArtikelId = part.id " +
                        "and part.artikelid not in (select id from artikel)" +
                        ")");

        target.update("delete from pdartikel where artikelid not in (select id from artikel)");
        target.update("delete from pdArtikel where pdid not in (select id from pd)");

        target.commit();
    }

    public static void main(String[] args) throws IOException, ParseException {
        ConnectionExt connectionExt = new ConnectionExt(
                "jdbc:postgresql://localhost:5432/dialys_i_vast",
                "liferay",
                "liferay",
                org.postgresql.Driver.class.getName()
        );
        // fk_PDArtikel_Artikel
        List<Map<String, Object>> items = connectionExt.query(
                "select * from pdartikel where artikelid not in (select id from artikel)", 0, 1_000_000
        );

        for (Map<String, Object> item : items) {
            System.out.println(item);
        }

        System.out.println("Found " + items.size() + " items.");

        List<Map<String, Object>> bestPdRads = connectionExt.query(
                "select brad.id from BestPDRad brad, pdartikel part " +
                        "where brad.pdArtikelId = part.id " +
                        "and part.artikelid not in (select id from artikel)",
                0,
                1_000_000
        );
        for (Map<String, Object> bestPdRad : bestPdRads) {
            System.out.println(bestPdRad);
        }
        System.out.println("Antal bestpdrad " + bestPdRads.size());

        connectionExt.update(
                "delete from BestPDRad where id in (" +
                        "select brad.id from BestPDRad brad, pdartikel part " +
                        "where brad.pdArtikelId = part.id " +
                        "and part.artikelid not in (select id from artikel)" +
                        ")");

        connectionExt.update("delete from pdartikel where artikelid not in (select id from artikel)");
        connectionExt.commit();
    }

    protected void insertObsoleteFlik() {
        // public.flik{titel={Baxter}, ordning={1}, typ={PD}, id={1}}
        Map<String, Object> obsoleteFlik = new HashMap<>();
        obsoleteFlik.put("titel", "Obsoleta varor");
        obsoleteFlik.put("ordning", -1);
        obsoleteFlik.put("typ", "PD");
        obsoleteFlik.put("id", 99);
        target.insert("public.flik", obsoleteFlik);
        target.commit();
    }

    protected void fixJpaSequence() {
        try {
            target.execute("DROP SEQUENCE if exists public.hibernate_sequence");

            target.execute("CREATE SEQUENCE public.hibernate_sequence\n" +
                    "        INCREMENT 1\n" +
                    "        MINVALUE 1\n" +
                    "        MAXVALUE 9223372036854775807\n" +
                    "        START 207994\n" +
                    "        CACHE 1;\n" +
                    // "        ALTER TABLE public.hibernate_sequence;\n" +
                    "        ");

            target.commit();

            System.out.println(
                    "fixJpaSequence succeeded. The index public.hibernate_sequence start value is set to 207994."
            );
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(
                    "fixJpaSequence failed. Maybe because the index public.hibernate_sequence already exists."
            );
        }
    }

}
