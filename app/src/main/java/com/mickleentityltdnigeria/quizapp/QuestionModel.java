package com.mickleentityltdnigeria.quizapp;

public class QuestionModel {

    private String QuestionString;
    private String Answer;

    public QuestionModel(String questionString, String answer) {
        this.QuestionString = questionString;
        this.Answer = answer;
    }

    public String getQuestionString() {
        return this.QuestionString;
    }

    public void setQuestionString(String questionString) {
        this.QuestionString = questionString;
    }

    public String getAnswer() {
        return this.Answer;
    }

    public void setAnswer(String answer) {
        this.Answer = answer;
    }
}
