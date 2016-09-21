package com.robot;

import com.robot.application.segmentWord.MyIKAnalyzer;
import com.robot.bean.Word;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xing on 2016/9/21.
 */
public class testIk extends BaseTest{

    @Autowired
    private MyIKAnalyzer myIKAnalyzer;

    @Test
    public void test(){
        String sentence = "你是我的小苹果";
        List<Word> words = myIKAnalyzer.getWordList(sentence, 1);
        for(Word w:words){
            System.out.println(w.getWord());
        }
    }
}
