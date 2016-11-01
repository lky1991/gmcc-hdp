package com.gmcc.hdp.demo.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by makun on 16/8/4.
 */
public class FileUtil {
    /**
     * 判断文件fileName是否存在
     * @param fileName
     * @return
     */
    public boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public boolean deleteFile(String fileName) {
        boolean flag = false;
        File file = new File(fileName);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 读取filename中的所有内容
     * @param fileName
     * @return
     */
    public String readToString(String fileName) {
        String encode = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent,encode);
        }catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 追加写入,content为将要追加的内容
     * @param fileName
     * @param content
     */
    public void appendFile(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileUtil fileUtil = new FileUtil();
//        String path = FileUtil.class.getClassLoader().getResource("test.txt").getPath();
//        String path = "/Users/makun/Projects/datatub/chinaMobile/trunk/网管室/dt-gmcc-hdp-sample_1/target/classes/test.txt";
//        String content = fileUtil.readToString(path);
//
//        for (int i=0;i<1;i++) {
//            fileUtil.appendFile(path,content);
//        }
//
//        System.out.println(fileUtil.readToString(path));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");//设置日期格式
        int index=0;
        while (index<10) {
            System.out.println(df.format(new Date()));
            long ms = 3000;
            try {
                Thread.sleep(ms);
                index ++;
            }catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
