package com.robot.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xing on 2016/11/28.
 */
public class WeightDicUtil {
    private static WeightDicUtil instance;

    private static Map<String, Double> weightDic;

    public Map<String, Double> getWeightDic(){
        return weightDic;
    }


    public WeightDicUtil(String filePath) {
        init(filePath);
    }

    private void init(String filePath){
        File file = new File(filePath);
        weightDic = new HashMap<String, Double>();
        if(file.isFile()){
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
                String line = "";
                int index = 0;
                while((line = br.readLine()) != null){
                    line = StringUtils.TRIM_STRING(line);
                    if(line == "") break;
                    String[] slist = line.split(" ");
                    String[] words = slist[0].split(",");
                    Double d = new Double(slist[1]);
                    for(String w:words){
                        weightDic.put(StringUtils.TRIM_STRING(w), d);
                    }
                }
                br.close();
            } catch (FileNotFoundException e) {
                System.err.println("[Error] buffer read weight dic error...");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("[Error] buffer readline weight dic error...");
                e.printStackTrace();
            }
        }
    }

    public static synchronized WeightDicUtil getInstance(String filePath){
        if(instance == null){
            synchronized (WeightDicUtil.class){
                if(instance == null){
                    instance = new WeightDicUtil(filePath);
                }
            }
        }
        return instance;
    }

}
