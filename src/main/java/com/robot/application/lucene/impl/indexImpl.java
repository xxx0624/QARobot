package com.robot.application.lucene.impl;

import com.carrotsearch.ant.tasks.junit4.dependencies.com.google.common.collect.Lists;
import com.robot.application.lucene.Index;
import com.robot.application.lucene.util.IndexWriterGetter;
import com.robot.bean.QA;
import com.robot.util.FileService;
import com.robot.util.QABeanService;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by xing on 2016/9/21.
 */
@Service
public class indexImpl implements Index {

    private static Logger logger = LoggerFactory.getLogger(indexImpl.class);

    @Override
    public int addIndex(QA qa, String indexDirPath, IndexWriter iw) {
        //IndexWriter iw = new IndexWriterGetter().getIndexWriter(indexDirPath);
        if (iw == null) {
            logger.error("add index fail");
            return 0;
        }
        if (qa != null) {
            Document doc = new Document();
            List<Field> fieldList = getFieldList(qa);
            for (Field field : fieldList) {
                doc.add(field);
            }
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
    public int updateIndex(QA qa, String indexDirPath) {
        IndexWriter iw = new IndexWriterGetter().getIndexWriter(indexDirPath);
        if (iw == null) {
            logger.error("update index fail");
            return 0;
        }
        if (qa != null) {
            Document doc = new Document();
            List<Field> fieldList = getFieldList(qa);
            Term term = new Term(QA.Field.QAID, qa.getQaId());
            for (Field field : fieldList) {
                doc.add(field);
            }
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
    public int deleteIndex(QA qa, String indexDirPath) {
        IndexWriter iw = new IndexWriterGetter().getIndexWriter(indexDirPath);
        if (iw == null) {
            logger.error("delete index fail");
            return 0;
        }
        if (qa != null) {
            List<Field> fieldList = getFieldList(qa);
            Term term = new Term(QA.Field.QAID, qa.getQaId());
            try {
                iw.deleteDocuments(term);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 1;
        }
        return 0;
    }

    @Override
    public int rebuildAllIndex(String faqFolderPath, String indexFolderPath) {
        IndexWriter iw = new IndexWriterGetter().getIndexWriter(indexFolderPath);
        if (iw == null) {
            logger.error("add allIndex fail");
            return 0;
        }
        logger.info("开始删除索引...", indexFolderPath);
        File fileFaq = new File(faqFolderPath);
        deleteAllIndex(indexFolderPath);
        logger.info("删除索引成功...", indexFolderPath);
        logger.info("开始重建索引...", faqFolderPath);
        int cnt = indexFolder(faqFolderPath, indexFolderPath, iw);
        logger.info("索引重建完成，重建{}条", cnt);
        return cnt;
    }

    @Override
    public int deleteAllIndex(String indexFolderPath) {
        FileService.deleteFolder(indexFolderPath);
        return 1;
    }

    int indexFolder(String path, String indexDirPath, IndexWriter iw) {
        if (iw == null) {
            logger.error("index the folder fail");
            return 0;
        }
        File file = new File(path);
        int cnt = 0;
        if (file.isFile()) {
            logger.info("正在建索引:{}", file.getAbsolutePath());
            QA qa = new QA();
            String html = FileService.read(path, "utf-8");
            qa.setQuestion(QABeanService.getQuestion(html));
            qa.setAnswer(QABeanService.getAnswer(html));
            qa.setQaId(FileService.getStringMD5String(qa.getQuestion() + qa.getAnswer()));
            cnt += addIndex(qa, indexDirPath, iw);
        } else {
            String[] files = file.list();
            for (int i = 0; i < files.length; i++) {
                cnt += indexFolder(path + '\\' + files[i], indexDirPath, iw);
            }
        }
        return cnt;
    }


    List<Field> getFieldList(QA qa) {
        List<Field> fieldList = Lists.newArrayList();
        Field field1 = new Field(QA.Field.QUESTION, preSolve(qa.getQuestion()), TextField.TYPE_STORED);
        Field field2 = new Field(QA.Field.ANSWER, preSolve(qa.getQuestion()), TextField.TYPE_STORED);
        Field field3 = new Field(QA.Field.QAID, qa.getQaId(), TextField.TYPE_STORED);
        fieldList.add(field3);
        fieldList.add(field1);
        fieldList.add(field2);
        return fieldList;
    }

    String preSolve(String sentence) {
        sentence = sentence.toLowerCase();
        return sentence;
    }

}
