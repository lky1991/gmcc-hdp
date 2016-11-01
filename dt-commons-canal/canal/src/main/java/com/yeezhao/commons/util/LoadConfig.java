package com.yeezhao.commons.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by $wally on 2016/9/12.
 */
public class LoadConfig {
    public Properties prop = null;
    private InputStream in = null;

    /**
     * HBase配置
     */
    public static String HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT;
    public static String HBASE__ZOOKEEPER_QUORUM;
    public static String HBASE_ZOOKEEPER_ZNODE_PARENT;

    /**
     * Kafka配置
     */
    public static String KAFKA_CONSUMER_ZOOKEEPER_CONNECT;
    public static String KAFKA_CONSUMER_GROUP_ID;
    public static String KAFKA_TOPIC;


    public LoadConfig(String path) {
        prop = new Properties();

        try {
            in = new BufferedInputStream(new FileInputStream(new File(path)));
            prop.load(in);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        initConfiguration();
    }

    public void initConfiguration() {
        HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT = prop.getProperty("hbase.zookeeper.property.clientPort");
        HBASE__ZOOKEEPER_QUORUM = prop.getProperty("hbase.zookeeper.quorum");
        HBASE_ZOOKEEPER_ZNODE_PARENT = prop.getProperty("hbase.zookeeper.znode.parent");

        KAFKA_CONSUMER_ZOOKEEPER_CONNECT = prop.getProperty("kafka.consumer.zookeeper.connect");
        KAFKA_CONSUMER_GROUP_ID = prop.getProperty("kafka.consumer.group.id");
        KAFKA_TOPIC = prop.getProperty("kafka.topic");
    }
}
