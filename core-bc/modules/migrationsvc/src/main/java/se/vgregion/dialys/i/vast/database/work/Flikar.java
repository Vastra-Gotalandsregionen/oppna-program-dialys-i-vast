package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flikar {

    private final ConnectionExt con;

    Map<String, Map<String, Object>> namedItems = new HashMap<>();

    public Flikar(ConnectionExt con) {
        this.con = con;
    }

    int counter = 7;

    public Map<String, Object> getFlik(Map<String, String> forThatArticle) {
        String groupName = forThatArticle.get("Grupp");
        String typ = forThatArticle.get("PD/HD");
        if (namedItems.containsKey(groupName)) {
            return namedItems.get(groupName);
        }
        List<Map<String, Object>> findings = con.query(
                "select * from flik where titel = ? and typ = ?",
                0, 10,
                groupName, typ
        );

        if (!findings.isEmpty()) {
            Map<String, Object> item = findings.get(0);
            namedItems.put(groupName, item);
            return item;
        } else {

            Map<String, Object> item = Data.toMap(
                    "id", counter,
                    "ordning",counter++,
                    "titel", groupName,
                    "typ", typ,
                    "aktiv", true,
                    "flikrotid", typ
            );

            con.insert("flik", item);
            // con.commit();
            return item;
        }
    }

}
