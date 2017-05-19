package com.example.android.budapestquizapp;

/**
 * Created by MátéZoltán on 2017. 05. 18..
 */

public class Question {

    private int mQuestionType;

    static public int RADIO = 0;

    static public int CHECK = 1;

    static public int TEXT = 2;

    private String mQuestion;

    private String[] mAnswers = new String[4];

    private int mImageResourceID;

    private String mRightAnswer;

    private String[] mRightAnswers = new String[2];


    public Question (int type, String question, String[] answers, int imageID, String rightAnswer){
        mQuestionType = type;
        mQuestion = question;
        mAnswers = answers;
        mImageResourceID = imageID;
        mRightAnswer = rightAnswer;

    }

    public Question (int type, String question, String[] answers, int imageID, String[] rightAnswers){
        mQuestionType = type;
        mQuestion = question;
        mAnswers = answers;
        mImageResourceID = imageID;
        mRightAnswers = rightAnswers;

    }

    public Question (int type, String question, int imageID, String rightAnswer){
        mQuestionType = type;
        mQuestion = question;
        mImageResourceID = imageID;
        mRightAnswer = rightAnswer;

    }

    public int getType(){
        return mQuestionType;
    }


    public String getQuestion(){
        return mQuestion;
    }

    public String[] getAnswers(){
        return mAnswers;
    }

    public int getImageResourceID(){
        return mImageResourceID;
    }

    public String getRightAnswer(){
        return mRightAnswer;
    }

    public String[] getRightAnswers(){
        return mRightAnswers;
    }

}
