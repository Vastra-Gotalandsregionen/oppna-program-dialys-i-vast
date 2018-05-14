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

}