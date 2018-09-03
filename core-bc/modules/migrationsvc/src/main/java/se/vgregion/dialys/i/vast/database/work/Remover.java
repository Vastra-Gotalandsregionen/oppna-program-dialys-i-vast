package se.vgregion.dialys.i.vast.database.work;

import se.vgregion.arbetsplatskoder.db.migration.sql.ConnectionExt;

import java.io.IOException;

public class Remover {

    public static void main(String[] args) throws IOException {
        ConnectionExt main = AbstractDatabaseCopy.getTargetConnection();
        System.out.println(main.getUrl());
        System.out.println("Patient count " + main.query("select count(*) from patient", 0, 1));
        int r = main.update("delete from patient where pnr not in (?)");
        System.out.println("Number of affected items " + r);
    }

}
