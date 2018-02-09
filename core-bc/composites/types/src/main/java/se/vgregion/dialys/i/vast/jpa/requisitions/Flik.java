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
@Table(name = "Flik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Flik.findAll", query = "SELECT f FROM Flik f")
    , @NamedQuery(name = "Flik.findById", query = "SELECT f FROM Flik f WHERE f.id = :id")
    , @NamedQuery(name = "Flik.findByTitel", query = "SELECT f FROM Flik f WHERE f.titel = :titel")
    , @NamedQuery(name = "Flik.findByOrdning", query = "SELECT f FROM Flik f WHERE f.ordning = :ordning")
    , @NamedQuery(name = "Flik.findByTyp", query = "SELECT f FROM Flik f WHERE f.typ = :typ")})
public class Flik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "Titel")
    private String titel;
    @Column(name = "Ordning")
    private Integer ordning;
    @Size(max = 2)
    @Column(name = "Typ")
    private String typ;

    public Flik() {
    }

    public Flik(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
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
        if (!(object instanceof Flik)) {
            return false;
        }
        Flik other = (Flik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.Flik[ id=" + id + " ]";
    }
    
}
