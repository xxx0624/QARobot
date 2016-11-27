package com.robot.po;

/**
 * Created by xing on 2016/11/4.
 */
public class QA {

    private String question;
    private String answer;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setQaId(Integer id) {
        this.id = id;
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
