package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * After first deploy of the application - run this.
 */
public class PostDeployWork {

    public static void main(String[] args) throws IOException {
        ConnectionExt target = AbstractDatabaseCopy.getTargetConnection();
        target.execute("update flik set aktiv = true");
        target.commit();
        target.execute("update flik set aktiv = false where titel is null or titel = '' or titel = 'Obsoleta varor'");
        target.commit();

        target.update("update pd set typ = 'HD' where id in (\n" +
                "select p.id from pd p join patient pat on p.patientid = pat.id where pat.typ = 'HD'\n" +
                ")");
        target.commit();
    }

}
