package com.robot.application.segmentWord.impl;


import com.google.common.collect.Lists;
import com.robot.application.segmentWord.MyIKAnalyzer;
import com.robot.bean.Word;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Created by xing on 2016/9/21.
 */
@Service
public class IKAnalyzerImpl implements MyIKAnalyzer {
    @Override
    public List<Word> getWordList(String sentene, int segementWordTye) {
        List<Word> words = Lists.newArrayList();
        switch (segementWordTye) {
            case 1:
                words = getMaxWordList(sentene);
                break;
            case 2:
                words = getMinWordList(sentene);
                break;
            default:
            {}
        }
        return words;
    }

    public List<Word> getMaxWordList(String sentence){
        Dictionary.initial(MyConfig.getInstance());
        List<Word> wordList = Lists.newArrayList();
        Analyzer analyzer = new IKAnalyzer(true);
        StringReader stringReader = new StringReader(sentence);
        TokenStream ts = null;
        try {
            ts = analyzer.tokenStream("", stringReader);
            ts.reset();
            CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
            while(ts.incrementToken()){
                wordList.add(new Word(term.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (ts != null) {
                try {
                    ts.end();
                    ts.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return wordList;
    }

    public List<Word> getMinWordList(String sentence){
        Dictionary.initial(MyConfig.getInstance());
        List<Word> wordList = Lists.newArrayList();
        Analyzer analyzer = new IKAnalyzer(false);
        StringReader stringReader = new StringReader(sentence);
        TokenStream ts = null;
        try {
            ts = analyzer.tokenStream("", stringReader);
            ts.reset();
            CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
            while(ts.incrementToken()){
                wordList.add(new Word(term.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (ts != null) {
                try {
                    ts.end();
                    ts.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return wordList;
    }

    public static void main(String[] args){
        // 构建IK分词器，使用smart分词模式
        Analyzer analyzer = new IKAnalyzer(true);

        // 获取Lucene的TokenStream对象
        TokenStream ts = null;
        try {
            ts = analyzer.tokenStream("myfield", new StringReader(
                    "这是一个中文分词的例子，你可以直接运行它！IKAnalyer can analysis english text too"));
            // 获取词元位置属性
            OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
            // 获取词元文本属性
            CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
            // 获取词元文本属性
            TypeAttribute type = ts.addAttribute(TypeAttribute.class);

            // 重置TokenStream（重置StringReader）
            ts.reset();
            // 迭代获取分词结果
            while (ts.incrementToken()) {
                System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : "
                        + term.toString() + " | " + type.type());
            }
            // 关闭TokenStream（关闭StringReader）
            ts.end(); // Perform end-of-stream operations, e.g. set the final offset.

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 释放TokenStream的所有资源
            if (ts != null) {
                try {
                    ts.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
