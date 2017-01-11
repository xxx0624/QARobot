package com.robot;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xing on 2016/12/28.
 */
public class CalculateAcc {
    public static List<Integer> test(String path) throws IOException {
        int cnt = 0;
        int top1 = 0, top2 = 0, top3 = 0;
        int target = 4;
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        for(int i = 0; i < hssfSheet.getLastRowNum(); i ++){
            if(i == 0) continue;
            for(int j = 0; j < hssfSheet.getRow(i).getLastCellNum(); j ++){
                HSSFCell cell = hssfSheet.getRow(i).getCell(j);
                if(cell != null && j == target){
                    String str = "";
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        str = String.valueOf(cell.getNumericCellValue());
                    }
                    else if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                        str = cell.getStringCellValue();
                    }
                    else{
                        str = cell.getStringCellValue();
                    }
                    if(str.trim().equals("1")){
                        top1 += 1;
                    }
                    else if(str.trim().equals("2")){
                        top2 += 1;
                    }
                    else if(str.trim().equals("3")){
                        top3 += 1;
                    }
                    cnt += 1;
                }
            }
        }
        System.out.println("[F1,Just Count] [" + path + "] "
                + "top1=" + (1.0 * top1 / cnt)
                + " top2=" + (1.0 * (top1 + top2) / cnt)
                + " top3=" + (1.0 * (top1 + top2 + top3) / cnt)
        );
        System.out.println("[F2] [" + path + "] "
                + "top1=" + (1.0 * top1 / cnt)
                + " top2=" + (1.0 * (top1 * 1.0 + top2 * 0.75) / cnt)
                + " top3=" + (1.0 * (top1 * 1.0 + top2 * 0.75 + top3 * 0.5) / cnt)
                + "\n"
        );
        List<Integer> res = new ArrayList<Integer>();
        res.add(top1);
        res.add(top2);
        res.add(top3);
        res.add(cnt);
        return res;
    }
    public static void main(String[] args) throws IOException {
        String[] pathList = {
                //"D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.14.xls",
                "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.19.xls",
                "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.20.xls",
                "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.22.xls",
                "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.26.xls",
                "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.27.xls",
                "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.28.xls",
                /*"D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.12.xls",
                "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.5.xls",
                "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.6.xls",
                "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.7.xls",
                "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.8.xls",
                "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率12.9.xls",*/
        };
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for(String path:pathList){
            res.add(test(path));
        }
        int top1 = 0, top2 = 0, top3 = 0;
        int cnt = 0;
        for(int i = 0; i < res.size(); i ++){
            top1 += res.get(i).get(0).intValue();
            top2 += res.get(i).get(1).intValue();
            top3 += res.get(i).get(2).intValue();
            cnt += res.get(i).get(3).intValue();
        }
        System.out.println("[F1,Just Count] All = " + (top1 + top2 + top3) * 1.0 / cnt);
        System.out.println("[F2] All = " + (top1 * 1.0 + top2 * 0.75 + top3 * 0.50) / cnt);
    }
}
