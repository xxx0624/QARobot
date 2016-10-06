package com.robot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xing on 2016/10/5.
 */
@Controller
public class Index {
    @RequestMapping(value = "/home")
    public ModelAndView home(){
        return new ModelAndView("home");
    }
}
