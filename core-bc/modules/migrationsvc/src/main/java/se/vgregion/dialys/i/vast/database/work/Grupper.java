package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grupper {

    private final ConnectionExt con;

    private final Flikar flikar;

    Map<String, Map<String, Object>> namedItems = new HashMap<>();

    public Grupper(ConnectionExt con, Flikar flikar) {
        this.con = con;
        this.flikar = flikar;
    }

    private int counter = 0;

    public Map<String, Object> getGrupp(Map<String, String> forThatArticle) {
        Map<String, Object> flik = flikar.getFlik(forThatArticle);

        String groupName = forThatArticle.get("Undergrupp");
        groupName = groupName.replaceAll("[^a-öA-Ö]", "");
        if (namedItems.containsKey(groupName)) {
            return namedItems.get(groupName);
        }

        List<Map<String, Object>> findings = con.query(
                "select * from grupp where titel = ? and flikid = ?",
                0, 10,
                groupName, flik.get("id")
        );

        if (!findings.isEmpty()) {
            Map<String, Object> item = findings.get(0);
            namedItems.put(groupName, item);
            return item;
        } else {
            Map<String, Object> item = Data.toMap(
                    "id", counter,
                    "ordning",counter++,
                    "titel", groupName
            );
            System.out.println("ny grupp " + item);
            con.insert("grupp", item);

            return item;
        }
    }

}
