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
@Table(name = "Apotek")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apotek.findAll", query = "SELECT a FROM Apotek a")
    , @NamedQuery(name = "Apotek.findById", query = "SELECT a FROM Apotek a WHERE a.id = :id")
    , @NamedQuery(name = "Apotek.findByNamn", query = "SELECT a FROM Apotek a WHERE a.namn = :namn")
    , @NamedQuery(name = "Apotek.findByAdress", query = "SELECT a FROM Apotek a WHERE a.adress = :adress")
    , @NamedQuery(name = "Apotek.findByPostNr", query = "SELECT a FROM Apotek a WHERE a.postNr = :postNr")
    , @NamedQuery(name = "Apotek.findByPostOrt", query = "SELECT a FROM Apotek a WHERE a.postOrt = :postOrt")
    , @NamedQuery(name = "Apotek.findByTelefon", query = "SELECT a FROM Apotek a WHERE a.telefon = :telefon")
    , @NamedQuery(name = "Apotek.findByFritext", query = "SELECT a FROM Apotek a WHERE a.fritext = :fritext")})
public class Apotek implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "Namn")
    private String namn;
    @Size(max = 50)
    @Column(name = "Adress")
    private String adress;
    @Size(max = 6)
    @Column(name = "PostNr")
    private String postNr;
    @Size(max = 50)
    @Column(name = "PostOrt")
    private String postOrt;
    @Size(max = 20)
    @Column(name = "Telefon")
    private String telefon;
    @Size(max = 200)
    @Column(name = "Fritext")
    private String fritext;

    public Apotek() {
    }

    public Apotek(Integer id) {
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPostNr() {
        return postNr;
    }

    public void setPostNr(String postNr) {
        this.postNr = postNr;
    }

    public String getPostOrt() {
        return postOrt;
    }

    public void setPostOrt(String postOrt) {
        this.postOrt = postOrt;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
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
        if (!(object instanceof Apotek)) {
            return false;
        }
        Apotek other = (Apotek) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.Apotek[ id=" + id + " ]";
    }
    
}
