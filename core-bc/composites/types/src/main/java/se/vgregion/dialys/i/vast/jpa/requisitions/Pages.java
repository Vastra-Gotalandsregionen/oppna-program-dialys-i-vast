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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author clalu4
 */
@Entity
@Table(name = "Pages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pages.findAll", query = "SELECT p FROM Pages p")
    , @NamedQuery(name = "Pages.findById", query = "SELECT p FROM Pages p WHERE p.id = :id")
    , @NamedQuery(name = "Pages.findByUrl", query = "SELECT p FROM Pages p WHERE p.url = :url")
    , @NamedQuery(name = "Pages.findByCollection", query = "SELECT p FROM Pages p WHERE p.collection = :collection")
    , @NamedQuery(name = "Pages.findByDescription", query = "SELECT p FROM Pages p WHERE p.description = :description")
    , @NamedQuery(name = "Pages.findByTicketTime", query = "SELECT p FROM Pages p WHERE p.ticketTime = :ticketTime")})
public class Pages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "URL")
    private String url;
    @Size(max = 50)
    @Column(name = "Collection")
    private String collection;
    @Size(max = 255)
    @Column(name = "Description")
    private String description;
    @Column(name = "TicketTime")
    private Integer ticketTime;

    public Pages() {
    }

    public Pages(Integer id) {
        this.id = id;
    }

    public Pages(Integer id, String url) {
        this.id = id;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Pages)) {
            return false;
        }
        Pages other = (Pages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vgregion.dialys.i.vast.jpa.requisitions.Pages[ id=" + id + " ]";
    }
    
}
