package se.vgregion.dialys.i.vast.jpa;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "viewlog")
public class ViewLog extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id", nullable = false)
    private Integer id;

    @Column(name = "date")
    private Date date = new Date();

    @Column(name = "username")
    private String userName;

    @Column(name = "patientid")
    private Integer patientId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}