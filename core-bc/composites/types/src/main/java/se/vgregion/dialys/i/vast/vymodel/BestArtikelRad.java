package se.vgregion.dialys.i.vast.vymodel;

import se.vgregion.dialys.i.vast.jpa.requisitions.Artikel;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestPDRad;

public class BestArtikelRad {
    private int antal;
    private String art_nr;
    private String mangd;
    private String artikel;

    public BestArtikelRad(int antal, String art_nr, String mangd, String artikel) {
        super();
        this.antal = antal;
        this.art_nr = art_nr;
        this.mangd = mangd;
        this.artikel = artikel;
    }

    public BestArtikelRad(BestPDRad brad, Artikel art) {
        this(brad.getAntal(), art.getArtNr(), art.getStorlek(), art.getNamn());
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }

    public String getArt_nr() {
        return art_nr;
    }

    public void setArt_nr(String art_nr) {
        this.art_nr = art_nr;
    }

    public String getMangd() {
        return mangd;
    }

    public void setMangd(String mangd) {
        this.mangd = mangd;
    }

    public String getArtikel() {
        return artikel;
    }

    public void setArtikel(String artikel) {
        this.artikel = artikel;
    }
}
