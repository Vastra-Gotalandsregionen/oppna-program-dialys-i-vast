package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * After first deploy of the application - run this.
 */
public class MiscProbeing {

    static ConnectionExt target;//  = AbstractDatabaseCopy.getTargetConnection();

    public static void main(String[] args) throws IOException {

        target = AbstractDatabaseCopy.getTargetConnection();

        System.out.println("Trying to delete from bestpdrad");
        long t = run("delete from bestpdrad where bestid in (\n" +
                "select distinct brad.bestid from pd \n" +
                "left join pdartikel pda on (pda.pdid = pd.id)\n" +
                "left join bestinfo bi on (bi.pdid = pd.id)\n" +
                "left join bestpdrad brad on (brad.bestid = bi.id)\n" +
                "where pd.datum < (CURRENT_DATE - interval '3 year')\n" +
                ")");
        System.out.println("Took " + t + "s.");

        System.out.println("Trying to delete from bestinfo");
        t = run("delete from bestinfo where id in (\n" +
                "select distinct bi.id from pd \n" +
                "left join pdartikel pda on (pda.pdid = pd.id)\n" +
                "left join bestinfo bi on (bi.pdid = pd.id)\n" +
                "where pd.datum < (CURRENT_DATE - interval '3 year')\n" +
                ")");
        System.out.println("Took " + t + "s.");

        System.out.println("Trying to delete from pdartikel");
        t = run("delete from pdartikel where id in (\n" +
                "select distinct pda.id from pd \n" +
                "left join pdartikel pda on (pda.pdid = pd.id)\n" +
                "where pd.datum < (CURRENT_DATE - interval '3 year')\n" +
                ")");
        System.out.println("Took " + t + "s.");

        System.out.println("Trying to delete from pd");
        t = run("delete from pd where id in (\n" +
                "select distinct pd.id from pd \n" +
                "where pd.datum < (CURRENT_DATE - interval '3 year')\n" +
                ")");
        System.out.println("Took " + t + "s.");

    }

    static String format(Date date) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

    static long run(String sql) {
        long time = System.currentTimeMillis();
        target.update(sql);
        return (System.currentTimeMillis() - time) / 1000;
    }

    private static void print(List<Map<String, Object>> result) {
        for (Map<String, Object> item : result) {
            System.out.println(item);
        }
        System.out.println();
    }


}
