package com.robot;

import com.robot.application.segmentWord.MySegmentWord;
import com.robot.application.synonymWord.synonymWord;
import com.robot.bean.Word;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xing on 2016/9/21.
 */
public class testIk extends BaseTest{

    @Autowired
    private MySegmentWord mySegmentWord;

    @Autowired
    private synonymWord synWord;

    @Test
    public void test(){
        String sentence = "请问我该怎么开网上认证的发票呢？请问U棒在线升级哪里操作？";
        List<Word> words = mySegmentWord.getWordList(sentence, 1);
        Iterator<Word> iterator = words.iterator();
        System.out.println("\nFen ci: ");
        while(iterator.hasNext()){
            Word w = iterator.next();
            if(w.getWord().length() <= 1){
                iterator.remove();
                continue;
            }
            System.out.print(w.getWord() + " ");
        }
        System.out.println("\nSyn words:" );
        String synDictFile = "dict/customSynonm.dic";
        Resource resource = new ClassPathResource(synDictFile);
        try {
            synDictFile = resource.getURL().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Word> words2 = synWord.getSynonymWords(words, synDictFile);
        iterator = words2.iterator();
        while(iterator.hasNext()){
            System.out.print(iterator.next().getWord() + " ");
        }
        System.out.println("\nKB:");
        String kbFile = "dict/kb.dic";
        resource = new ClassPathResource(kbFile);
        try {
            kbFile = resource.getURL().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Word> words3 = synWord.filterKB(words2, kbFile);
        iterator = words3.iterator();
        while(iterator.hasNext()){
            System.out.print(iterator.next().getWord() + " ");
        }
    }
}
