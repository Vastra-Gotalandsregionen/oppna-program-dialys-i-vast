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
@Table(name = "Placering")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Placering.findAll", query = "SELECT p FROM Placering p")
    , @NamedQuery(name = "Placering.findById", query = "SELECT p FROM Placering p WHERE p.id = :id")
    , @NamedQuery(name = "Placering.findByUsersID", query = "SELECT p FROM Placering p WHERE p.usersID = :usersID")
    , @NamedQuery(name = "Placering.findByApotekID", query = "SELECT p FROM Placering p WHERE p.apotekID = :apotekID")
    , @NamedQuery(name = "Placering.findByMottagningID", query = "SELECT p FROM Placering p WHERE p.mottagningID = :mottagningID")})
public class Placering implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "UsersID")
    private Integer usersID;
    @Column(name = "ApotekID")
    private Integer apotekID;
    @Column(name = "MottagningID")
    private Integer mottagningID;

    public Placering() {
    }

    public Placering(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsersID() {
        return usersID;
    }

    public void setUsersID(Integer usersID) {
        this.usersID = usersID;
    }

    public Integer getApotekID() {
        return apotekID;
    }

    public void setApotekID(Integer apotekID) {
        this.apotekID = apotekID;
    }

    public Integer getMottagningID() {
        return mottagningID;
    }

    public void setMottagningID(Integer mottagningID) {
        this.mottagningID = mottagningID;
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
        if (!(object instanceof Placering)) {
            return false;
        }
        Placering other = (Placering) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.Placering[ id=" + id + " ]";
    }
    
}
