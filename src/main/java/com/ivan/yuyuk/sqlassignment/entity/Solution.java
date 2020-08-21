package com.ivan.yuyuk.sqlassignment.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "solution")
public class Solution {

    @EmbeddedId
    private SolutionId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("assignmentId")
    private Assignment assignment;

    @Column(name = "answer_date")
    private Date createdOn = new Date();

    @Column(name = "answer")
    private String answer;

    public Solution() {}

    public Solution(SolutionId id, String answer) {
        this.id = id;
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return id.equals(solution.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public SolutionId getId() {
        return id;
    }

    public void setId(SolutionId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", user=" + user +
                ", assignment=" + assignment +
                ", createdOn=" + createdOn +
                ", answer='" + answer + '\'' +
                '}';
    }
}
