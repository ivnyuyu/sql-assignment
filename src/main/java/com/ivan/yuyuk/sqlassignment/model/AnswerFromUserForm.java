package com.ivan.yuyuk.sqlassignment.model;

import java.io.Serializable;

public class AnswerFromUserForm implements Serializable {

    private Long id;
    private String answer;
    private boolean isCorrectAnswer;
    private boolean isUserSubmitAnswer;

    public AnswerFromUserForm(){

    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }

    public boolean isUserSubmitAnswer() {
        return isUserSubmitAnswer;
    }

    public void setUserSubmitAnswer(boolean userSubmitAnswer) {
        isUserSubmitAnswer = userSubmitAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
