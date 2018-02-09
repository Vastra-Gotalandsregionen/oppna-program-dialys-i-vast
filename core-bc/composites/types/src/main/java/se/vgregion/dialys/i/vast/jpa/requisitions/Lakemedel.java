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
@Table(name = "Lakemedel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lakemedel.findAll", query = "SELECT l FROM Lakemedel l")
    , @NamedQuery(name = "Lakemedel.findById", query = "SELECT l FROM Lakemedel l WHERE l.id = :id")
    , @NamedQuery(name = "Lakemedel.findByNamn", query = "SELECT l FROM Lakemedel l WHERE l.namn = :namn")
    , @NamedQuery(name = "Lakemedel.findByStyrka", query = "SELECT l FROM Lakemedel l WHERE l.styrka = :styrka")
    , @NamedQuery(name = "Lakemedel.findByStorlek", query = "SELECT l FROM Lakemedel l WHERE l.storlek = :storlek")
    , @NamedQuery(name = "Lakemedel.findByForpackning", query = "SELECT l FROM Lakemedel l WHERE l.forpackning = :forpackning")
    , @NamedQuery(name = "Lakemedel.findByArtNr", query = "SELECT l FROM Lakemedel l WHERE l.artNr = :artNr")
    , @NamedQuery(name = "Lakemedel.findByUpphandlad", query = "SELECT l FROM Lakemedel l WHERE l.upphandlad = :upphandlad")
    , @NamedQuery(name = "Lakemedel.findByOrdning", query = "SELECT l FROM Lakemedel l WHERE l.ordning = :ordning")})
public class Lakemedel implements Serializable {

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
    @Column(name = "Styrka")
    private String styrka;
    @Size(max = 20)
    @Column(name = "Storlek")
    private String storlek;
    @Size(max = 50)
    @Column(name = "Forpackning")
    private String forpackning;
    @Size(max = 20)
    @Column(name = "ArtNr")
    private String artNr;
    @Column(name = "Upphandlad")
    private Boolean upphandlad;
    @Column(name = "Ordning")
    private Integer ordning;

    public Lakemedel() {
    }

    public Lakemedel(Integer id) {
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

    public String getStyrka() {
        return styrka;
    }

    public void setStyrka(String styrka) {
        this.styrka = styrka;
    }

    public String getStorlek() {
        return storlek;
    }

    public void setStorlek(String storlek) {
        this.storlek = storlek;
    }

    public String getForpackning() {
        return forpackning;
    }

    public void setForpackning(String forpackning) {
        this.forpackning = forpackning;
    }

    public String getArtNr() {
        return artNr;
    }

    public void setArtNr(String artNr) {
        this.artNr = artNr;
    }

    public Boolean getUpphandlad() {
        return upphandlad;
    }

    public void setUpphandlad(Boolean upphandlad) {
        this.upphandlad = upphandlad;
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
        if (!(object instanceof Lakemedel)) {
            return false;
        }
        Lakemedel other = (Lakemedel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.Lakemedel[ id=" + id + " ]";
    }
    
}
