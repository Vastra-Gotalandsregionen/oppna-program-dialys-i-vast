package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility
 */
public class Data {

    public Map<String, Object> toMap(Object... keysAndValues) {
        if (keysAndValues.length % 2 > 0) throw new RuntimeException();
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < keysAndValues.length; i += 2) {
            result.put((String) keysAndValues[i], keysAndValues[i + 1]);
        }
        return result;
    }

}
