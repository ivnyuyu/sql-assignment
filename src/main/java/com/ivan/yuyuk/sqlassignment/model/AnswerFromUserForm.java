package com.ivan.yuyuk.sqlassignment.model;

import java.io.Serializable;

public class AnswerFromUserForm implements Serializable {

    private Long id;
    private String answer;
    private ResponseStatus status;

    public AnswerFromUserForm() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ResponseStatus getStatus() {
        if(status == null) {
            return ResponseStatus.EMPTY;
        }
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}


