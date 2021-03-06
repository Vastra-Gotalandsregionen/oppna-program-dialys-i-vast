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
import java.util.HashSet;
import java.util.Set;

/**
 * @author clalu4
 */
@Entity
@Table(name = "Users")
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "UserName")
    private String userName;

    @Size(min = 1, max = 200)
    @Column(name = "PassWord")
    private String passWord;

    @Size(max = 50)
    @Column(name = "Name")
    private String name;

    @Column(name = "sjukskoterska")
    private Boolean sjukskoterska = false;

    @Column(name = "pharmaceut")
    private Boolean pharmaceut = false;

    @Column(name = "admin")
    private Boolean admin = false;

    @Column(name = "status")
    private String status;

    @Column(name = "password_encrypted_flag")
    private Boolean passwordEncryptionFlag = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "anstallning",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "mottagningid"))
    private Set<Mottagning> mottagnings = new HashSet<>();

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "User[ userName=" + userName + " ]";
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

    public Set<Mottagning> getMottagnings() {
        return mottagnings;
    }

    public void setMottagnings(Set<Mottagning> mottagnings) {
        this.mottagnings = mottagnings;
    }

}
