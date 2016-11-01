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
     * Canal配置
     */
    public static String DESTINATION;
    public static String CANAL_SERVER_LIST;
    public static String FILTER;
    public static Integer BATCH_SIZE;
    public static Integer TYPE;


    /**
     * kafka_produce 相关配置
     */
    public static String KAFKA_METADATA_BROKER_LIST;
    public static String KAFKA_PRODUCER_TYPE;
    public static String KAFAK_REQUEST_REQUIRED_ACK;
    public static String KAFKA_SERIALIZER_CLASS;
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
        DESTINATION = prop.getProperty("destination");
        CANAL_SERVER_LIST = prop.getProperty("canal_server_list");
        FILTER = prop.getProperty("filter");
        BATCH_SIZE = Integer.valueOf(prop.getProperty("batchSize"));
        TYPE = Integer.valueOf(prop.getProperty("type"));

        KAFKA_METADATA_BROKER_LIST = prop.getProperty("kafka.metadata.broker.list");
        KAFKA_PRODUCER_TYPE = prop.getProperty("kafka.producer.type");
        KAFAK_REQUEST_REQUIRED_ACK = prop.getProperty("kafka.request.required.acks");
        KAFKA_SERIALIZER_CLASS = prop.getProperty("kafka.serializer.class");
        KAFKA_TOPIC = prop.getProperty("kafka.topic");
    }
}
