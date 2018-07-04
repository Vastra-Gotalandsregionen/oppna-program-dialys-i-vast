package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;
import se.vgregion.arbetsplatskoder.db.migration.sql.meta.Schema;
import se.vgregion.arbetsplatskoder.db.migration.sql.meta.Table;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * After first deploy of the application - run this.
 */
public class MiscProbeing {

    public static void main(String[] args) throws IOException {
        ConnectionExt source = AbstractDatabaseCopy.getSourceConnection();

        for (Schema schema : source.getSchemas()) {
            Set<String> names = new HashSet<>();
            for (Table table : schema.getTables()) {
                names.add(table.getTableName());
            }
            System.out.println(schema.getName());
            System.out.println(" " + names);
        }

    }

    private static void print(List<Map<String, Object>> result) {
        for (Map<String, Object> item : result) {
            System.out.println(item);
        }
        System.out.println();
    }



}
