package se.vgregion.dialys.i.vast.database.work;

import org.junit.Ignore;
import org.junit.Test;
import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class MiscExperimentsTest {

    @Ignore
    @Test
    public void main() throws IOException {
        ConnectionExt con = AbstractDatabaseCopy.getTargetConnection();
        List<Map<String, Object>> lifra1s = con.query("select * from users where userName = ?", 0, 1, "lifra1");
        for (Map<String, Object> lifra1 : lifra1s) {
            System.out.println(lifra1);
        }
    }

}
