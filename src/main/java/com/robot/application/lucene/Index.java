package com.robot.application.lucene;

import com.robot.bean.QA;
import org.apache.lucene.index.IndexWriter;

/**
 * Created by xing on 2016/9/21.
 */
public interface Index {
    int addIndex(QA qa, String indexDirPath, IndexWriter iw);

    int updateIndex(QA qa, String indexDirPath);

    int deleteIndex(QA qa, String indexDirPath);

    int rebuildAllIndex(String faqFolderPath, String indexFolderPath);

    int deleteAllIndex(String indexFolderPath);
}
