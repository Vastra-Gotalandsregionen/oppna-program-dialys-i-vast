package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ImportArtiklar {

    static ConnectionExt target;

    static {
        try {
            target = AbstractDatabaseCopy.getTargetConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*static List<Map<String, String>> importItems = initImportItems();*/

    /* private static Map<String, Map<String, Object>> artikelIndex = getArtikelIndexFromDatabase();*/

    private static List<Map<String, String>> getImportItems() {
        URL res = ImportArtiklar.class.getResource("Produktlista PD_HD_barn_2018-06-26.csv");
        List<Map<String, String>> items = null;
        try {
            items = toMaps(Paths.get(res.toURI()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Flikar flikar = new Flikar(target);
        Grupper grupper = new Grupper(target, flikar);

        int i = (int) target.query(
                "select max(id) from artikel", 0, 1
        ).get(0).values().iterator().next();

        i++;
        List<Map<String, String>> items = getImportItems();
        for (Map<String, String> item : items) {
            if ("Barn".equals(item.get("PD/HD"))) {
                continue;
            }
            Map<String, Object> flik = flikar.getFlik(item);
            Map<String, Object> grupp = grupper.getGrupp(item);
            // [maxantal, ordning, namn, gruppid, id, artnr, storlek, aktiv]
            // "PD/HD", "Grupp", "﻿ID", "Artikelbenämning inkl styrka", "Lev artikelnummer:", "Leverantör",
            // "Artikelnummer", "Beredningsform", "Undergrupp", "GruppID", "FörpackingsstorlekStorlek"
            Map<String, Object> entity = Data.toMap(
                    "maxantal", 100,
                    "ordning", i,
                    "namn", item.get("Artikelbenämning inkl styrka"),
                    "gruppid", grupp.get("id"),
                    "id", i,
                    "artnr", item.get("Artikelnummer"),
                    "storlek", item.get("FörpackingsstorlekStorlek"),
                    "aktiv", true
            );
            target.insert("artikel", entity);
            i++;
        }

        target.commit();
    }

    /*static Map<String, Map<String, Object>> getArtikelIndexFromDatabase() {
        List<Map<String, Object>> artiklar = target.query("select * from artikel", 0, 1_000_000);
        Map<String, Map<String, Object>> fromDbIndex = new HashMap<>();
        for (Map<String, Object> artikel : artiklar) {
            // System.out.println(artikel);
            String key = String.join(";", Arrays.asList(
                    (artikel.get("namn") + ""),
                    (artikel.get("artnr") + "").trim(),
                    (artikel.get("storlek") + "")));
            if (fromDbIndex.containsKey(key)) throw new RuntimeException(artikel.toString());
            fromDbIndex.put(key, artikel);
        }
        return fromDbIndex;
    }*/

    static List<Map<String, String>> toMaps(Path path) {
        try {
            List<Map<String, String>> result = new ArrayList<>();
            List<String> rows = new ArrayList<>(Files.readAllLines(path));
            String[] keys = toKeys(rows.remove(0));

            for (String row : rows) {
                String[] parts = row.split(Pattern.quote(";"));
                Map<String, String> map = new HashMap<>();
                int c = 0;
                for (String key : keys) {
                    if (parts.length <= c) break;
                    map.put(key, parts[c++]);
                }
                result.add(map);
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

/*    static Map<String, Map<String, String>> getFromFileIndex(List<Map<String, String>> importItems) {
        Map<String, Map<String, String>> result = new HashMap<>();
        for (Map<String, String> item : importItems) {
            String key = String.join(";", item.get("Artikelbenämning inkl styrka").trim(), item.get("Lev artikelnummer:").trim(), item.get("FörpackingsstorlekStorlek").trim());
            result.put(key, item);
        }
        return result;
    }*/


    static String[] toKeys(String line) {
        String[] result = line.split(Pattern.quote(";"));
        for (int i = 0; i < result.length; i++) {
            result[i] = result[i].replaceAll("\\(.*\\)", "").trim();
        }
        return result;
    }

}
