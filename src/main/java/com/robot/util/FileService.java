package com.robot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.MessageDigest;

/**
 * Created by xing on 2016/9/22.
 */
public class FileService {

    private static Logger logger = LoggerFactory.getLogger(FileService.class);

    private static char md5Chars[] = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String read(String fileName, String encoding) {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis, encoding);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line + '\n');
            }
            br.close();
            isr.close();
            fis.close();
            return sb.toString();
        } catch (FileNotFoundException e) {
            logger.error("读取文件失败", e.getMessage());
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            logger.error("读取文件失败", e.getMessage());
            return sb.toString();
        } catch (IOException e) {
            logger.error("读取文件失败", e.getMessage());
            return sb.toString();
        }
    }

    public static boolean write(String fileContent, String fileName,
                                String encoding) throws Exception {
        try {
            File f = new File(fileName);
            if (f.exists())
                return false;
            FileOutputStream fos = new FileOutputStream(fileName);
            OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
            osw.write(fileContent);
            osw.flush();
            osw.close();
            fos.close();
            return true;
        } catch (Exception e) {
            logger.error("写文件失败", e.getMessage());
            return false;
        }
    }

    public static String getStringMD5String(String str) {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(str.getBytes());
            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            logger.error("MD5加密失败", e.getMessage());
            return null;
        }
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = md5Chars[(bt & 0xf0) >> 4];
        char c1 = md5Chars[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static void deleteFolder(String folderPath) {
        File file = new File(folderPath);
        if (file.isFile()) {
            if (folderPath.equals("write.lock")) {

            } else {
                file.delete();
            }
        } else {
            String[] files = file.list();
            for (int i = 0; i < files.length; i++) {
                System.out.println(file.getAbsolutePath());
                String newPath = folderPath.charAt(folderPath.length() - 1) == '/' ?
                        folderPath + files[i] : folderPath + '/' + files[i];
                deleteFolder(newPath);
            }
        }
    }
}
