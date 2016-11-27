package com.robot.application.synonymWord.impl;

import com.google.common.base.CharMatcher;
import com.robot.application.synonymWord.synonymWord;
import com.robot.bean.Word;
import com.robot.util.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import com.google.common.*;

/**
 * Created by xing on 2016/11/27.
 */
@Service
public class synonymWordImpl implements synonymWord {

    @Override
    public List<Word> getSynonymWords(List<Word> words, String synonymWordFilePath) {
        // TODO: 2016/11/27 同义词库存入内存中
        List<Word> newWords = new ArrayList<Word>();
        //1. load syn words
        File testFile = new File(synonymWordFilePath);
        if(!testFile.isFile()){
            System.err.println("[Error] synonymWords file not exist.");
            return words;
        }
        Map<String, Integer> wordsMap = new HashMap<String, Integer>();
        List<String> synWords = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(synonymWordFilePath), "UTF-8"));
            String line = "";
            int index = 0;
            while((line = br.readLine()) != null){
                line = StringUtils.TRIM_STRING(line);
                if(line == "") break;
                String[] tempWords = line.split(",");
                for(int k = 0; k < tempWords.length; k ++){
                    tempWords[k] = StringUtils.TRIM_STRING(tempWords[k]);
                }
                if(tempWords.length <= 1) break;
                synWords.add(line);
                wordsMap.put(tempWords[0], index);
                index += 1;
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.err.println("[Error] buffer read synonymWords error...");
            e.printStackTrace();
            return words;
        } catch (IOException e) {
            System.err.println("[Error] buffer readline synonymWords error...");
            e.printStackTrace();
            return words;
        }
        //2. get result
        Set<String> wordsSet = new HashSet<String>();
        for(int i = 0; i < words.size(); i ++){
            String curWord = StringUtils.TRIM_STRING(words.get(i).getWord());
            if(!wordsSet.contains(curWord)){
                wordsSet.add(curWord);
                newWords.add(new Word(curWord));
                //expand syn words
                if(wordsMap.containsKey(curWord)){
                    int index = wordsMap.get(curWord);
                    String[] tempSynWords = synWords.get(index).split(",");
                    for(int j = 0; j < tempSynWords.length; j ++){
                        tempSynWords[j] = StringUtils.TRIM_STRING(tempSynWords[j]);
                        if(j == 0 || tempSynWords[j] == "") continue;
                        if(!wordsSet.contains(tempSynWords[j])){
                            wordsSet.add(tempSynWords[j]);
                            newWords.add(new Word(tempSynWords[j]));
                        }
                    }
                }
            }
        }
        return newWords;
    }

    @Override
    public List<Word> filterKB(List<Word> words, String kbFilePath) {
        // TODO: 2016/11/27 kb规则文件导入内存
        List<Word> newWords = new ArrayList<Word>();
        File testFile = new File(kbFilePath);
        if(!testFile.isFile()){
            System.err.println("[Error] kb file not exist.");
            return words;
        }
        Map<String, Integer> wordsMap = new HashMap<String, Integer>();
        for(int i = 0; i < words.size(); i ++){
            wordsMap.put(words.get(i).getWord(), i);
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(kbFilePath), "UTF-8"));
            Map<Integer, Integer> removeWords = new HashMap<Integer, Integer>();
            Set<String> wordsSet = new HashSet<String>();
            String line = "";
            while((line = br.readLine()) != null){
                String[] tempWords = line.split(",");
                for(int k = 0; k < tempWords.length; k ++){
                    tempWords[k] = StringUtils.TRIM_STRING(tempWords[k]);
                }
                if(tempWords.length != 3) continue;
                if(wordsMap.containsKey(tempWords[0]) && wordsMap.containsKey(tempWords[1])){
                    if(!wordsSet.contains(tempWords[2])) {
                        wordsSet.add(tempWords[2]);
                        newWords.add(new Word(tempWords[2]));
                    }
                    removeWords.put(wordsMap.get(tempWords[0]), 1);
                    removeWords.put(wordsMap.get(tempWords[1]), 1);
                }
            }
            for(int i = 0; i < words.size(); i ++){
                if(!wordsSet.contains(words.get(i)) && !removeWords.containsKey(i)){
                    newWords.add(new Word(words.get(i).getWord()));
                    wordsSet.add(words.get(i).getWord());
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.err.println("[Error] buffer read kb file error...");
            e.printStackTrace();
            return words;
        } catch (IOException e) {
            System.err.println("[Error] buffer readline kb file error...");
            e.printStackTrace();
            return words;
        }
        return newWords;
    }
}
