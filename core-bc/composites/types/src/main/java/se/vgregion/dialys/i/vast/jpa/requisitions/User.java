/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vgregion.dialys.i.vast.jpa.requisitions;

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
@Table(name = "Users")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Users.findAll", query = "SELECT u FROM User u")
        , @NamedQuery(name = "Users.findById", query = "SELECT u FROM User u WHERE u.userName = :userName")
        , @NamedQuery(name = "Users.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName")
        , @NamedQuery(name = "Users.findByPassWord", query = "SELECT u FROM User u WHERE u.passWord = :passWord")
        , @NamedQuery(name = "Users.findByName", query = "SELECT u FROM User u WHERE u.name = :name")
        , @NamedQuery(name = "Users.findByTyp", query = "SELECT u FROM User u WHERE u.typ = :typ")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "UserName")
    private String userName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "PassWord")
    private String passWord;

    @Size(max = 50)
    @Column(name = "Name")
    private String name;

    @Size(max = 2)
    @Column(name = "Typ")
    private String typ;

    @Column(name = "sjukskoterska")
    private Boolean sjukskoterska;

    @Column(name = "pharmaceut")
    private Boolean pharmaceut;

    @Column(name = "admin")
    private Boolean admin;

    @Column(name = "password_encrypted_flag")
    private Boolean passwordEncryptionFlag = false;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Ansvarig> ansvariga;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        hash += (userName != null ? userName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the userName fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userName == null && other.userName != null) || (this.userName != null && !this.userName.equals(other.userName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.User[ userName=" + userName + " ]";
    }

    public Set<Ansvarig> getAnsvariga() {
        return ansvariga;
    }

    public void setAnsvariga(Set<Ansvarig> ansvariga) {
        this.ansvariga = ansvariga;
    }

    public Boolean getPasswordEncryptionFlag() {
        return passwordEncryptionFlag;
    }

    public void setPasswordEncryptionFlag(Boolean passwordIsEncrpted) {
        this.passwordEncryptionFlag = passwordIsEncrpted;
    }

    public Boolean getSjukskoterska() {
        return sjukskoterska;
    }

    public void setSjukskoterska(Boolean sjukskoterska) {
        this.sjukskoterska = sjukskoterska;
    }

    public Boolean getPharmaceut() {
        return pharmaceut;
    }

    public void setPharmaceut(Boolean pharmaceut) {
        this.pharmaceut = pharmaceut;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
