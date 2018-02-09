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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author clalu4
 */
@Entity
@Table(name = "Dosering")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dosering.findAll", query = "SELECT d FROM Dosering d")
    , @NamedQuery(name = "Dosering.findById", query = "SELECT d FROM Dosering d WHERE d.id = :id")
    , @NamedQuery(name = "Dosering.findByNamn", query = "SELECT d FROM Dosering d WHERE d.namn = :namn")
    , @NamedQuery(name = "Dosering.findByOrdning", query = "SELECT d FROM Dosering d WHERE d.ordning = :ordning")})
public class Dosering implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "Namn")
    private String namn;
    @Column(name = "Ordning")
    private Integer ordning;

    public Dosering() {
    }

    public Dosering(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public Integer getOrdning() {
        return ordning;
    }

    public void setOrdning(Integer ordning) {
        this.ordning = ordning;
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
        if (!(object instanceof Dosering)) {
            return false;
        }
        Dosering other = (Dosering) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.Dosering[ id=" + id + " ]";
    }
    
}
