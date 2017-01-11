package com.robot.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.*;
import java.util.*;

/**
 * Created by xing on 2016/12/7.
 */
public class ExcelUtil {
    public static void ParseExcel() throws IOException {
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

        System.out.println("all rows is " + hssfSheet.getLastRowNum());
        for (int i = 0; i < hssfSheet.getLastRowNum(); i++) {
            System.out.println("Row[" + (i + 1) + "] the cols is " + hssfSheet.getRow(i).getLastCellNum());
            if (i == 0) continue;
            List<String> synonmDicts = new ArrayList<String>();
            String qString = "";
            String tags1 = "";
            String tags2 = "";
            for (int j = 0; j < hssfSheet.getRow(i).getLastCellNum(); j++) {
                HSSFCell cell = hssfSheet.getRow(i).getCell(j);
                if (cell != null) {
                    String cellString = "";
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        cellString = String.valueOf(cell.getNumericCellValue());
                    }
                    else if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                        cellString = cell.getStringCellValue();
                    }
                    else{
                        cellString = cell.getStringCellValue();
                    }
                    cellString = StringUtils.TRIM_STRING(cellString);
                    System.out.println("[" + j + "] = " + cellString);
                    //dict
                    if ((j == dictPos1 || j == dictpos2 || j == dictpos3) && !cellString.equals("")) {
                        String[] words = cellString.split("、");
                        for (String w : words) {
                            if (!w.equals("")) {
                                dictWriter.append(w + "\n");
                            }
                        }
                        if (j == dictPos1) {
                            tags1 = cellString;
                            weightWords.put(tags1, 0);
                        }
                        if (j == dictpos3) {
                            tags2 = cellString;
                            weightWords.put(tags2, 0);
                        }
                    }
                    //synonm dict
                    if (j == synonmLeft && !cellString.equals("")) {
                        synonmDicts.add(cellString);
                    }
                    if (j == synonmRight && !cellString.equals("") && synonmDicts.size() == 1) {
                        String[] words = cellString.split("、");
                        String value = synonmDicts.get(0);
                        for (String w : words) {
                            if (!w.equals("")) {
                                //synonmDictWriter.append("," + w);
                                if (value.equals("")) {
                                    value = w;
                                } else {
                                    value += "," + w;
                                }
                            }
                        }
                        words = value.split(",");
                        for(int k1 = 0; k1 < words.length; k1 ++) {
                            value = "";
                            for (int k2 = 0; k2 < words.length; k2 ++) {
                                if (k1 == k2) continue;
                                if (value.equals("")) value = words[k2];
                                else value += "," + words[k2];
                            }
                            synonmWords.put(words[k1], value);
                        }
                    }
                    //q a
                    if (j == qIndex) {
                        qString = cellString;
                    }
                    if (j == aIndex) {
                        String filename = FileService.anotherMD5(qString + cellString/* + tags1 + tags2*/);
                        qaWriter = new BufferedWriter(new FileWriter(documentPath + filename + ".html"));
                        String tags1String = tags1;
                        if(synonmWords.containsKey(tags1)) tags1String += "," + synonmWords.get(tags1);
                        String tags2String = tags2;
                        if(synonmWords.containsKey(tags2)) tags2String += "," + synonmWords.get(tags2);
                        qaWriter.append(QABeanService.createQAWithTags(qString, cellString, tags1String, tags2String));
                        qaWriter.flush();
                        qaWriter.close();
                        //qaList.add(qString + cellString);
                    }
                }
            }
            dictWriter.flush();
        }
        for(String key:synonmWords.keySet()){
            String value = key + "," + synonmWords.get(key);
            String[] words = value.split(",");
            for(int k1 = 0; k1 < words.length; k1 ++){
                value = "";
                for(int k2 = 0; k2 < words.length; k2 ++){
                    if(k1 == k2) continue;
                    if(value.equals("")) value = words[k2];
                    else value += "," + words[k2];
                }
                synonmDictWriter.write(words[k1] + "," + value + "\n");
            }
            synonmDictWriter.flush();
        }
        ////////load other qalist////////
        qaList = getQAList();
        /////////////////////////////////
        Iterator<String> it = qaList.iterator();
        while (it.hasNext()) {
            String tempString = it.next();
            for (String w : weightWords.keySet()) {
                if (tempString.contains(w)) {
                    weightWords.put(w, weightWords.get(w) + 1);
                }
            }
        }
        String weightSpliter = "~";
        for (String w : weightWords.keySet()) {
            if (synonmWords.containsKey(w)) {
                String[] wlist = synonmWords.get(w).split(",");
                weightDictWriter.append(w + weightSpliter + weightWords.get(w) * 1.0 / qaList.size() + "\n");
                for (String ws : wlist) {
                    if (!ws.equals("")) {
                        weightDictWriter.append(ws + weightSpliter + weightWords.get(w) * 1.0 / qaList.size() + "\n");
                    }
                }
            } else {
                weightDictWriter.append(w + weightSpliter + weightWords.get(w) * 1.0 / qaList.size() + "\n");
            }
        }
        weightDictWriter.flush();
        synonmDictWriter.close();
        dictWriter.close();
        weightDictWriter.close();
    }

    public static void ParseExcel2() throws IOException {
        String excelFilePath = "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\测试题库2.xls";
        InputStream is = new FileInputStream(excelFilePath);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(1);
        String dictPath = "C:\\Users\\xing\\workspace\\QARobot\\src\\main\\resources\\dict\\";
        //dict
        String dictFilePath = dictPath + "dict-excel-2.dic";
        List<Integer> dictPosList = new ArrayList<Integer>();
        dictPosList.add(4);
        dictPosList.add(5);
        dictPosList.add(6);
        dictPosList.add(7);
        dictPosList.add(8);
        dictPosList.add(9);
        BufferedWriter dictWriter = new BufferedWriter(new FileWriter(dictFilePath));
        //synonm dict
        String synonmDictFilePath = dictPath + "customSynonm-excel-2.dic";
        List<Integer> synonmIndexList = new ArrayList<Integer>();
        synonmIndexList.add(4);
        synonmIndexList.add(5);
        synonmIndexList.add(6);
        synonmIndexList.add(7);
        synonmIndexList.add(8);
        synonmIndexList.add(9);
        BufferedWriter synonmDictWriter = new BufferedWriter(new FileWriter(synonmDictFilePath));
        Map<String, String> synonmWords = new HashMap<String, String>();
        //weight dict
        String weightDictFilePath = dictPath + "weight-excel-2.dic";
        BufferedWriter weightDictWriter = new BufferedWriter(new FileWriter(weightDictFilePath));
        Map<String, Integer> weightWords = new HashMap<String, Integer>();
        //kb dict
        /*
        String kbDictFilePath = "";
        BufferedWriter kbDictWriter = new BufferedWriter(new FileWriter(kbDictFilePath));
        */
        //document qa
        String documentPath = "C:\\Users\\xing\\workspace\\QARobot\\src\\main\\resources\\document-excel-2\\";
        int qIndex = 12;
        int aIndex = 13;
        List<String> qaList = new ArrayList<String>();
        BufferedWriter qaWriter = null;

        System.out.println("all rows is " + hssfSheet.getLastRowNum());
        for (int i = 0; i < hssfSheet.getLastRowNum(); i++) {
            System.out.println("Row[" + (i + 1) + "] the cols is " + hssfSheet.getRow(i).getLastCellNum());
            if (i == 0) continue;
            String qString = "";
            String tags1 = "";
            String tags2 = "";
            String synonmString = "";
            int synonmIndex = 0;
            for (int j = 0; j < hssfSheet.getRow(i).getLastCellNum() && j <= aIndex; j++) {
                HSSFCell cell = hssfSheet.getRow(i).getCell(j);
                if(synonmIndex % 2 == 0){
                    synonmString = "";
                }
                if (cell != null) {
                    String cellString = "";
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        cellString = String.valueOf(cell.getNumericCellValue());
                    }
                    else if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                        cellString = cell.getStringCellValue();
                    }
                    else{
                        cellString = cell.getStringCellValue();
                    }
                    cellString = StringUtils.TRIM_STRING(cellString);
                    System.out.println("[" + j + "] = " + cellString);
                    //dict
                    if (dictPosList.contains(Integer.valueOf(j)) && !cellString.equals("")) {
                        String[] words = cellString.split("、");
                        for (String w : words) {
                            if (!w.equals("")) {
                                weightWords.put(w, 0);
                                dictWriter.append(w + "\n");
                            }
                        }
                        if (dictPosList.get(0).equals(Integer.valueOf(j))) {
                            for (String w : words) {
                                if (!w.equals("")) {
                                    if (tags1.equals("")) {
                                        tags1 = w;
                                    } else {
                                        tags1 += "," + w;
                                    }
                                }
                            }
                        } else if(dictPosList.get(2).equals(Integer.valueOf(j))
                                || dictPosList.get(4).equals(Integer.valueOf(j))){
                            for (String w : words) {
                                if (!w.equals("")) {
                                    if (tags2.equals("")) {
                                        tags2 = w;
                                    } else {
                                        tags2 += "," + w;
                                    }
                                }
                            }
                        }
                    }
                    //synonm dict
                    if (synonmIndex < synonmIndexList.size()
                            && synonmIndex % 2 == 0
                           && synonmIndexList.get(synonmIndex).equals(Integer.valueOf(j))
                            && !cellString.equals("")) {
                        synonmString = splitStringByComma(cellString);
                        synonmIndex += 1;
                    }
                    else if (synonmIndex < synonmIndexList.size()
                            && synonmIndexList.get(synonmIndex).equals(Integer.valueOf(j))
                            && !cellString.equals("")
                            && !synonmString.equals("")) {
                        synonmIndex += 1;
                        //synonmDictWriter.append(synonmString);
                        String[] words = cellString.split("、");
                        String value = synonmString;
                        for (String w : words) {
                            if (!w.equals("")) {
                                //synonmDictWriter.append("," + w);
                                if (value.equals("")) {
                                    value = w;
                                } else {
                                    value += "," + w;
                                }
                            }
                        }
                        words = value.split(",");
                        for (int k1 = 0; k1 < words.length; k1++) {
                            value = "";
                            for (int k2 = 0; k2 < words.length; k2++) {
                                if (k1 == k2) continue;
                                if (value.equals("")) value = words[k2];
                                else value += "," + words[k2];
                            }
                            synonmWords.put(words[k1], value);
                        }
                        //synonmDictWriter.append("\n");
                    }
                    else if(synonmIndexList.contains(Integer.valueOf(j))){
                        synonmIndex += 1;
                    }
                    //q a
                    if (j == qIndex) {
                        qString = cellString;
                    }
                    if (j == aIndex) {
                        String filename = FileService.anotherMD5(qString + cellString/* + tags1 + tags2*/);
                        qaWriter = new BufferedWriter(new FileWriter(documentPath + filename + ".html"));
                        //1
                        String[] tags1String = tags1.trim().split(",");
                        Set<String> tags1Set = new HashSet<String>();
                        for(String curS:tags1String){
                            tags1Set.add(curS);
                            if(synonmWords.containsKey(curS)) {
                                for (String nxtS : synonmWords.get(curS).split(",")) {
                                    tags1Set.add(nxtS);
                                }
                            }
                        }
                        String tags1Ans = "";
                        for(String curS:tags1Set){
                            if(tags1Ans.equals("")) tags1Ans = curS;
                            else tags1Ans += "," + curS;
                        }
                        //2
                        String[] tags2String = tags2.trim().split(",");
                        Set<String> tags2Set = new HashSet<String>();
                        for(String curS:tags2String){
                            tags2Set.add(curS);
                            if(synonmWords.containsKey(curS)) {
                                for (String nxtS : synonmWords.get(curS).split(",")) {
                                    tags2Set.add(nxtS);
                                }
                            }
                        }
                        String tags2Ans = "";
                        for(String curS:tags2Set){
                            if(tags2Ans.equals("")) tags2Ans = curS;
                            else tags2Ans += "," + curS;
                        }
                        qaWriter.append(QABeanService.createQAWithTags(qString, cellString, tags1Ans, tags2Ans));
                        qaWriter.flush();
                        qaWriter.close();
                        //qaList.add(qString + cellString);
                    }
                }
                else{
                    if(synonmIndexList.contains(Integer.valueOf(j)))
                        synonmIndex += 1;
                }
            }
            //synonmDictWriter.flush();
            dictWriter.flush();
        }
        for(String key:synonmWords.keySet()){
            String value = key + "," + synonmWords.get(key);
            String[] words = value.split(",");
            for(int k1 = 0; k1 < words.length; k1 ++){
                value = "";
                for(int k2 = 0; k2 < words.length; k2 ++){
                    if(k1 == k2) continue;
                    if(value.equals("")) value = words[k2];
                    else value += "," + words[k2];
                }
                synonmDictWriter.write(words[k1] + "," + value + "\n");
            }
            synonmDictWriter.flush();
        }
        ////////load other qalist////////
        qaList = getQAList();
        /////////////////////////////////
        Iterator<String> it = qaList.iterator();
        while (it.hasNext()) {
            String tempString = it.next();
            for (String w : weightWords.keySet()) {
                if (tempString.contains(w)) {
                    weightWords.put(w, weightWords.get(w) + 1);
                }
                else if (synonmWords.containsKey(w)){
                    boolean f = false;
                    String[] wlist = synonmWords.get(w).split(",");
                    for(String ws : wlist){
                        if(tempString.contains(ws)){
                            f = true;break;
                        }
                    }
                    if(f){
                        weightWords.put(w, weightWords.get(w) + 1);
                    }
                }
            }
        }
        String weightSpliter = "~";
        for (String w : weightWords.keySet()) {
            if (synonmWords.containsKey(w)) {
                String[] wlist = synonmWords.get(w).split(",");
                weightDictWriter.append(w + weightSpliter + weightWords.get(w) * 1.0 / qaList.size() + "\n");
                for (String ws : wlist) {
                    if (!ws.equals("")) {
                        weightDictWriter.append(ws + weightSpliter + weightWords.get(w) * 1.0 / qaList.size() + "\n");
                    }
                }
            } else {
                weightDictWriter.append(w + weightSpliter + weightWords.get(w) * 1.0 / qaList.size() + "\n");
            }
        }
        weightDictWriter.flush();
        synonmDictWriter.close();
        dictWriter.close();
        weightDictWriter.close();

    }

    public static String splitStringByComma(String s){
        s = StringUtils.TRIM_STRING(s);
        String[] list = s.split("、");
        s = "";
        for(String w:list){
            if(w.equals("")) continue;
            if(s.equals("")) s = w;
            else s += "," + w;
        }
        return s;
    }

    public static List<String> getQAList() throws IOException {
        String[] qaFileNameList = {
                //"D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\测试题库.xls",
                "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\测试题库2.xls"
        };
        List<String> qaList = new ArrayList<String>();
        for(String filename:qaFileNameList){
            InputStream is = new FileInputStream(filename);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            for(int sheetNo = 0; sheetNo < 3; sheetNo ++){
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheetNo);
                int qindex = -1;
                int aindex = -1;
                for(int row = 0; row < hssfSheet.getLastRowNum(); row += 1){
                    for(int col = 0; col < hssfSheet.getRow(row).getLastCellNum(); col ++){
                        Cell cell = hssfSheet.getRow(row).getCell(col);
                        if(cell == null) continue;
                        String s = "";
                        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                            s = String.valueOf(cell.getNumericCellValue());
                        }
                        else{
                            s = cell.getStringCellValue();
                        }
                        if(s.equals("q")){
                            qindex = col;
                        }
                        if(s.equals("a")){
                            aindex = col;
                            break;
                        }
                    }
                    break;
                }
                if(qindex >= 0 && aindex >= 0){
                    for(int row = 0; row < hssfSheet.getLastRowNum(); row ++){
                        if(row == 0) continue;
                        String qq = "";
                        String aa = "";
                        for(int col = 0; col < hssfSheet.getRow(row).getLastCellNum(); col ++){
                            Cell cell = hssfSheet.getRow(row).getCell(col);
                            if(cell == null) continue;
                            String s = "";
                            if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                                s = String.valueOf(cell.getNumericCellValue());
                            }
                            else{
                                s = cell.getStringCellValue();
                            }
                            s = StringUtils.TRIM_STRING(s);
                            if(col == qindex && !s.equals("")){
                                qq = s;
                            }
                            if(col == aindex && !s.equals("")){
                                aa = s;
                            }
                        }
                        if(!qq.equals("") && !aa.equals("")){
                            qaList.add(qq + aa);
                        }
                    }
                }
            }
        }
        return qaList;
    }

    public static void main(String[] args) throws IOException {
        // ExcelUtil.ParseExcel();
        ExcelUtil.ParseExcel2();
    }
}
