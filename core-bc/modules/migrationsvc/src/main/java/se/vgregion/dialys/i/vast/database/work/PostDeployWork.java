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

        target.update("update patient set typ = 'PD' where id in \n" +
                "(select pat.id\n" +
                "from mottagning mot \n" +
                " join ansvarig ans on ans.mottagningid = mot.id\n" +
                " join patient pat on pat.pas = ans.id\n" +
                "where mot.namn like 'PD %')");

        target.update("update patient set typ = 'HD' where id in \n" +
                "(select pat.id\n" +
                "from mottagning mot \n" +
                " join ansvarig ans on ans.mottagningid = mot.id\n" +
                " join patient pat on pat.pas = ans.id\n" +
                "where mot.namn like 'HD %')");

        target.update("update pd set typ = 'HD' where id in (\n" +
                "select p.id from pd p join patient pat on p.patientid = pat.id where pat.typ = 'HD'\n" +
                ")");

        target.commit();
    }

}
