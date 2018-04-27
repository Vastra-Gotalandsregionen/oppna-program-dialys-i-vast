/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vgregion.dialys.i.vast.jpa.requisitions;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

/**
 * @author clalu4
 */
@Entity
@Table(name = "Grupp")
@XmlRootElement
public class Grupp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    /*@Column(name = "FlikID", insertable = false, updatable = false)
    private Integer flikID;*/

    @Size(max = 20)
    @Column(name = "Titel")
    private String titel;

    @Column(name = "Ordning")
    private Integer ordning;

    @OneToMany(mappedBy = "grupp", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Artikel> artikels;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "flikID", foreignKey = @ForeignKey(name = "fk_grupp_flik"))
    private Flik flik;

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

    /*public Integer getFlikID() {
        return flikID;
    }

    public void setFlikID(Integer flikID) {
        this.flikID = flikID;
    }*/

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

    public Set<Artikel> getArtikels() {
        return artikels;
    }

    public void setArtikels(Set<Artikel> artikels) {
        this.artikels = artikels;
    }

    public Flik getFlik() {
        return flik;
    }

    public void setFlik(Flik flik) {
        this.flik = flik;
    }
}
