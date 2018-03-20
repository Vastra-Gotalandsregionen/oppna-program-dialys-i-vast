/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vgregion.dialys.i.vast.jpa.requisitions;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author clalu4
 */
@Entity
@Table(name = "BestPDRad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BestPDRad.findAll", query = "SELECT b FROM BestPDRad b")
    , @NamedQuery(name = "BestPDRad.findById", query = "SELECT b FROM BestPDRad b WHERE b.id = :id")
    , @NamedQuery(name = "BestPDRad.findByBestID", query = "SELECT b FROM BestPDRad b WHERE b.bestID = :bestID")
    , @NamedQuery(name = "BestPDRad.findByPDArtikelID", query = "SELECT b FROM BestPDRad b WHERE b.pDArtikelID = :pDArtikelID")
    , @NamedQuery(name = "BestPDRad.findByAntal", query = "SELECT b FROM BestPDRad b WHERE b.antal = :antal")})
public class BestPDRad implements Serializable {

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
    @Column(name = "Antal")
    private Integer antal;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pDArtikelID", foreignKey = @ForeignKey(name = "fk_BestPDRad_PDArtikel"))
    private PDArtikel pdArtikel;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bestID", foreignKey = @ForeignKey(name = "fk_BestPDRad_BestInfo"))
    private BestInfo bestInfo;

    public BestPDRad() {
    }

    public BestPDRad(Integer id) {
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

    public Integer getAntal() {
        return antal;
    }

    public void setAntal(Integer antal) {
        this.antal = antal;
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
        if (!(object instanceof BestPDRad)) {
            return false;
        }
        BestPDRad other = (BestPDRad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.BestPDRad[ id=" + id + " ]";
    }

    public BestInfo getBestInfo() {
        return bestInfo;
    }

    public void setBestInfo(BestInfo bestInfo) {
        this.bestInfo = bestInfo;
    }

    public PDArtikel getPdArtikel() {
        return pdArtikel;
    }

    public void setPdArtikel(PDArtikel pdArtikel) {
        this.pdArtikel = pdArtikel;
    }
}
