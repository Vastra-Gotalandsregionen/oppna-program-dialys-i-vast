/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vgregion.dialys.i.vast.jpa.requisitions;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author clalu4
 */
@Entity
@Table(name = "BestallningOLD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BestallningOLD.findAll", query = "SELECT b FROM BestallningOLD b")
    , @NamedQuery(name = "BestallningOLD.findById", query = "SELECT b FROM BestallningOLD b WHERE b.id = :id")
    , @NamedQuery(name = "BestallningOLD.findByBestID", query = "SELECT b FROM BestallningOLD b WHERE b.bestID = :bestID")
    , @NamedQuery(name = "BestallningOLD.findByPDArtikelID", query = "SELECT b FROM BestallningOLD b WHERE b.pDArtikelID = :pDArtikelID")
    , @NamedQuery(name = "BestallningOLD.findByDatum", query = "SELECT b FROM BestallningOLD b WHERE b.datum = :datum")
    , @NamedQuery(name = "BestallningOLD.findByAntal", query = "SELECT b FROM BestallningOLD b WHERE b.antal = :antal")
    , @NamedQuery(name = "BestallningOLD.findByUtskrivare", query = "SELECT b FROM BestallningOLD b WHERE b.utskrivare = :utskrivare")
    , @NamedQuery(name = "BestallningOLD.findByLevDatum", query = "SELECT b FROM BestallningOLD b WHERE b.levDatum = :levDatum")
    , @NamedQuery(name = "BestallningOLD.findByFritext", query = "SELECT b FROM BestallningOLD b WHERE b.fritext = :fritext")})
public class BestallningOLD implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "BestID")
    private Integer bestID;
    @Column(name = "PDArtikelID")
    private Integer pDArtikelID;
    @Column(name = "Datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @Column(name = "Antal")
    private Integer antal;
    @Size(max = 50)
    @Column(name = "Utskrivare")
    private String utskrivare;
    @Column(name = "LevDatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date levDatum;
    @Size(max = 200)
    @Column(name = "Fritext")
    private String fritext;

    public BestallningOLD() {
    }

    public BestallningOLD(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBestID() {
        return bestID;
    }

    public void setBestID(Integer bestID) {
        this.bestID = bestID;
    }

    public Integer getPDArtikelID() {
        return pDArtikelID;
    }

    public void setPDArtikelID(Integer pDArtikelID) {
        this.pDArtikelID = pDArtikelID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Integer getAntal() {
        return antal;
    }

    public void setAntal(Integer antal) {
        this.antal = antal;
    }

    public String getUtskrivare() {
        return utskrivare;
    }

    public void setUtskrivare(String utskrivare) {
        this.utskrivare = utskrivare;
    }

    public Date getLevDatum() {
        return levDatum;
    }

    public void setLevDatum(Date levDatum) {
        this.levDatum = levDatum;
    }

    public String getFritext() {
        return fritext;
    }

    public void setFritext(String fritext) {
        this.fritext = fritext;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BestallningOLD)) {
            return false;
        }
        BestallningOLD other = (BestallningOLD) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.BestallningOLD[ id=" + id + " ]";
    }
    
}
