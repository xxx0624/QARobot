package com.robot.dao;

import com.robot.po.QA;
import org.springframework.stereotype.Repository;

/**
 * Created by xing on 2016/11/4.
 */
public interface QADao {
    QA selectQA(Integer id);
}
