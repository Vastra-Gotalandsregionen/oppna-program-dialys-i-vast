/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vgregion.dialys.i.vast.jpa.requisitions;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author clalu4
 */
@Entity
@Table(name = "BestLakemedelRad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BestLakemedelRad.findAll", query = "SELECT b FROM BestLakemedelRad b")
    , @NamedQuery(name = "BestLakemedelRad.findById", query = "SELECT b FROM BestLakemedelRad b WHERE b.id = :id")
    , @NamedQuery(name = "BestLakemedelRad.findByBestID", query = "SELECT b FROM BestLakemedelRad b WHERE b.bestID = :bestID")
    , @NamedQuery(name = "BestLakemedelRad.findByPatientLakemedelID", query = "SELECT b FROM BestLakemedelRad b WHERE b.patientLakemedelID = :patientLakemedelID")
    , @NamedQuery(name = "BestLakemedelRad.findByAntal", query = "SELECT b FROM BestLakemedelRad b WHERE b.antal = :antal")})
public class BestLakemedelRad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "BestID")
    private Integer bestID;
    @Column(name = "PatientLakemedelID")
    private Integer patientLakemedelID;
    @Column(name = "Antal")
    private Integer antal;

    public BestLakemedelRad() {
    }

    public BestLakemedelRad(Integer id) {
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

    public Integer getPatientLakemedelID() {
        return patientLakemedelID;
    }

    public void setPatientLakemedelID(Integer patientLakemedelID) {
        this.patientLakemedelID = patientLakemedelID;
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
        if (!(object instanceof BestLakemedelRad)) {
            return false;
        }
        BestLakemedelRad other = (BestLakemedelRad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.BestLakemedelRad[ id=" + id + " ]";
    }
    
}
