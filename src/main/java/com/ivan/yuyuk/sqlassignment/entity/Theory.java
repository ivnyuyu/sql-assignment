package com.ivan.yuyuk.sqlassignment.entity;

import javax.persistence.*;

@Entity
@Table(name = "theory")
public class Theory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "theme")
    private String theme;
    @Column(name = "content")
    private String content;

    public Theory() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
