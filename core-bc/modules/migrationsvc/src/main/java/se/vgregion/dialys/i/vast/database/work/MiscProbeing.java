package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * After first deploy of the application - run this.
 */
public class MiscProbeing {

    public static void main(String[] args) throws IOException {
        ConnectionExt source = AbstractDatabaseCopy.getSourceConnection();

        List<Map<String, Object>> result = source.query(
                "select u.userName, r.roleName from users u " +
                        "join usersRoles ur on ur.usersId = u.id " +
                        "join roles r on r.id = ur.rolesId " +
                        "order by u.userName, r.roleName",
                0,
                100_000
        );

        print(result);

        if (true) return;

        List<Map<String, Object>> pagesRoles = source.query(
                "select * from rolesPages", 0, 100_000);

        print(pagesRoles);

        List<Map<String, Object>> pages = source.query(
                "select * from pages", 0, 100_000);

        print(pages);

        List<Map<String, Object>> sysDiagrams = source.query(
                "select * from sysDiagrams", 0, 100_000);

        print(sysDiagrams);
    }

    private static void print(List<Map<String, Object>> result) {
        for (Map<String, Object> item : result) {
            System.out.println(item);
        }
        System.out.println();
    }



}
