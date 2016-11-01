package com.gmcc.hdp.demo.util;

/**
 * 样例运行的常量类
 * Created by $wally on 2016/7/8.
 */
public class HDPSampleConfiguration {

    /**
     * 通用配置
     */
    public static String LOCAL_2G_USER_FILE = "2G_user_location_data.csv";//本地文件名
    public static String LOCAL_TMP_2G_USER_FILE = "/tmp/2G_user_location_data.csv";//测试文件存放路径


    /**
     * HBase样例运行配置
     */
    public static String HBASE_COLUMNFAMILY_NAME = "Base";  //table列簇名
    public static String HBASE_ZOOKEEPER_QUORUM_LIST = "host1,host2,host3";//hbase.zookeeper.quorum
    public static String HBASE_MASTER_KERBEROS_PRINCIPAL = "hbase/host@local_domain";//hbase集群的master在kerberos中的principal
    public static String HBASE_REGIONSERVER_KERBEROS_PRINCIPAL = "hbase/host@local_domain";//hbase集群的regionserver在kerberos中的principal


    /**
     * Hive样例运行配置
     */
    public static String HIVE_DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";//连接hive数据库的驱动名称
    public static String HIVE_SERVER_NAME = "hiveServer2";//hiveServer2服务器地址
    public static String HIVE_DATABASE_NAME = "default";//hive中数据库的地址
    public static String HIVE_PLATFORM_USERNAME = "user"; //平台用户名
    public static String HIVE_PLATFORM_PASSWORD = "password";//平台用户名对应密码
    public static String HIVE_KERBEROS_PRINCIPAL = "hive/host@local_domain";//hiveSever在kerberos中的principal


    /**
     * kafka样例运行配置
     */
    public static String KAFKA_ZOOKEEPER_CONNECT_LIST = "host1:2181,host2:2181,host3:2181";//zookeeper.connect.list
    public static String KAFKA_GROUP_ID = "group_id";//customer.group.list
    public static String KAFKA_BROKER_LIST = "host1:9092,host2:9092,host3:9092";//metadata.broker.list(注意端口号配置)

    /**
     * spark 样例运行配置
     */
    public static String SPARK_AMBARI_LOG_PATH = "file:///var/log/ambari-agent/ambari-agent.log";//ambari日志路径

    /**
     * oozie 配置
     */
    public static String OOZIE_URL = "http://vm2.datatub.com:11000/oozie";

}
