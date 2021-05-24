package com.mitramandiri.test.entities;

import javax.persistence.*;

@Table(name = "position")
@Entity
public class Position extends AbstractEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code",length = 50, nullable = false)
    private String code;

    @Column(name = "name",length = 100, nullable = false)
    private String name;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
