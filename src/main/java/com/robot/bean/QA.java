package com.robot.bean;

/**
 * Created by xing on 2016/9/21.
 */
public class QA {
    public static class Field{
        public static final String QUESTION = "question";
        public static final String ANSWER = "answer";
        public static final String QAID = "qaId";
    }

    private String question;
    private String answer;
    private String qaId;

    public String getQaId() {
        return qaId;
    }

    public void setQaId(String qaId) {
        this.qaId = qaId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
