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
@Table(name = "Artikel")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Artikel.findAll", query = "SELECT a FROM Artikel a")
        , @NamedQuery(name = "Artikel.findById", query = "SELECT a FROM Artikel a WHERE a.id = :id")
        , @NamedQuery(name = "Artikel.findByGruppID", query = "SELECT a FROM Artikel a WHERE a.gruppID = :gruppID")
        , @NamedQuery(name = "Artikel.findByNamn", query = "SELECT a FROM Artikel a WHERE a.namn = :namn")
        , @NamedQuery(name = "Artikel.findByStorlek", query = "SELECT a FROM Artikel a WHERE a.storlek = :storlek")
        , @NamedQuery(name = "Artikel.findByArtNr", query = "SELECT a FROM Artikel a WHERE a.artNr = :artNr")
        , @NamedQuery(name = "Artikel.findByOrdning", query = "SELECT a FROM Artikel a WHERE a.ordning = :ordning")})
public class Artikel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "GruppID", insertable = false, updatable = false)
    private Integer gruppID;
    @Size(max = 50)
    @Column(name = "Namn")
    private String namn;
    @Size(max = 50)
    @Column(name = "Storlek")
    private String storlek;
    @Size(max = 20)
    @Column(name = "ArtNr")
    private String artNr;
    @Column(name = "ordning")
    private Integer ordning;

    @JsonIgnore
    @OneToMany(mappedBy = "artikel", fetch = FetchType.LAZY)
    private Set<PDArtikel> pdArtikels;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "gruppID", foreignKey = @ForeignKey(name = "fk_artikel_grupp"))
    private Grupp grupp;


    private Integer maxantal;

    public Integer getMaxantal() {
        return maxantal;
    }

    public void setMaxantal(Integer maxantal) {
        this.maxantal = maxantal;
    }

    public Artikel() {

    }

    public Artikel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGruppID() {
        return gruppID;
    }

    public void setGruppID(Integer gruppID) {
        this.gruppID = gruppID;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getStorlek() {
        return storlek;
    }

    public void setStorlek(String storlek) {
        this.storlek = storlek;
    }

    public String getArtNr() {
        return artNr;
    }

    public void setArtNr(String artNr) {
        this.artNr = artNr;
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
        if (!(object instanceof Artikel)) {
            return false;
        }
        Artikel other = (Artikel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.Artikel[ id=" + id + " ]";
    }

    public Set<PDArtikel> getPdArtikels() {
        return pdArtikels;
    }

    public void setPdArtikels(Set<PDArtikel> pdArtikels) {
        this.pdArtikels = pdArtikels;
    }

    public Grupp getGrupp() {
        return grupp;
    }

    public void setGrupp(Grupp grupp) {
        this.grupp = grupp;
    }
}
