package com.robot.application.segmentWord;

import com.robot.bean.Word;

import java.util.List;

/**
 * Created by xing on 2016/9/21.
 */
public interface MySegmentWord {
    /*
        1:最大词长分词
        2:最细粒度分词
    */
    List<Word> getWordList(String sentene, int segementWordTye);


}
