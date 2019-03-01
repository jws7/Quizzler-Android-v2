package com.londonappbrewery.quizzler;

import android.util.Log;

public class QuizModel {

    private Question[] mQuestionBank;

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    private int mIndex;
    public  int NR_OF_QUESTIONS;
    private double PERCENT_PER_QUESTION;
    private int mScore;

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public QuizModel() {

        this.mQuestionBank = new Question[]{
                new Question(R.string.question_1, true),
                new Question(R.string.question_2, true),
                new Question(R.string.question_3, true),
                new Question(R.string.question_4, true),
                new Question(R.string.question_5, true),
                new Question(R.string.question_6, false),
                new Question(R.string.question_7, true),
                new Question(R.string.question_8, false),
                new Question(R.string.question_9, true),
                new Question(R.string.question_10, true),
                new Question(R.string.question_11, false),
                new Question(R.string.question_12, false),
                new Question(R.string.question_13, true),
        };
        this.NR_OF_QUESTIONS = mQuestionBank.length;
        this.PERCENT_PER_QUESTION = Math.ceil(100.0 / NR_OF_QUESTIONS);
    }

    public boolean checkAnswer(boolean userSelection){
        Question firstQuestion = mQuestionBank[mIndex];
        boolean correctAnswer = firstQuestion.answer;
        boolean correct = correctAnswer == userSelection;
        if(correct) mScore++;
        return correct;
    }

    public int getQuestionText(){
        Log.d("Quizzler","getQuestionText()");
        Question secondQuestion = mQuestionBank[mIndex];
        return secondQuestion.questionId;
    }

    public void nextQuestion(){
        mIndex = (mIndex + 1) % NR_OF_QUESTIONS;
    }

    public int percentComplete(){
        double percentCompleted = mIndex * PERCENT_PER_QUESTION;
        return (int) percentCompleted;
    }

    public void reset(){
        mIndex = 0;
        mScore = 0;
    }

    public boolean isBeginning(){
        return mIndex == 0;
    }

}
