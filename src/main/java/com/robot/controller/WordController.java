package com.robot.controller;

import com.robot.application.segmentWord.MySegmentWord;
import com.robot.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xing on 2016/10/7.
 */
@Controller
@RequestMapping(value = "/word")
public class WordController {
    @Autowired
    private MySegmentWord sw;

    @RequestMapping(value = "/")
    public Result segmentWord(String sentence) {
        return null;
    }
}
