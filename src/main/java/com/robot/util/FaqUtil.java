package com.robot.util;

import com.robot.bean.QAEx2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xing on 2016/11/28.
 */
public class FaqUtil {
    private static FaqUtil instance;

    private static List<QAEx2> faqList;

    public FaqUtil(String faqFolderPath) {
        init(faqFolderPath);
    }

    public List<QAEx2> getFaqList(){
        return faqList;
    }

    private void init(String faqFolderPath){
            faqList = new ArrayList<QAEx2>();
            File files = new File(faqFolderPath);
            if(files.isDirectory()){
                File[] fs = files.listFiles();
                for(File f:fs){
                    String html = FileService.read(f.getAbsolutePath(), "UTF-8");
                    String q = QABeanService.getQuestion(html);
                    String a = QABeanService.getAnswer(html);
                    String tag1 = QABeanService.getTagContent(html, "tag1");
                    String tag2 = QABeanService.getTagContent(html, "tag2");
                    String[] tags1 = StringUtils.TRIM_STRING(tag1).split(",");
                    List<String> t1 = new ArrayList<String>();
                    for(String s:tags1){
                        if(s.equals("")) continue;
                        t1.add(s);
                    }
                    String[] tags2 = StringUtils.TRIM_STRING(tag2).split(",");
                    List<String> t2 = new ArrayList<String>();
                    for(String s:tags2){
                        if(s.equals("")) continue;
                        t2.add(s);
                    }
                    QAEx2 qa = new QAEx2(q, a, t1, t2);
                    faqList.add(qa);
                }
            }
    }

    public static synchronized FaqUtil getInstance(String faqFolderPath){
        if(instance == null){
            synchronized (FaqUtil.class){
                if(instance == null){
                    instance = new FaqUtil(faqFolderPath);
                }
            }
        }
        return instance;
    }

}
