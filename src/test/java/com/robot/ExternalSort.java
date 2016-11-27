package com.robot;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


// Goal: offer a generic external-memory sorting program in Java.
//
// It must be :
//  - hackable (easy to adapt)
//  - scalable to large files
//  - sensibly efficient.

// This software is in the public domain.

public class ExternalSort {

    private String TAG = "BaiDuSearchUtils";
    private String encode = "utf-8";

    public URL SearchKey(String key) {
        URL u = null;
        try {
            key = URLEncoder.encode(key, encode);

            u = new URL("http://baike.baidu.com/item/" + key);
            URLConnection conn = u.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), encode));
            String str = reader.readLine();
            System.out.println(TAG + "str1：" + str);
            while (str != null) {
                System.out.println(TAG+ "str2：" + str);
                str = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return u;
    }

    public static void main(String[] args){
        URL u = new ExternalSort().SearchKey("雷电");

    }

}