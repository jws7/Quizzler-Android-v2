package com.londonappbrewery.quizzler;

public class Question {

    public int questionId;
    public boolean answer;

    public Question(int questionResourceId, boolean trueOrFalse){
        this.questionId = questionResourceId;
        this.answer = trueOrFalse;
    }
}
