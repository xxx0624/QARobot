package com.robot.util;

import com.google.common.base.CharMatcher;

/**
 * Created by xing on 2016/11/27.
 */
public class StringUtils {
    public static String TRIM_STRING(String s){
        s = CharMatcher.anyOf("\r").removeFrom(s);
        s = CharMatcher.anyOf("\n").removeFrom(s);
        s = CharMatcher.BREAKING_WHITESPACE.trimFrom(s);
        if(s.startsWith("\uFEFF")){
            s = s.replace("\uFEFF", "");
        }
        return s;
    }
}
