package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static se.vgregion.dialys.i.vast.database.work.Csvs.toMaps;

public class ArtikelImport {

    static ConnectionExt target;

    static {
        try {
            target = AbstractDatabaseCopy.getTargetConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arga) {
        List<Map<String, String>> items = toMaps(Paths.get(System.getProperty("user.home"),"Downloads\\Dialys\\2018-08-27_Produktlista dialys i Väst_till malin.csv"));

        for (Map<String, String> item : items) {
            System.out.print(item);
            Integer gruppId = getGruppId(item.get("PD/HD"), item.get("Grupp"), item.get("Undergrupp"));
            Map<String, Object> ni = new HashMap<>();
            ni.put("id", artSeq);
            ni.put("artnr", item.get("Artikelnummer"));
            ni.put("gruppid", gruppId);
            ni.put("namn", item.get("Artikelbenämning inkl styrka"));
            ni.put("ordning", artSeq);
            ni.put("storlek", item.get("FörpackingsstorlekStorlek"));
            ni.put("aktiv", true);
            ni.put("maxantal", item.get(0));
            target.insert("artikel", ni);
            artSeq++;
        }
        target.commit();

    }

    static int  artSeq = 0;

    static int flikSeq = 0;

    static int gruppSeq = 0;

    static Integer getFlikId(String typ, String forThatName) {
        List<Map<String, Object>> stuff = target.query(
                "select * from flik where titel = ? and flikrotid = ?", 0, 1,
                forThatName,
                typ);

        if (stuff.isEmpty()) {
            Map<String, Object> ni = new HashMap<>();
            ni.put("ordning", flikSeq);
            ni.put("id", flikSeq++);
            ni.put("typ", typ);
            ni.put("flikrotid", typ);
            ni.put("aktiv", true);
            ni.put("titel", forThatName);
            target.insert("flik", ni);
            return flikSeq - 1;
        }

        return (Integer) stuff.get(0).get("id");
    }

    static Integer getGruppId(String typ, String flik, String gruppNamn) {
        int flikId = getFlikId(typ, flik);

        List<Map<String, Object>> stuff = target.query(
                "select * from grupp where titel = ? and flikid = ?", 0, 1,
                gruppNamn,
                flikId);

        if (stuff.isEmpty()) {
            Map<String, Object> ni = new HashMap<>();
            ni.put("ordning", gruppSeq);
            ni.put("id", gruppSeq++);
            ni.put("flikid", flikId);
            ni.put("titel", gruppNamn);
            target.insert("grupp", ni);
            return gruppSeq - 1;
        }

        return (Integer) stuff.get(0).get("id");
    }

}
