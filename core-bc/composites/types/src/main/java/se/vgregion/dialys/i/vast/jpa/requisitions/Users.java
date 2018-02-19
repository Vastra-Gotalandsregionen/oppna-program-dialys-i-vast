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
import java.util.List;

/**
 * @author clalu4
 */
@Entity
@Table(name = "Users")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
        , @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.userName = :userName")
        , @NamedQuery(name = "Users.findByUserName", query = "SELECT u FROM Users u WHERE u.userName = :userName")
        , @NamedQuery(name = "Users.findByPassWord", query = "SELECT u FROM Users u WHERE u.passWord = :passWord")
        , @NamedQuery(name = "Users.findByName", query = "SELECT u FROM Users u WHERE u.name = :name")
        , @NamedQuery(name = "Users.findByTyp", query = "SELECT u FROM Users u WHERE u.typ = :typ")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
/*    @userName
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "userName")
    private Integer userName;*/

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "UserName")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PassWord")
    private String passWord;
    @Size(max = 50)
    @Column(name = "Name")
    private String name;
    @Size(max = 2)
    @Column(name = "Typ")
    private String typ;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Ansvarig> ansvariga;


    public Users() {
    }

    public Users(String userName) {
        this.userName = userName;
    }

    public Users(String userName, String passWord) {
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userName == null && other.userName != null) || (this.userName != null && !this.userName.equals(other.userName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.Users[ userName=" + userName + " ]";
    }

    public List<Ansvarig> getAnsvariga() {
        return ansvariga;
    }

    public void setAnsvariga(List<Ansvarig> ansvariga) {
        this.ansvariga = ansvariga;
    }
}
