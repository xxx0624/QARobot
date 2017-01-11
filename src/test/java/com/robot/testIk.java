package com.robot;

import com.robot.application.normalSearch.Search;
import com.robot.application.segmentWord.MySegmentWord;
import com.robot.application.synonymWord.synonymWord;
import com.robot.bean.QAEx2;
import com.robot.bean.Word;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by xing on 2016/9/21.
 */
public class testIk extends BaseTest{

    @Autowired
    private MySegmentWord mySegmentWord;

    @Autowired
    private synonymWord synWord;

    @Autowired
    private Search search;

    @Test
    public void testManyTimes(){
        Scanner cin = new Scanner(System.in);
        String sentence = "";
        while(cin.hasNext()){
            sentence = cin.next();
            System.out.println(sentence);
        }
    }

    @Test
    public void test(){
        String[] sentenceList = {
                "我已经报完税，现在做清卡，但是不行。怎么办？",
                "什么是抄税清单",
                "增值税开票软件哪里下载？",
                "抄报数据上传失败，出现汇总和明细比对不一致，报税失败。问题出在哪里，怎么回事",
                "我已成功重报了增值税表，还要进行远程抄报吗？？",
                "抄税清卡可以在18号之后吗？",

        };
        int sentence_id = 0;
        for(String sentence:sentenceList) {
            List<Word> words = mySegmentWord.getWordList(sentence, 1);
            Iterator<Word> iterator = words.iterator();
            System.out.println("\nFen ci: ");
            while (iterator.hasNext()) {
                Word w = iterator.next();
                if (w.getWord().length() <= 1) {
                    iterator.remove();
                    continue;
                }
                System.out.print(w.getWord() + " ");
            }
            System.out.println("\nSyn words:");
            String synDictFile = "dict/customSynonm-excel.dic";
            Resource resource = new ClassPathResource(synDictFile);
            try {
                synDictFile = resource.getURL().getPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Word> words2 = synWord.getSynonymWords(words, synDictFile);
            iterator = words2.iterator();
            while (iterator.hasNext()) {
                System.out.print(iterator.next().getWord() + " ");
            }
            System.out.println("\nKB:");
            String kbFile = "dict/kb-excel.dic";
            resource = new ClassPathResource(kbFile);
            try {
                kbFile = resource.getURL().getPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Word> words3 = synWord.filterKB(words2, kbFile);
            iterator = words3.iterator();
            while (iterator.hasNext()) {
                System.out.print(iterator.next().getWord() + " ");
            }
            System.out.println();
            //start search
            String faqFolderPath = "document-excel";
            resource = new ClassPathResource(faqFolderPath);
            try {
                faqFolderPath = resource.getURL().getPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String weightDictPath = "dict/weight-excel.dic";
            resource = new ClassPathResource(weightDictPath);
            try {
                weightDictPath = resource.getURL().getPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<QAEx2> ans = search.search(words3, faqFolderPath, weightDictPath);
            Iterator<QAEx2> it = ans.iterator();
            System.out.println("["+sentence_id+"]Question:" + sentence);
            System.out.println("search result size = " + ans.size());
            int log_cnt = 0;
            while (it.hasNext()) {
                QAEx2 qa = it.next();
                System.out.println("[" + qa.getScore() + "]" + qa.getQuestion() + "\n" + qa.getAnswer() + "\n");
                log_cnt += 1;
                if (log_cnt >= 3) break;
            }
        }
    }
}
