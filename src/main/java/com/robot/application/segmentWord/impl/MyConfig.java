package com.robot.application.segmentWord.impl;

import org.wltea.analyzer.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

/**
 * Created by xing on 2016/9/21.
 */
public class MyConfig implements Configuration {
    /*
  * 分词器默认字典路径
  */
    private static final String PATH_DIC_MAIN = "resources/dict/main.dic";
    private static final String PATH_DIC_QUANTIFIER = "resources/dict/quantifier.dic";
    private static final String PATH_DIC_MYDICT = "resources/dict/mydict.dic";

    /*
     * 分词器配置文件路径
     */
    private static final String FILE_NAME = "resources/config/local/IKAnalyzer.cfg.xml";
    // 配置属性——扩展字典
    private static final String EXT_DICT = "ext_dict";
    // 配置属性——扩展停止词典
    private static final String EXT_STOP = "ext_stopwords";

    private Properties props;
    /*
     * 是否使用smart方式分词
     */
    private boolean useSmart;

    /**
     * 返回单例
     * @return Configuration单例
     */
    public static Configuration getInstance() {
        return new MyConfig();
    }

    /*
     * 初始化配置文件
     */
    private MyConfig() {
        props = new Properties();

        InputStream input = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
        if (input != null) {
            try {
                props.loadFromXML(input);
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回useSmart标志位
     * useSmart =true ，分词器使用智能切分策略， =false则使用细粒度切分
     * @return useSmart
     */
    public boolean useSmart() {
        return useSmart;
    }

    /**
     * 设置useSmart标志位
     * useSmart =true ，分词器使用智能切分策略， =false则使用细粒度切分
     * @param useSmart
     */
    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    /**
     * 获取主词典路径
     *
     * @return String 主词典路径
     */
    public String getMainDictionary() {
        return PATH_DIC_MAIN;
    }

    /**
     * 获取量词词典路径
     * @return String 量词词典路径
     */
    public String getQuantifierDicionary() {
        return PATH_DIC_QUANTIFIER;
    }

    /**
     * 获取扩展字典配置路径
     * @return List<String> 相对类加载器的路径
     */
    public List<String> getExtDictionarys() {
        List<String> extDictFiles = new ArrayList<String>(2);
        String extDictCfg = props.getProperty(EXT_DICT);
        if (extDictCfg != null) {
            // 使用;分割多个扩展字典配置
            String[] filePaths = extDictCfg.split(";");
            if (filePaths != null) {
                for (String filePath : filePaths) {
                    if (filePath != null && !"".equals(filePath.trim())) {
                        extDictFiles.add(filePath.trim());
                    }
                }
            }
        }
        return extDictFiles;
    }

    /**
     * 获取扩展停止词典配置路径
     * @return List<String> 相对类加载器的路径
     */
    public List<String> getExtStopWordDictionarys() {
        List<String> extStopWordDictFiles = new ArrayList<String>(2);
        String extStopWordDictCfg = props.getProperty(EXT_STOP);
        if (extStopWordDictCfg != null) {
            // 使用;分割多个扩展字典配置
            String[] filePaths = extStopWordDictCfg.split(";");
            if (filePaths != null) {
                for (String filePath : filePaths) {
                    if (filePath != null && !"".equals(filePath.trim())) {
                        extStopWordDictFiles.add(filePath.trim());
                    }
                }
            }
        }
        return extStopWordDictFiles;
    }
}
