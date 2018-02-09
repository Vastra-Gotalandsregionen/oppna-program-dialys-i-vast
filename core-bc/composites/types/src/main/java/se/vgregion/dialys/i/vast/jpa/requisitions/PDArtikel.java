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
@Table(name = "PDArtikel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PDArtikel.findAll", query = "SELECT p FROM PDArtikel p")
    , @NamedQuery(name = "PDArtikel.findById", query = "SELECT p FROM PDArtikel p WHERE p.id = :id")
    , @NamedQuery(name = "PDArtikel.findByPdid", query = "SELECT p FROM PDArtikel p WHERE p.pdid = :pdid")
    , @NamedQuery(name = "PDArtikel.findByArtikelID", query = "SELECT p FROM PDArtikel p WHERE p.artikelID = :artikelID")})
public class PDArtikel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PDID")
    private Integer pdid;
    @Column(name = "ArtikelID")
    private Integer artikelID;

    public PDArtikel() {
    }

    public PDArtikel(Integer id) {
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

    public Integer getArtikelID() {
        return artikelID;
    }

    public void setArtikelID(Integer artikelID) {
        this.artikelID = artikelID;
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
        if (!(object instanceof PDArtikel)) {
            return false;
        }
        PDArtikel other = (PDArtikel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.PDArtikel[ id=" + id + " ]";
    }
    
}
