package com.robot.application.lucene.util;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * Created by xing on 2016/10/4.
 */
public class IndexWriterGetter {

    //@Autowired
    //private MyIKAnalyzer ik;

    public org.apache.lucene.index.IndexWriter getIndexWriter(String indexDirPath) {
        Analyzer ik = new IKAnalyzer(true);

        File indexDir = new File(indexDirPath);
        Directory indexDirectory = null;
        try {
            indexDirectory = FSDirectory.open(indexDir);
        } catch (IOException e) {
            // TODO: 2016/10/5  
            e.printStackTrace();
        }
        IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_47, ik);
        try {
            return new org.apache.lucene.index.IndexWriter(indexDirectory, iwConfig);
        } catch (IOException e) {
            // TODO: 2016/10/5  
            e.printStackTrace();
        }
        return null;
    }
}
