/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vgregion.dialys.i.vast.jpa.requisitions;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

/**
 * @author clalu4
 */
@Entity
@Table(name = "PDArtikel")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "PDArtikel.findAll", query = "SELECT p FROM PDArtikel p")
        , @NamedQuery(name = "PDArtikel.findById", query = "SELECT p FROM PDArtikel p WHERE p.id = :id")
        , @NamedQuery(name = "PDArtikel.findByPdid", query = "SELECT p FROM PDArtikel p WHERE p.pdid = :pdid")
        , @NamedQuery(name = "PDArtikel.findByArtikelID", query = "SELECT p FROM PDArtikel p WHERE p.artikelID = :artikelID")})
public class PDArtikel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PDID", insertable = false, updatable = false)
    private Integer pdid;

    @Column(name = "ArtikelID", updatable = false, insertable = false)
    private Integer artikelID;

    @Column(name = "maxantal")
    private Integer maxantal;

    @ManyToOne
    @JoinColumn(name = "artikelID", foreignKey = @ForeignKey(name = "fk_PDArtikel_Artikel"))
    private Artikel artikel;

    @JsonIgnore
    @OneToMany(mappedBy = "pdArtikel", fetch = FetchType.LAZY)
    private Set<BestPDRad> bestPDRad;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pdid", foreignKey = @ForeignKey(name = "fk_pdartikel_pd"))
    private Pd pd;

    public PDArtikel() {
    }

    public PDArtikel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPdid() {
        return pdid;
    }

    public void setPdid(Integer pdid) {
        this.pdid = pdid;
    }

    public Integer getArtikelID() {
        return artikelID;
    }

    public void setArtikelID(Integer artikelID) {
        this.artikelID = artikelID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PDArtikel)) return false;

        PDArtikel pdArtikel = (PDArtikel) o;

        return equals(pdArtikel.pdid, pdid) && equals(pdArtikel.artikelID, artikelID);

        /*if (id != null ? !id.equals(pdArtikel.id) : pdArtikel.id != null) return false;
        if (pdid != null ? !pdid.equals(pdArtikel.pdid) : pdArtikel.pdid != null) return false;
        if (artikelID != null ? !artikelID.equals(pdArtikel.artikelID) : pdArtikel.artikelID != null) return false;
        return artikel != null ? artikel.equals(pdArtikel.artikel) : pdArtikel.artikel == null;*/
    }

    private boolean equals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pdid != null ? pdid.hashCode() : 0);
        result = 31 * result + (artikelID != null ? artikelID.hashCode() : 0);
        result = 31 * result + (artikel != null ? artikel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.PDArtikel[ id=" + id + " ]";
    }

    public Set<BestPDRad> getBestPDRad() {
        return bestPDRad;
    }

    public void setBestPDRad(Set<BestPDRad> bestPDRad) {
        this.bestPDRad = bestPDRad;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    public Pd getPd() {
        return pd;
    }

    public void setPd(Pd pd) {
        this.pd = pd;
    }

    public Integer getMaxantal() {
        return maxantal;
    }

    public void setMaxantal(Integer maxantal) {
        this.maxantal = maxantal;
    }
}
