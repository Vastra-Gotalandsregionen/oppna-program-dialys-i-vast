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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author clalu4
 */
@Entity
@Table(name = "RolesPages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolesPages.findAll", query = "SELECT r FROM RolesPages r")
    , @NamedQuery(name = "RolesPages.findById", query = "SELECT r FROM RolesPages r WHERE r.id = :id")
    , @NamedQuery(name = "RolesPages.findByRolesID", query = "SELECT r FROM RolesPages r WHERE r.rolesID = :rolesID")
    , @NamedQuery(name = "RolesPages.findByPagesID", query = "SELECT r FROM RolesPages r WHERE r.pagesID = :pagesID")
    , @NamedQuery(name = "RolesPages.findByRightsLevel", query = "SELECT r FROM RolesPages r WHERE r.rightsLevel = :rightsLevel")
    , @NamedQuery(name = "RolesPages.findByTicketTime", query = "SELECT r FROM RolesPages r WHERE r.ticketTime = :ticketTime")})
public class RolesPages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RolesID")
    private int rolesID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PagesID")
    private int pagesID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RightsLevel")
    private int rightsLevel;
    @Column(name = "TicketTime")
    private Integer ticketTime;

    public RolesPages() {
    }

    public RolesPages(Integer id) {
        this.id = id;
    }

    public RolesPages(Integer id, int rolesID, int pagesID, int rightsLevel) {
        this.id = id;
        this.rolesID = rolesID;
        this.pagesID = pagesID;
        this.rightsLevel = rightsLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRolesID() {
        return rolesID;
    }

    public void setRolesID(int rolesID) {
        this.rolesID = rolesID;
    }

    public int getPagesID() {
        return pagesID;
    }

    public void setPagesID(int pagesID) {
        this.pagesID = pagesID;
    }

    public int getRightsLevel() {
        return rightsLevel;
    }

    public void setRightsLevel(int rightsLevel) {
        this.rightsLevel = rightsLevel;
    }

    public Integer getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(Integer ticketTime) {
        this.ticketTime = ticketTime;
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
        if (!(object instanceof RolesPages)) {
            return false;
        }
        RolesPages other = (RolesPages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.RolesPages[ id=" + id + " ]";
    }
    
}
