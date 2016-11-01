package com.gmcc.hdp.demo.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 样例运行的常量类
 * Created by $wally on 2016/7/8.
 */
public class HDPSampleConfiguration {

    private String filePath=null;
    public Properties prop=null;
    private InputStream in=null;

    /**
     * 通用配置
     */
    public static String LOCAL_2G_USER_FILE;
    public static String LOCAL_TMP_2G_USER_FILE ;


    /**
     * HBase样例运行配置
     */
    public static String HBASE_COLUMNFAMILY_NAME ;
    public static String HBASE_ZOOKEEPER_QUORUM_LIST ;
    public static String HBASE_MASTER_KERBEROS_PRINCIPAL;
    public static String HBASE_REGIONSERVER_KERBEROS_PRINCIPAL ;


    /**
     * Hive样例运行配置
     */
    public static String HIVE_DRIVER_NAME;
    public static String HIVE_SERVER_NAME;
    public static String HIVE_DATABASE_NAME ;
    public static String HIVE_PLATFORM_USERNAME ;
    public static String HIVE_PLATFORM_PASSWORD;
    public static String HIVE_KERBEROS_PRINCIPAL ;


    /**
     * kafka样例运行配置
     */
    public static String KAFKA_ZOOKEEPER_CONNECT_LIST;
    public static String KAFKA_GROUP_ID;
    public static String KAFKA_BROKER_LIST;

    /**
     * spark 样例运行配置
     */
    public static String SPARK_AMBARI_LOG_PATH ;

    /**
     * oozie 配置
     */
    public static String OOZIE_URL;



    public HDPSampleConfiguration(String filePath){
        this.filePath=filePath;
        prop = new Properties();

        try{
            in = new BufferedInputStream(new FileInputStream(filePath));
            prop.load(in);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try{
                if(in!=null){
                    in.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        initConfiguration();
    }

    public void initConfiguration(){
        LOCAL_2G_USER_FILE=prop.getProperty("local_2g_user_file");
        LOCAL_TMP_2G_USER_FILE=prop.getProperty("local_tmp_2g_user_file");

        HBASE_COLUMNFAMILY_NAME=prop.getProperty("hbase_columnfamily_name");
        HBASE_ZOOKEEPER_QUORUM_LIST=prop.getProperty("hbase_zookeeper_quorum_list");
        HBASE_MASTER_KERBEROS_PRINCIPAL=prop.getProperty("hbase_master_kerberos_principal");
        HBASE_REGIONSERVER_KERBEROS_PRINCIPAL=prop.getProperty("hbase_regionserver_kerberos_principal");

        HIVE_DRIVER_NAME=prop.getProperty("hive_driver_name");
        HIVE_SERVER_NAME=prop.getProperty("hive_server_name");
        HIVE_DATABASE_NAME=prop.getProperty("hive_database_name");
        HIVE_PLATFORM_USERNAME=prop.getProperty("hive_platform_username");
        HIVE_PLATFORM_PASSWORD=prop.getProperty("hive_platform_password");
        HIVE_KERBEROS_PRINCIPAL=prop.getProperty("hive_kerberos_principal");

        KAFKA_ZOOKEEPER_CONNECT_LIST=prop.getProperty("kafka_zookeeper_connect_list");
        KAFKA_GROUP_ID=prop.getProperty("kafka_group_id");
        KAFKA_BROKER_LIST=prop.getProperty("kafka_broker_list");
        SPARK_AMBARI_LOG_PATH=prop.getProperty("spark_ambari_log_path");

        OOZIE_URL=prop.getProperty("oozie_url");
    }
}
