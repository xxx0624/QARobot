package com.robot.application.normalSearch.impl;

import com.robot.application.normalSearch.Search;
import com.robot.bean.QAEx2;
import com.robot.bean.Word;
import com.robot.util.FaqUtil;
import com.robot.util.WeightDicUtil;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Collections.sort;

/**
 * Created by xing on 2016/11/28.
 */
@Service
public class SearchImpl implements Search {
    @Override
    public List<QAEx2> search(List<Word> userQuestion, String faqFolderPath, String weightDicPath) {

        FaqUtil faqUtil = FaqUtil.getInstance(faqFolderPath);
        List<QAEx2> faqlist = faqUtil.getFaqList();

        WeightDicUtil weightDicUtil = WeightDicUtil.getInstance(weightDicPath);
        Map<String, Double> weightDic = weightDicUtil.getWeightDic();

        List<QAEx2> ans = new ArrayList<QAEx2>();
        Iterator<QAEx2> iterator = faqlist.iterator();
        while(iterator.hasNext()){
            QAEx2 curQA = iterator.next();
            //rule
            int cnt = 0;
            double curScore = 0.0;
            double part1Score = 0.0;
            double part2Score = 0.0;
            for(int i = 0; i < userQuestion.size(); i ++){
                String w1 = userQuestion.get(i).getWord();
                for(int j = 0; j < curQA.getTags1().size(); j ++){
                    String w2 = curQA.getTags1().get(j);
                    if(w1.equals(w2)) {
                        cnt += 1;
                        if (weightDic.containsKey(w1)){
                            part1Score += weightDic.get(w1).doubleValue();
                        }
                    }
                }
                for(int j = 0; j < curQA.getTags2().size(); j ++){
                    String w2 = curQA.getTags2().get(j);
                    if(w1.equals(w2)) {
                        cnt += 1;
                        if (weightDic.containsKey(w1)){
                            part2Score += weightDic.get(w1).doubleValue();
                        }
                    }
                }
            }
            //tags1 is more important than tags
            curScore = 10.0*part1Score + part2Score;
            if(cnt == (curQA.getTags1().size() + curQA.getTags2().size())){
                curQA.setScore(curScore);
                ans.add(curQA);
            }
        }
        sort(ans);
        return ans;
    }

    private boolean checkIn(List<Word> userQ, String w){
        for(int i = 0; i < userQ.size(); i ++){
            Word cw = userQ.get(i);
            if(cw.getWord().equals(w)){
                return true;
            }
        }
        return false;
    }
}
