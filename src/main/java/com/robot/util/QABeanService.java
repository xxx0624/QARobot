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

    public static String getTagContent(String html, String tag){
        int pos1 = html.indexOf(tag);
        if(pos1 >= 0){
            int pos2 = html.indexOf("</div>", pos1);
            pos1 += 6;
            return html.substring(pos1, pos2);
        }
        return "";
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

    public static String createQA(String q, String a, String tags1, String tags2) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
                "<head><title>Search</title></head><body>" +
                "<div class=\"tag1\">"+tags1+"</div>" +
                "<div class=\"tag2\">"+tags2+"</div>" +
                "<div class=\"q\">"+q+"</div><br>" +
                "<div class=\"a\">" +a+
                "</div></body></html>");
        return sb.toString();
    }

    public static String createQAWithTags(String q, String a, String tags1, String tags2) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>" +
                "<head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">" +
                "<meta  name=\"tag1\"  content=\"" + tags1 + "\"/>" +
                "<meta  name=\"tag2\"  content=\"" + tags2 + "\"/>" +
                "</head>" +
                "<body>" +
                "<div class=\"q\">" + q + "</div>" +
                "<br><div class=\"a\">" + a + "</div>" +
                "</body>" +
                "</html>");
        return sb.toString();
    }
}
