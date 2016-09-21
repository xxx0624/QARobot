package com.robot.application.lucene;

import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;

import java.io.File;

/**
 * Created by xing on 2016/9/21.
 */
public interface Index {
    int addIndex(IndexWriter iw, String name, String value, FieldType type);

    int updateIndex(IndexWriter iw, String targetField, String name, String value, FieldType type);

    int rebuildAllIndex();

    int deleteIndex(File file);
}
