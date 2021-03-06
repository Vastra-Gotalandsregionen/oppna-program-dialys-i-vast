/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vgregion.dialys.i.vast.jpa.requisitions;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author clalu4
 */
@Entity
@Table(name = "BestInfo")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "BestInfo.findAll", query = "SELECT b FROM BestInfo b")
        , @NamedQuery(name = "BestInfo.findById", query = "SELECT b FROM BestInfo b WHERE b.id = :id")
        , @NamedQuery(name = "BestInfo.findByPdid", query = "SELECT b FROM BestInfo b WHERE b.pdid = :pdid")
        , @NamedQuery(name = "BestInfo.findByDatum", query = "SELECT b FROM BestInfo b WHERE b.datum = :datum")
        , @NamedQuery(name = "BestInfo.findByUtskrivare", query = "SELECT b FROM BestInfo b WHERE b.utskrivare = :utskrivare")
        , @NamedQuery(name = "BestInfo.findByLevDatum", query = "SELECT b FROM BestInfo b WHERE b.levDatum = :levDatum")
        , @NamedQuery(name = "BestInfo.findByFritext", query = "SELECT b FROM BestInfo b WHERE b.fritext = :fritext")})
public class BestInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PDID", updatable = false, insertable = false)
    private Integer pdid;

    @Column(name = "Datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @Size(max = 50)
    @Column(name = "Utskrivare")
    private String utskrivare;

    @Column(name = "LevDatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date levDatum;

    @Size(max = 200)
    @Column(name = "Fritext")
    private String fritext;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pdid", foreignKey = @ForeignKey(name = "fk_bestInfo_pd"))
    private Pd pd;

    @OneToMany(mappedBy = "bestInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<BestPDRad> bestPDRads;

    public BestInfo() {
    }

    public BestInfo(Integer id) {
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

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
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
        if (!(object instanceof BestInfo)) {
            return false;
        }
        BestInfo other = (BestInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.BestInfo[ id=" + id + " ]";
    }

    public Pd getPd() {
        return pd;
    }

    public void setPd(Pd pd) {
        this.pd = pd;
    }

    public Set<BestPDRad> getBestPDRads() {
        return bestPDRads;
    }

    public void setBestPDRads(Set<BestPDRad> bestPDRads) {
        this.bestPDRads = bestPDRads;
    }
}
