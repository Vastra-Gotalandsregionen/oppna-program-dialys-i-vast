package se.vgregion.dialys.i.vast.database.work;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Csvs {
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

    static String[] toKeys(String line) {
        String[] result = line.split(Pattern.quote(";"));
        for (int i = 0; i < result.length; i++) {
            result[i] = result[i].replaceAll("\\(.*\\)", "").trim();
        }
        return result;
    }
}
