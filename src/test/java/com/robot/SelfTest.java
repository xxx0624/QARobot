package com.robot;

import com.robot.util.HTTPUtil;
import com.robot.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by xing on 2017/1/9.
 */
public class SelfTest {
    public static List<String> getQueryFromExcel(String path) throws IOException {
        List<String> queryList = new ArrayList<String>();
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        for (int i = 0; i < hssfSheet.getLastRowNum(); i++) {
            if (i == 0) continue;
            HSSFCell cell = hssfSheet.getRow(i).getCell(1);
            if (cell != null) {
                String cellString = "";
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    cellString = String.valueOf(cell.getNumericCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    cellString = cell.getStringCellValue();
                } else {
                    cellString = cell.getStringCellValue();
                }
                cellString = StringUtils.TRIM_STRING(cellString);
                queryList.add(cellString);
            }
        }
        return queryList;
    }

    public static List<String> getAnswerList(String query){
        List<String> answerList = new ArrayList<String>();
        String url = "http://localhost:8080/XQRobot/SearchServlet2_testRobot";
        String data = "tagType=1" +
                "&" +
                "radioType_search=2" +
                "&" +
                "radioType_word=1" +
                "&" +
                "similaryInputTxt=如：上海" +
                "&" +
                "radioType_other=1" +
                "&" +
                "searchTxt="+query;
        String result = HTTPUtil.post(url, data);
        String[] resultList = StringUtils.TRIM_STRING(result).split("</br></br>");
        for(int i = 1; i < resultList.length; i += 2){
            answerList.add(resultList[i]);
            answerList.add(resultList[i + 1]);
        }
        return answerList;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String path = "D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率统计\\命中率12.28.xls";
        List<String> queryList = getQueryFromExcel(path);
        FileWriter tempFile = new FileWriter("D:\\TJ\\神计机器人相关问答系统\\神计问答语料\\命中率统计\\自测命中率-0105-lucene+姚丽萍标注方法.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(tempFile);
        int cnt = 0;
        for(String q:queryList){
            List<String> ansList = getAnswerList(q);
            bufferedWriter.write(q.trim() + " [" + ansList.size()/2 + "]\n");
            for(String answer:ansList){
                bufferedWriter.write(answer.trim() + "\n");
            }
            bufferedWriter.write("\n");
            bufferedWriter.flush();
            System.out.println("["+cnt+"]"+q + "\n");
            cnt ++ ;
            Thread.sleep(5000);
            //break;
        }
        bufferedWriter.close();
    }
}
