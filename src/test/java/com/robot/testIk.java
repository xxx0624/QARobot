package com.robot;

import com.robot.application.segmentWord.MySegmentWord;
import com.robot.bean.Word;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xing on 2016/9/21.
 */
public class testIk extends BaseTest{

    @Autowired
    private MySegmentWord mySegmentWord;

    @Test
    public void test(){
        String sentence = "这是一个中文分词的例子，你可以直接运行它！";
        List<Word> words = mySegmentWord.getWordList(sentence, 1);
        for(Word w:words){
            System.out.println(w.getWord());
        }
    }
}
