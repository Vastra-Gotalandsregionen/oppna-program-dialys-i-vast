package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * After first deploy of the application - run this.
 */
public class PostDeployWork {

    static ConnectionExt target;

    static {
        try {
            target = AbstractDatabaseCopy.getTargetConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
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

        target.update("update users set sjukskoterska = true where userName in " +
                "( select u.userName from users u join ansvarig a on a.userName = u.userName)");

        target.commit();

        fixTypFieldOnPatient();
        putRolesIntoUsers();
    }

    protected static void fixTypFieldOnPatient() {
        // Set all the PD:s
        target.execute("update patient set typ = 'PD' where id in \n" +
                " (select distinct p.id\n" +
                "from mottagning m\n" +
                " join ansvarig a on a.mottagningid = m.id\n" +
                " join patient p on p.pas = a.id\n" +
                "where m.namn like 'PD %')");

        // Set all the HD:s
        target.execute("update patient set typ = 'HD' where id in \n" +
                " (select distinct p.id\n" +
                "from mottagning m\n" +
                " join ansvarig a on a.mottagningid = m.id\n" +
                " join patient p on p.pas = a.id\n" +
                "where m.namn like 'HD %')");

        target.commit();
    }

    public static void putRolesIntoUsers() throws IOException {
        ConnectionExt source = AbstractDatabaseCopy.getSourceConnection();

        List<Map<String, Object>> result = source.query(
                "select u.userName, r.roleName from users u " +
                        "join usersRoles ur on ur.usersId = u.id " +
                        "join roles r on r.id = ur.rolesId " +
                        "order by u.userName, r.roleName",
                0,
                100_000
        );

        target.execute("update users set admin = false, pharmaceut = false, sjukskoterska = false");
        target.commit();

        for (Map<String, Object> userAndRole : result) {
            if (userAndRole.get("roleName").equals("ssk")) {
                target.execute("update users set sjukskoterska = ? where userName = ?", true, userAndRole.get("userName"));
            } else if (userAndRole.get("roleName").equals("Sysadm")) {
                target.execute("update users set admin = ? where userName = ?", true, userAndRole.get("userName"));
            } else if (userAndRole.get("roleName").equals("Apoteket")) {
                target.execute("update users set pharmaceut = ? where userName = ?", true, userAndRole.get("userName"));
            } else if (userAndRole.get("roleName").equals("Testare")) {
                target.execute("update users set admin = false, pharmaceut = false, sjukskoterska = false where userName = ?", userAndRole.get("userName"));
            } else {
                throw new RuntimeException();
            }
        }

        target.commit();
    }


}
