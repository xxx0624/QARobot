package com.robot.bean;

import java.util.Comparator;
import java.util.List;

/**
 * Created by xing on 2016/11/28.
 */
public class QAEx2 implements Comparable{
    private String question;
    private String answer;
    private double score;
    private List<String> tags1;
    private List<String> tags2;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getTags1() {
        return tags1;
    }

    public void setTags1(List<String> tags1) {
        this.tags1 = tags1;
    }

    public List<String> getTags2() {
        return tags2;
    }

    public void setTags2(List<String> tags2) {
        this.tags2 = tags2;
    }

    public QAEx2(String q, String a, List<String> tags1, List<String> tags2){
        this.question = q;
        this.answer = a;
        this.tags1 = tags1;
        this.tags2 = tags2;
    }

    @Override
    public int compareTo(Object o) {
        QAEx2 qa = (QAEx2) o;
        if(score > qa.getScore()){
            return -1;
        }
        if(score == qa.getScore()){
            return 0;
        }
        return 1;
    }
}
