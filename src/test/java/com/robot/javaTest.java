package com.robot;

import com.robot.util.FileService;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/**
 * Created by xing on 2017/1/5.
 */
public class javaTest {
    private static String getTAG(String htmlStr, String tag) {
        org.jsoup.nodes.Document document = null;
        document = Jsoup.parse(htmlStr);
        Elements meta = document.getElementsByAttributeValue("name", tag);
        // System.out.println(meta.text());
        return meta.attr("content");
    }
    public static void main(String[] args){
        String q = "申请领用数量大于该纳税人本次可领用数量" + "请确认已使用的发票已经验旧成功了，确认可领用的发票数量。";
        System.out.println("MD5后：" + FileService.anotherMD5(q));
    }
}
