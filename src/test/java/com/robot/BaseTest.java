package com.robot;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xing on 2016/9/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:webmvc-config.xml",
        "classpath*:config/log4j.xml"
})
public class BaseTest {
}
