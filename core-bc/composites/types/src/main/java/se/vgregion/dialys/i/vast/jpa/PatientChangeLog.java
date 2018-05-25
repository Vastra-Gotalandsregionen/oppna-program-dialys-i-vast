package se.vgregion.dialys.i.vast.jpa;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PatientChangeLog")
public class PatientChangeLog extends AbstractEntity {

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

    @Column(name = "data", columnDefinition = "TEXT")
    private String data;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientChangeLog)) return false;

        PatientChangeLog viewLog = (PatientChangeLog) o;

        if (id != null ? !id.equals(viewLog.id) : viewLog.id != null) return false;
        if (date != null ? !date.equals(viewLog.date) : viewLog.date != null) return false;
        if (userName != null ? !userName.equals(viewLog.userName) : viewLog.userName != null) return false;
        return patientId != null ? patientId.equals(viewLog.patientId) : viewLog.patientId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (patientId != null ? patientId.hashCode() : 0);
        return result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}