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
@Table(name = "Patient_Lakemedel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PatientLakemedel.findAll", query = "SELECT p FROM PatientLakemedel p")
    , @NamedQuery(name = "PatientLakemedel.findById", query = "SELECT p FROM PatientLakemedel p WHERE p.id = :id")
    , @NamedQuery(name = "PatientLakemedel.findByLakemedelID", query = "SELECT p FROM PatientLakemedel p WHERE p.lakemedelID = :lakemedelID")
    , @NamedQuery(name = "PatientLakemedel.findByDoseringText", query = "SELECT p FROM PatientLakemedel p WHERE p.doseringText = :doseringText")
    , @NamedQuery(name = "PatientLakemedel.findByLakarID", query = "SELECT p FROM PatientLakemedel p WHERE p.lakarID = :lakarID")
    , @NamedQuery(name = "PatientLakemedel.findByPatientID", query = "SELECT p FROM PatientLakemedel p WHERE p.patientID = :patientID")
    , @NamedQuery(name = "PatientLakemedel.findByCreated", query = "SELECT p FROM PatientLakemedel p WHERE p.created = :created")
    , @NamedQuery(name = "PatientLakemedel.findByIsDeleted", query = "SELECT p FROM PatientLakemedel p WHERE p.isDeleted = :isDeleted")
    , @NamedQuery(name = "PatientLakemedel.findByIsDeletedBy", query = "SELECT p FROM PatientLakemedel p WHERE p.isDeletedBy = :isDeletedBy")
    , @NamedQuery(name = "PatientLakemedel.findByIsDeletedTime", query = "SELECT p FROM PatientLakemedel p WHERE p.isDeletedTime = :isDeletedTime")})
public class PatientLakemedel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "LakemedelID")
    private Integer lakemedelID;
    @Size(max = 50)
    @Column(name = "DoseringText")
    private String doseringText;
    @Column(name = "LakarID")
    private Integer lakarID;
    @Column(name = "PatientID")
    private Integer patientID;
    @Column(name = "Created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "IsDeleted")
    private Boolean isDeleted;
    @Column(name = "IsDeletedBy")
    private Integer isDeletedBy;
    @Column(name = "IsDeletedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date isDeletedTime;

    public PatientLakemedel() {
    }

    public PatientLakemedel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLakemedelID() {
        return lakemedelID;
    }

    public void setLakemedelID(Integer lakemedelID) {
        this.lakemedelID = lakemedelID;
    }

    public String getDoseringText() {
        return doseringText;
    }

    public void setDoseringText(String doseringText) {
        this.doseringText = doseringText;
    }

    public Integer getLakarID() {
        return lakarID;
    }

    public void setLakarID(Integer lakarID) {
        this.lakarID = lakarID;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeletedBy() {
        return isDeletedBy;
    }

    public void setIsDeletedBy(Integer isDeletedBy) {
        this.isDeletedBy = isDeletedBy;
    }

    public Date getIsDeletedTime() {
        return isDeletedTime;
    }

    public void setIsDeletedTime(Date isDeletedTime) {
        this.isDeletedTime = isDeletedTime;
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
        if (!(object instanceof PatientLakemedel)) {
            return false;
        }
        PatientLakemedel other = (PatientLakemedel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.PatientLakemedel[ id=" + id + " ]";
    }
    
}
