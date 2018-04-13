/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vgregion.dialys.i.vast.jpa.requisitions;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author clalu4
 */
@Entity
@Table(name = "Anstallning")
@XmlRootElement
public class Anstallning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "userName", insertable = false, updatable = false)
    private String userName;

    @Column(name = "mottagningId", insertable = false, updatable = false)
    private Integer mottagningId;

    @Id
    @ManyToOne
    @JoinColumn(name = "userName", foreignKey = @ForeignKey(name = "fk_anstallning_user"))
    @JsonIgnore
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "mottagningId", foreignKey = @ForeignKey(name = "fk_anstallning_mottagning"))
    private Mottagning mottagning;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Mottagning getMottagning() {
        return mottagning;
    }

    public void setMottagning(Mottagning mottagning) {
        this.mottagning = mottagning;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getMottagningId() {
        return mottagningId;
    }

    public void setMottagningId(Integer mottagningId) {
        this.mottagningId = mottagningId;
    }

    /*@EmbeddedId
    private Id id;

    @ManyToOne
    @JoinColumn(name = "userName")
    private User user;

    @ManyToOne
    @JoinColumn(name = "mottagningId")
    private Mottagning mottagning;

    public Anstallning() {
        super();
    }

    public Anstallning(Id id) {
        super();
        setId(id);
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Mottagning getMottagning() {
        return mottagning;
    }

    public void setMottagning(Mottagning mottagning) {
        this.mottagning = mottagning;
    }

    @Embeddable
    public static class Id implements Serializable {

        public Id() {
            super();
        }

        public Id(String userName, Integer mottagningId) {
            super();
            this.userName = userName;
            this.mottagningId = mottagningId;
        }

        @Column(name = "userName", insertable = false, updatable = false)
        private String userName;

        @Column(name = "mottagningId", insertable = false, updatable = false)
        private Integer mottagningId;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Integer getMottagningId() {
            return mottagningId;
        }

        public void setMottagningId(Integer patientId) {
            this.mottagningId = patientId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;

            Id id = (Id) o;

            if (userName != null ? !userName.equals(id.userName) : id.userName != null) return false;
            return mottagningId != null ? mottagningId.equals(id.mottagningId) : id.mottagningId == null;
        }

        @Override
        public int hashCode() {
            int result = userName != null ? userName.hashCode() : 0;
            result = 31 * result + (mottagningId != null ? mottagningId.hashCode() : 0);
            return result;
        }
    }*/

}
