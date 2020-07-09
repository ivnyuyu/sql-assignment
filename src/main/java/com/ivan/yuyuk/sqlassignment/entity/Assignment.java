package com.ivan.yuyuk.sqlassignment.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Assignment")
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

    @JoinColumn(name = "user_loader")
    @ManyToOne(fetch = FetchType.EAGER)
    private User userLoader;

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

    public User getUserLoader() {
        return userLoader;
    }

    public void setUserLoader(User userLoader) {
        this.userLoader = userLoader;
    }
}
