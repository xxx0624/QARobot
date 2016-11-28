package com.robot.application.normalSearch;

import com.robot.bean.QAEx2;
import com.robot.bean.Word;

import java.util.List;

/**
 * Created by xing on 2016/11/28.
 */
public interface Search {
    public List<QAEx2> search(List<Word> userQuestion, String faqFolderPath, String weightDicPath);
}
