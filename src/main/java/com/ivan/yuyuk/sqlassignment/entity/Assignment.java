package com.ivan.yuyuk.sqlassignment.entity;

import javax.persistence.*;

@Entity
@Table(name = "assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "correct_query")
    private String correctQuery;

    public Assignment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCorrectQuery() {
        return correctQuery;
    }

    public void setCorrectQuery(String correctQuery) {
        this.correctQuery = correctQuery;
    }
}
