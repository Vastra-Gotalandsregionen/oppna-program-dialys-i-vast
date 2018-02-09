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
@Table(name = "Grupp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupp.findAll", query = "SELECT g FROM Grupp g")
    , @NamedQuery(name = "Grupp.findById", query = "SELECT g FROM Grupp g WHERE g.id = :id")
    , @NamedQuery(name = "Grupp.findByFlikID", query = "SELECT g FROM Grupp g WHERE g.flikID = :flikID")
    , @NamedQuery(name = "Grupp.findByTitel", query = "SELECT g FROM Grupp g WHERE g.titel = :titel")
    , @NamedQuery(name = "Grupp.findByOrdning", query = "SELECT g FROM Grupp g WHERE g.ordning = :ordning")})
public class Grupp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FlikID")
    private Integer flikID;
    @Size(max = 20)
    @Column(name = "Titel")
    private String titel;
    @Column(name = "Ordning")
    private Integer ordning;

    public Grupp() {
    }

    public Grupp(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlikID() {
        return flikID;
    }

    public void setFlikID(Integer flikID) {
        this.flikID = flikID;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
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
        if (!(object instanceof Grupp)) {
            return false;
        }
        Grupp other = (Grupp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.Grupp[ id=" + id + " ]";
    }
    
}
