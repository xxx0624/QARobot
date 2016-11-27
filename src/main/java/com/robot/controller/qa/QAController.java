package com.robot.controller.qa;

import com.robot.po.QA;
import com.robot.dao.QADao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xing on 2016/11/3.
 */
@Controller
public class QAController {
    @Autowired
    private QADao qaDao;

    @RequestMapping("/addQA")
    public void addQA(String question, String answer){
        //
    }

    @RequestMapping("/selectQA/{id}")
    public void selectQA(@PathVariable Integer id){
        QA qa = qaDao.selectQA(id);
        System.out.println(qa.getId() + " " + qa.getQuestion() + " " + qa.getAnswer());
    }
}
