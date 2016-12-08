package com.robot.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Intercept;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.*;

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
        String dictFilePath = dictPath + "dict-excel-1.dic";
        int dictPos1 = 4;
        int dictpos2 = 5;
        int dictpos3 = 6;
        BufferedWriter dictWriter = new BufferedWriter(new FileWriter(dictFilePath));
        //synonm dict
        String synonmDictFilePath = dictPath + "customSynonm-excel-1.dic";
        int synonmLeft = 4;
        int synonmRight = 5;
        BufferedWriter synonmDictWriter = new BufferedWriter(new FileWriter(synonmDictFilePath));
        Map<String, String> synonmWords = new HashMap<String, String>();
        //weight dict
        String weightDictFilePath = dictPath + "weight-excel-1.dic";
        BufferedWriter weightDictWriter = new BufferedWriter(new FileWriter(weightDictFilePath));
        Map<String, Integer> weightWords = new HashMap<String, Integer>();
        //kb dict
        /*
        String kbDictFilePath = "";
        BufferedWriter kbDictWriter = new BufferedWriter(new FileWriter(kbDictFilePath));
        */
        //document qa
        String documentPath = "C:\\Users\\xing\\workspace\\QARobot\\src\\main\\resources\\document-excel-1\\";
        int qIndex = 9;
        int aIndex = 10;
        List<String> qaList = new ArrayList<String>();
        BufferedWriter qaWriter = null;
        //paser excel:some special case
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
                        if(j == dictPos1) {
                            tags1 = cellString;
                            weightWords.put(tags1, 0);
                        }
                        if(j == dictpos3) {
                            tags2 = cellString;
                            weightWords.put(tags2, 0);
                        }
                    }
                    //synonm dict
                    if(j == synonmLeft && !cellString.equals("")){
                        synonmDicts.add(cellString);
                    }
                    if(j == synonmRight && !cellString.equals("") && synonmDicts.size() == 1){
                        synonmDictWriter.append(synonmDicts.get(0));
                        String[] words = cellString.split("、");
                        String value = "";
                        for(String w:words) {
                            if(!w.equals("")) {
                                synonmDictWriter.append("," + w);
                                if(value.equals("")){
                                    value = w;
                                }
                                else{
                                    value += ","+w;
                                }
                            }
                        }
                        synonmWords.put(synonmDicts.get(0), value);
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
                        qaList.add(qString + cellString);
                    }
                }
            }
            synonmDictWriter.flush();
            dictWriter.flush();
        }
        Iterator<String> it = qaList.iterator();
        while(it.hasNext()){
            String tempString = it.next();
            for(String w:weightWords.keySet()){
                if(tempString.contains(w)){
                    weightWords.put(w, weightWords.get(w) + 1);
                }
            }
        }
        for(String w:weightWords.keySet()){
            if(synonmWords.containsKey(w)){
                String[] wlist = synonmWords.get(w).split(",");
                weightDictWriter.append(w + " " + weightWords.get(w) * 1.0 / qaList.size() + "\n");
                for(String ws:wlist){
                    if(!ws.equals("")) {
                        weightDictWriter.append(ws + " " + weightWords.get(w) * 1.0 / qaList.size() + "\n");
                    }
                }
            }
            else {
                weightDictWriter.append(w + " " + weightWords.get(w) * 1.0 / qaList.size() + "\n");
            }
        }
        weightDictWriter.flush();
        synonmDictWriter.close();
        dictWriter.close();
        weightDictWriter.close();
    }
    public static List<String> getRowContent(Row row){
        List<String> ans = new ArrayList<String>();
        return ans;
    }
}
