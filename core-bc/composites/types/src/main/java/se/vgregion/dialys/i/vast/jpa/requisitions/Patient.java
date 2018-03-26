/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vgregion.dialys.i.vast.jpa.requisitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

/**
 * @author clalu4
 */
@Entity
@Table(name = "Patient")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
        , @NamedQuery(name = "Patient.findById", query = "SELECT p FROM Patient p WHERE p.id = :id")
        , @NamedQuery(name = "Patient.findByPnr", query = "SELECT p FROM Patient p WHERE p.pnr = :pnr")
        , @NamedQuery(name = "Patient.findByEfternamn", query = "SELECT p FROM Patient p WHERE p.efternamn = :efternamn")
        , @NamedQuery(name = "Patient.findByFornamn", query = "SELECT p FROM Patient p WHERE p.fornamn = :fornamn")
        , @NamedQuery(name = "Patient.findByAdress", query = "SELECT p FROM Patient p WHERE p.adress = :adress")
        , @NamedQuery(name = "Patient.findByPostNr", query = "SELECT p FROM Patient p WHERE p.postNr = :postNr")
        , @NamedQuery(name = "Patient.findByPostOrt", query = "SELECT p FROM Patient p WHERE p.postOrt = :postOrt")
        , @NamedQuery(name = "Patient.findByTelefon", query = "SELECT p FROM Patient p WHERE p.telefon = :telefon")
        , @NamedQuery(name = "Patient.findByMobil", query = "SELECT p FROM Patient p WHERE p.mobil = :mobil")
        , @NamedQuery(name = "Patient.findByEpost", query = "SELECT p FROM Patient p WHERE p.epost = :epost")
        , @NamedQuery(name = "Patient.findByPortkod", query = "SELECT p FROM Patient p WHERE p.portkod = :portkod")
        , @NamedQuery(name = "Patient.findByUtdelDag", query = "SELECT p FROM Patient p WHERE p.utdelDag = :utdelDag")
        , @NamedQuery(name = "Patient.findByUtdelVecka", query = "SELECT p FROM Patient p WHERE p.utdelVecka = :utdelVecka")
        , @NamedQuery(name = "Patient.findByUtdelText", query = "SELECT p FROM Patient p WHERE p.utdelText = :utdelText")
        , @NamedQuery(name = "Patient.findByPas", query = "SELECT p FROM Patient p WHERE p.pas = :pas")
        , @NamedQuery(name = "Patient.findBySamtycke", query = "SELECT p FROM Patient p WHERE p.samtycke = :samtycke")
        , @NamedQuery(name = "Patient.findByIsDeleted", query = "SELECT p FROM Patient p WHERE p.isDeleted = :isDeleted")})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "Pnr")
    private String pnr;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Efternamn")
    private String efternamn;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Fornamn")
    private String fornamn;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Adress")
    private String adress;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "PostNr")
    private String postNr;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PostOrt")
    private String postOrt;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Telefon")
    private String telefon;

    @Size(max = 20)
    @Column(name = "Mobil")
    private String mobil;

    @Size(max = 50)
    @Column(name = "Epost")
    private String epost;

    @Size(max = 10)
    @Column(name = "Portkod")
    private String portkod;

    @Size(max = 10)
    @Column(name = "UtdelDag")
    private String utdelDag;

    @Size(max = 10)
    @Column(name = "UtdelVecka")
    private String utdelVecka;

    @Size(max = 200)
    @Column(name = "UtdelText")
    private String utdelText;

    @Size(max = 1200)
    @Column(name = "ovrigt")
    private String ovrigt;

    @Column(name = "PAS", updatable = false, insertable = false)
    private Integer pas;

    @Column(name = "typ")
    @Enumerated(EnumType.STRING)
    private Typ typ;

    @Column(name = "leveransPaminnelse")
    private Boolean leveransPaminnelse = false;

    @Column(name = "avropsOmbud")
    private String avropsOmbud;

    @Column(name = "leveransMottagningsOmbud")
    private String leveransMottagningsOmbud;

    public Set<Pd> getPds() {
        return pds;
    }

    private static int noNulls(Long l) {
        if (l == null) {
            return 0;
        }
        return Math.toIntExact(l);
    }

    public void setPds(Set<Pd> pds) {
        this.pds = pds;
    }

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private Set<Pd> pds;

    @Column(name = "Samtycke")
    private Boolean samtycke;

    @Column(name = "IsDeleted")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pas", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_patient_ansvarig"))
    private Ansvarig ansvarig;

    @Column(name = "lasText")
    private String lasText;

    public Patient() {
    }

    public Patient(Integer id) {
        this.id = id;
    }

    public Patient(Integer id, String pnr, String efternamn, String fornamn, String adress, String postNr, String postOrt, String telefon) {
        this.id = id;
        this.pnr = pnr;
        this.efternamn = efternamn;
        this.fornamn = fornamn;
        this.adress = adress;
        this.postNr = postNr;
        this.postOrt = postOrt;
        this.telefon = telefon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getEfternamn() {
        return efternamn;
    }

    public void setEfternamn(String efternamn) {
        this.efternamn = efternamn;
    }

    public String getFornamn() {
        return fornamn;
    }

    public void setFornamn(String fornamn) {
        this.fornamn = fornamn;
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

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public String getEpost() {
        return epost;
    }

    public void setEpost(String epost) {
        this.epost = epost;
    }

    public String getPortkod() {
        return portkod;
    }

    public void setPortkod(String portkod) {
        this.portkod = portkod;
    }

    public String getUtdelDag() {
        return utdelDag;
    }

    public void setUtdelDag(String utdelDag) {
        this.utdelDag = utdelDag;
    }

    public String getUtdelVecka() {
        return utdelVecka;
    }

    public void setUtdelVecka(String utdelVecka) {
        this.utdelVecka = utdelVecka;
    }

    public String getUtdelText() {
        return utdelText;
    }

    public void setUtdelText(String utdelText) {
        this.utdelText = utdelText;
    }

    public Integer getPas() {
        return pas;
    }

    public void setPas(Integer pas) {
        this.pas = pas;
    }

    public Boolean getSamtycke() {
        return samtycke;
    }

    public void setSamtycke(Boolean samtycke) {
        this.samtycke = samtycke;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.Patient[ id=" + id + " ]";
    }

    public Ansvarig getAnsvarig() {
        return ansvarig;
    }

    public void setAnsvarig(Ansvarig ansvarig) {
        this.ansvarig = ansvarig;
    }

    public String getLasText() {
        return lasText;
    }

    public void setLasText(String lasText) {
        this.lasText = lasText;
    }

    public String getOvrigt() {
        return ovrigt;
    }

    public void setOvrigt(String ovrigt) {
        this.ovrigt = ovrigt;
    }


    public String getAvropsOmbud() {
        return avropsOmbud;
    }

    public void setAvropsOmbud(String avropsOmbud) {
        this.avropsOmbud = avropsOmbud;
    }

    public String getLeveransMottagningsOmbud() {
        return leveransMottagningsOmbud;
    }

    public void setLeveransMottagningsOmbud(String leveransMottagningsOmbud) {
        this.leveransMottagningsOmbud = leveransMottagningsOmbud;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getLeveransPaminnelse() {
        return leveransPaminnelse;
    }

    public void setLeveransPaminnelse(Boolean leveransPaminnelse) {
        this.leveransPaminnelse = leveransPaminnelse;
    }

    public Typ getTyp() {
        return typ;
    }

    public void setTyp(Typ typ) {
        this.typ = typ;
    }

    public enum Typ {
        PD,
        HD
    }

}
