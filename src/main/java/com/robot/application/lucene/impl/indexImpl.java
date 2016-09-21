package com.robot.application.lucene.impl;

import com.robot.application.lucene.Index;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import java.io.File;
import java.io.IOException;

/**
 * Created by xing on 2016/9/21.
 */
public class indexImpl implements Index {
    @Override
    public int addIndex(IndexWriter iw, String name, String value, FieldType type) {
        if(!name.equals("") && !value.equals("") && type!=null && iw!=null){
            Document doc = new Document();
            doc.add(new Field(name, value, type));
            try {
                iw.addDocument(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 1;
        }
        return 0;
    }

    @Override
    public int updateIndex(IndexWriter iw, String targetField, String name, String value, FieldType type){
        if(!name.equals("") && !value.equals("") && type!=null && iw!=null){
            Document doc = new Document();
            Term term = new Term(targetField, name);
            doc.add(new Field(name, value, type));
            try {
                iw.updateDocument(term, doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 1;
        }
        return 0;
    }

    @Override
    public int rebuildAllIndex() {
        return 0;
    }

    @Override
    public int deleteIndex(File file) {
        return 0;
    }

    void deleteFolder(String folderPath){

    }
}
