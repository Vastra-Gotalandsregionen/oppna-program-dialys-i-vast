/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vgregion.dialys.i.vast.jpa.requisitions;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author clalu4
 */
@Entity
@Table(name = "Mottagning")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mottagning.findAll", query = "SELECT m FROM Mottagning m")
    , @NamedQuery(name = "Mottagning.findById", query = "SELECT m FROM Mottagning m WHERE m.id = :id")
    , @NamedQuery(name = "Mottagning.findByNamn", query = "SELECT m FROM Mottagning m WHERE m.namn = :namn")
    , @NamedQuery(name = "Mottagning.findByApotekID", query = "SELECT m FROM Mottagning m WHERE m.apotekID = :apotekID")})
public class Mottagning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Size(max = 50)
    @Column(name = "Namn")
    private String namn;

    @Column(name = "ApotekID")
    private Integer apotekID;

    @OneToMany(mappedBy = "mottagning")
    private Set<Ansvarig> ansvarigs;

    public Mottagning() {
    }

    public Mottagning(Integer id) {
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

    public Integer getApotekID() {
        return apotekID;
    }

    public void setApotekID(Integer apotekID) {
        this.apotekID = apotekID;
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
        if (!(object instanceof Mottagning)) {
            return false;
        }
        Mottagning other = (Mottagning) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.Mottagning[ id=" + id + " ]";
    }

    public Set<Ansvarig> getAnsvarigs() {
        return ansvarigs;
    }

    public void setAnsvarigs(Set<Ansvarig> ansvarigs) {
        this.ansvarigs = ansvarigs;
    }
}
