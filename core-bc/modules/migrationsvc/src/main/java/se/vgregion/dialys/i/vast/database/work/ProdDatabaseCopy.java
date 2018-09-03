package se.vgregion.dialys.i.vast.database.work;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Usage: Install a dump of the old db somewhere and notes its login. Likewise do the same with a another, new database.
 * Go to your home directory and create the file structure home/.app/dialys-i-vast/.
 * Then create 2 jdbc-files: <code> main.jdbc.properties </code> and <code> legacy.jdbc.properties </code>.
 * <p>
 * Inside <code> main.jdbc.properties </code> put the connection information to your New-Db, like this:
 * <code>
 * jdbc.url=jdbc:jtds:sqlserver://localhost:1433;databaseName=pd
 * jdbc.driver=org.postgresql.Driver
 * jdbc.user=sa
 * jdbc.password=scott
 * </code>
 * Do the corresponding thingy
 */
public class ProdDatabaseCopy extends AbstractDatabaseCopy {

    public static void main(String[] args) throws IOException, ParseException {
        /*List<Map<String, String>> patients = ImportArtiklar.toMaps(
                Paths.get(System.getProperty("user.home"), ".hotell", "ifeed", "Aktiva patienter DIalys i Väst_samlad lista.csv"));

        System.out.println("Item count " + patients.size());
        System.out.println("Keys " + patients.get(0).keySet());

        Set<String> pnrs = new HashSet<>();
        for (Map<String, String> patient : patients) {
            // System.out.println(patient);
            String pnr = patient.get("Personnummer");
            if (pnr.length() == 11) {
                pnr = "19".concat(pnr);
            }
            pnrs.add(String.format("'%s'", pnr));
        }

        System.out.println(pnrs);

        if (true) return;*/

        getApplicationInfo(); // You don't need this. Just to check if another property file is present.

        ProdDatabaseCopy dc = new ProdDatabaseCopy();
        dc.init();

        dc.dropAllTablesInTargetDatabase();
        dc.createTablesInTarget();
        dc.addAllTuplesFromLegacyIntoTarget();
        dc.connectUsersWithAnsvarig();

        // Todo: This should not be done when running job for production... comment away then.
        /*dc.obfuscateUserPasswords();*/

        dc.miscPatientsUpdates();
        /*dc.obfuscatePatients();*/
        dc.removeOrphanPdAndBestInfoAndBestPDRad();
        dc.insertObsoleteFlik();

        dc.fixShortPersonalNumbers();
        dc.putHyphenInPersonalNumbers();
        dc.fixJpaSequence();
    }

}
