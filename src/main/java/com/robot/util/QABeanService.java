package com.robot.util;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/**
 * Created by xing on 2016/9/22.
 */
public class QABeanService {
    public static String getQuestion(String htmlStr) {
        org.jsoup.nodes.Document document = null;
        document = Jsoup.parse(htmlStr);
        Elements meta = document.select(".q");
        // System.out.println(meta.text());
        return meta.text();
    }

    public static String getAnswer(String htmlStr) {
        org.jsoup.nodes.Document document = null;
        document = Jsoup.parse(htmlStr);
        Elements meta = document.select(".a");
        // System.out.println(meta.text());
        return meta.text();
    }

    public static String createSearchText(String q, String a) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        sb.append("<head><title>Search</title></head><body>");
        q = q.replace("\n", "<br>");
        sb.append("<div class=\"q\">" + q + "</div><br>");
        a = a.replace("\n", "<br>");
        sb.append("<div class=\"a\">" + a + "</div>");
        sb.append("</body></html>");
        return sb.toString();
    }
}
