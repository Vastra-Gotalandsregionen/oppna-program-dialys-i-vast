package se.vgregion.dialys.i.vast.jpa;

import javax.persistence.*;

@Entity
@Table(name = "dummy")
public class Dummy extends AbstractEntity {

    @Id
    @Column (name = "id", nullable = false)
    private Integer id;

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}