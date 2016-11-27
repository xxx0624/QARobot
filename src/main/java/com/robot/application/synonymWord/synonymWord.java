package com.robot.application.synonymWord;

import com.robot.bean.Word;

import java.util.List;

/**
 * Created by xing on 2016/11/27.
 */
public interface synonymWord {
    public List<Word> getSynonymWords(List<Word> words, String synonymWordFilePath);

    public List<Word> filterKB(List<Word> words, String kbFilePath);
}
