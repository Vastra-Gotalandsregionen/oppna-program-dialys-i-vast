package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataCleaner {

    static ConnectionExt target;

    static {
        try {
            target = AbstractDatabaseCopy.getTargetConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        removeUnusedUsers();
        cleanThreeYearOldData();
        removeUnusedArtiklar();
    }

    private static void removeUnusedArtiklar() {
        System.out.println("Tar bort oanvända artiklar.");
        run("delete from artikel a where a.id not in (\n" +
                " select artikelid from pdartikel \n" +
                ")");
        System.out.println("Tar bort oanvända grupper.");
        run("delete from grupp where id not in (\n" +
                " select gruppid from artikel \n" +
                ")");
        System.out.println("Tar bort oanvända flikar.");
        run("delete from flik where id not in (\n" +
                "select distinct flikid from grupp\n" +
                ")");
        target.commit();
    }

    public static void removeUnusedUsers() {
        System.out.println("Ta bort oanvända användare.");
        run("delete from users where username not in (\n" +
                "  select username from anstallning \n" +
                ")");
        target.commit();
    }

    public static void cleanThreeYearOldData() {
        System.out.println("Tar bort från bestpdrad");
        run("delete from bestpdrad where bestid in (\n" +
                "select distinct brad.bestid from pd \n" +
                "left join pdartikel pda on (pda.pdid = pd.id)\n" +
                "left join bestinfo bi on (bi.pdid = pd.id)\n" +
                "left join bestpdrad brad on (brad.bestid = bi.id)\n" +
                "where pd.datum < (CURRENT_DATE - interval '3 year')\n" +
                ")");

        System.out.println("Tar bort från bestinfo");
        run("delete from bestinfo where id in (\n" +
                "select distinct bi.id from pd \n" +
                "left join pdartikel pda on (pda.pdid = pd.id)\n" +
                "left join bestinfo bi on (bi.pdid = pd.id)\n" +
                "where pd.datum < (CURRENT_DATE - interval '3 year')\n" +
                ")");

        System.out.println("tar bort från pdartikel");
        run("delete from pdartikel where id in (\n" +
                "select distinct pda.id from pd \n" +
                "left join pdartikel pda on (pda.pdid = pd.id)\n" +
                "where pd.datum < (CURRENT_DATE - interval '3 year')\n" +
                ")");

        System.out.println("Tar bort från pd");
        run("delete from pd where id in (\n" +
                "select distinct pd.id from pd \n" +
                "where pd.datum < (CURRENT_DATE - interval '3 year')\n" +
                ")");
        target.commit();
    }

    static long run(String sql) {
        long time = System.currentTimeMillis();
        int count = target.update(sql);
        long r = (System.currentTimeMillis() - time);
        System.out.println("Tidsåtgång: " + formatTimeOfDay(r) + ", Antal rader: " + count);
        return r;
    }

    static String formatTimeOfDay(long time) {
        return formatTimeOfDay(new Date(time));
    }

    static String formatTimeOfDay(Date time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormatted = formatter.format(time);
        return dateFormatted;
    }

}
