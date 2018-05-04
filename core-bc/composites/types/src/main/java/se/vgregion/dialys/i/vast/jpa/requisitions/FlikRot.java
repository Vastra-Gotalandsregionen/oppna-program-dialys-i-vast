/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vgregion.dialys.i.vast.jpa.requisitions;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author clalu4
 */
@Entity
@Table(name = "flikrot")
@XmlRootElement
public class FlikRot implements Serializable {

    private static final long serialVersionUID = 1L;

    public FlikRot(String id) {
        this();
        this.id = id;
    }

    public FlikRot() {
        super();
    }

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    @OneToMany(mappedBy = "flikRot", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<Flik> fliks = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Flik> getFliks() {
        return fliks;
    }

    public void setFliks(Set<Flik> fliks) {
        this.fliks = fliks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlikRot)) return false;

        FlikRot flikRot = (FlikRot) o;

        if (id != null ? !id.equals(flikRot.id) : flikRot.id != null) return false;
        return fliks != null ? fliks.equals(flikRot.fliks) : flikRot.fliks == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fliks != null ? fliks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FlikRot{" +
                "id=" + id +
                '}';
    }

}

