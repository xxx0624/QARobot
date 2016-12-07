package com.robot.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Intercept;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xing on 2016/12/7.
 */
public class ExcelUtil {
    public static void main(String[] args) throws IOException {
        String excelFilePath = "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\测试题库.xls";
        InputStream is = new FileInputStream(excelFilePath);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        String dictPath = "C:\\Users\\xing\\workspace\\QARobot\\src\\main\\resources\\dict\\";
        //dict
        String dictFilePath = dictPath + "dict-excel.dic";
        int dictPos1 = 4;
        int dictpos2 = 5;
        int dictpos3 = 6;
        BufferedWriter dictWriter = new BufferedWriter(new FileWriter(dictFilePath));
        //synonm dict
        String synonmDictFilePath = dictPath + "customSynonm-excel.dic";
        int synonmLeft = 4;
        int synonmRight = 5;
        BufferedWriter synonmDictWriter = new BufferedWriter(new FileWriter(synonmDictFilePath));
        //weight dict
        String weightDictFilePath = dictPath + "weight-excel.dic";
        BufferedWriter weightDictWriter = new BufferedWriter(new FileWriter(weightDictFilePath));
        //kb dict
        /*
        String kbDictFilePath = "";
        BufferedWriter kbDictWriter = new BufferedWriter(new FileWriter(kbDictFilePath));
        */
        //document qa
        String documentPath = "C:\\Users\\xing\\workspace\\QARobot\\src\\main\\resources\\document-excel\\";
        int qIndex = 9;
        int aIndex = 10;
        BufferedWriter qaWriter = null;
        //other
        Set<Integer> set = new HashSet<Integer>();
        set.add(8);

        System.out.println("all rows is "+ hssfSheet.getLastRowNum());
        for(int i = 0; i < hssfSheet.getLastRowNum(); i ++){
            System.out.println("Row["+(i+1)+"] the cols is " + hssfSheet.getRow(i).getLastCellNum());
            if(i == 0) continue;
            List<String> synonmDicts = new ArrayList<String>();
            String qString = "";
            String tags1 = "";
            String tags2 = "";
            for(int j = 0; j < hssfSheet.getRow(i).getLastCellNum(); j ++){
                HSSFCell cell = hssfSheet.getRow(i).getCell(j);
                if(cell != null) {
                    String cellString = "";
                    if(set.contains(Integer.valueOf(j))){
                        cellString = String.valueOf(cell.getNumericCellValue());
                    }
                    else{
                        cellString = cell.getStringCellValue();
                    }
                    cellString = StringUtils.TRIM_STRING(cellString);
                    System.out.println("[" + j + "] = " + cellString);
                    //dict
                    if ((j == dictPos1 || j == dictpos2 || j == dictpos3) && !cellString.equals("")) {
                        String[] words = cellString.split("、");
                        for(String w:words) {
                            if(!w.equals("")) {
                                dictWriter.append(w + "\n");
                            }
                        }
                        if(j == dictPos1) tags1 = cellString;
                        if(j == dictpos3) tags2 = cellString;
                    }
                    //synonm dict
                    if(j == synonmLeft && !cellString.equals("")){
                        synonmDicts.add(cellString);
                    }
                    if(j == synonmRight && !cellString.equals("") && synonmDicts.size() == 1){
                        synonmDictWriter.append(synonmDicts.get(0));
                        String[] words = cellString.split("、");
                        for(String w:words) {
                            if(!w.equals("")) {
                                synonmDictWriter.append("," + w);
                            }
                        }
                        synonmDictWriter.append("\n");
                    }
                    //q a
                    if(j == qIndex){
                        qString = cellString;
                    }
                    if(j == aIndex){
                        String filename = FileService.getStringMD5String(qString + cellString + tags1 + tags2);
                        qaWriter = new BufferedWriter(new FileWriter(documentPath + filename));
                        qaWriter.append(QABeanService.createQA(qString,cellString, tags1, tags2));
                        qaWriter.flush();
                        qaWriter.close();
                    }
                }
            }
            synonmDictWriter.flush();
            dictWriter.flush();
            weightDictWriter.flush();
        }
        synonmDictWriter.close();
        dictWriter.close();
        weightDictWriter.close();
    }
    public static List<String> getRowContent(Row row){
        List<String> ans = new ArrayList<String>();
        return ans;
    }
}
