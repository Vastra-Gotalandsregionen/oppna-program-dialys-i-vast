/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vgregion.dialys.i.vast.jpa.requisitions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author clalu4
 */
@Entity
@Table(name = "PD")
@XmlRootElement
public class Pd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PatientID", updatable = false, insertable = false)
    private Integer patientID;

    @Column(name = "Datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;

    @Column(name = "Ersatter")
    private Integer ersatter;

    @Column(name = "Giltighet")
    @Temporal(TemporalType.TIMESTAMP)
    private Date giltighet;

    @Column(name = "SskID")
    private Integer sskID;

    @Column(name = "LAS")
    private Integer las;

    @Column(name = "typ")
    private String typ;

    @OneToMany(mappedBy = "pd")
    private Set<BestInfo> bestInfos;

    @OneToMany(mappedBy = "pd", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PDArtikel> pdArtikels;

    @JsonIgnore
    public Patient getPatient() {
        return patient;
    }

    @JsonProperty
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PatientID", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_pd_patient"))
    private Patient patient;

    public Pd() {
    }

    public Pd(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Integer getErsatter() {
        return ersatter;
    }

    public void setErsatter(Integer ersatter) {
        this.ersatter = ersatter;
    }

    public Date getGiltighet() {
        return giltighet;
    }

    public void setGiltighet(Date giltighet) {
        this.giltighet = giltighet;
    }

    public Integer getSskID() {
        return sskID;
    }

    public void setSskID(Integer sskID) {
        this.sskID = sskID;
    }

    public Integer getLas() {
        return las;
    }

    public void setLas(Integer las) {
        this.las = las;
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
        if (!(object instanceof Pd)) {
            return false;
        }
        Pd other = (Pd) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.Pd[ id=" + id + " ]";
    }

    public Set<BestInfo> getBestInfos() {
        return bestInfos;
    }

    public void setBestInfos(Set<BestInfo> bestInfos) {
        this.bestInfos = bestInfos;
    }

    public Set<PDArtikel> getPdArtikels() {
        return pdArtikels;
    }

    public void setPdArtikels(Set<PDArtikel> pdArtikels) {
        this.pdArtikels = pdArtikels;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
}
