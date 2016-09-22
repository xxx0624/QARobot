package com.robot.application.lucene;

import com.robot.bean.QA;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;

import java.io.File;

/**
 * Created by xing on 2016/9/21.
 */
public interface Index {
    int addIndex(IndexWriter iw, QA qa);

    int updateIndex(IndexWriter iw, QA qa);

    int deleteIndex(IndexWriter iw, QA qa);

    int rebuildAllIndex(String faqFolderPath, String indexFolderPath, IndexWriter iw);

    int deleteAllIndex(String indexFolderPath);
}
