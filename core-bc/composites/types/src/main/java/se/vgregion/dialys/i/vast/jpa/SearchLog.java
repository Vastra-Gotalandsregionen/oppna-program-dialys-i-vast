package se.vgregion.dialys.i.vast.jpa;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "searchlog")
public class SearchLog extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date")
    private Date date = new Date();

    @Column(name = "username")
    private String userName;

    @Column(name = "filter")
    private String filter;

    @Column(name = "result", columnDefinition = "TEXT")
    private String result;

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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchLog)) return false;

        SearchLog searchLog = (SearchLog) o;

        if (id != null ? !id.equals(searchLog.id) : searchLog.id != null) return false;
        if (date != null ? !date.equals(searchLog.date) : searchLog.date != null) return false;
        if (userName != null ? !userName.equals(searchLog.userName) : searchLog.userName != null) return false;
        if (filter != null ? !filter.equals(searchLog.filter) : searchLog.filter != null) return false;
        return result != null ? result.equals(searchLog.result) : searchLog.result == null;
    }

    @Override
    public int hashCode() {
        int result1 = id != null ? id.hashCode() : 0;
        result1 = 31 * result1 + (date != null ? date.hashCode() : 0);
        result1 = 31 * result1 + (userName != null ? userName.hashCode() : 0);
        result1 = 31 * result1 + (filter != null ? filter.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

}