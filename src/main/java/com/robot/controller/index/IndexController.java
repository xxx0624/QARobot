package com.robot.controller.index;

import com.robot.application.lucene.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Created by xing on 2016/10/4.
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {
    @Autowired
    private Index index;

    @RequestMapping(value = "/rebuildIndex")
    @ResponseBody
    public Integer rebuildIndex(){
        String faqFolderPath = "document";
        Resource resource = new ClassPathResource(faqFolderPath);
        try {
            faqFolderPath = resource.getURL().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String indexFolderPath = "index";
        resource = new ClassPathResource(indexFolderPath);
        try {
            indexFolderPath = resource.getURL().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer res = index.rebuildAllIndex(faqFolderPath, indexFolderPath);
        return res;
    }

}
