package com.ftpService.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
* @ClassName: ReadConfigUtil
* @Description: 读取配置文件工具类(properties类型和文件类型)
* @author wally@hudongpai.com
* @date 2016-6-29 下午03:46:40
 */
public class ReadConfigUtil {
    private static Log log = LogFactory.getLog(ReadConfigUtil.class);

    private InputStream inputStream;
    private BufferedReader bReader;
    private Properties config;
    private String strline;

    public String getStrline() {
        return strline;
    }

    public void setStrline(String strline) {
        this.strline = strline;
    }

    /**
     * @describe 构造函数
     * @param fileName 文件名
     * @param flag   若为true，则表示读取properties类型的文件，否则为txt文件
     */
    public ReadConfigUtil(String fileName, boolean flag) {
        config = null;
        bReader = null;
        inputStream = ReadConfigUtil.class.getClassLoader().getResourceAsStream(fileName);

        try {
            if (flag) {
                config = new Properties();
                config.load(inputStream);
            } else {
                bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                setStrline(readFile());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getValue(String key) {
        return config.getProperty(key);
    }

    private  String readFile() {
        StringBuffer sBuffer = new StringBuffer();

        try {
            String line = null;
            while ((line = bReader.readLine()) != null) {
                if (line.trim().length() > 0 && (!line.trim().startsWith("#"))) {
                    sBuffer.append(line);
                    sBuffer.append("\n");
                }
            }
        } catch (Exception e) {
            log.info("读文件异常！！！！");
        }
        return sBuffer.toString();
    }

    public static void main(String args[]) {
        System.out.println(new ReadConfigUtil("ftp.properties", true).getValue("ftp.internal.ip"));
        System.out.println(new ReadConfigUtil("ftp.properties", true).getValue("ftp.external.ip"));
    }
}