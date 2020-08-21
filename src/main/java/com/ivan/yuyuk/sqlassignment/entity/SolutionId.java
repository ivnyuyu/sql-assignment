package com.ivan.yuyuk.sqlassignment.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SolutionId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "assignment_id")
    private Long assignmentId;

    public SolutionId() {}

    public SolutionId(Long userId, Long assignmentId) {
        this.userId = userId;
        this.assignmentId = assignmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolutionId that = (SolutionId) o;
        return userId.equals(that.userId) &&
                assignmentId.equals(that.assignmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, assignmentId);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }
}
