package com.yeezhao.commons.mysql;

import com.yeezhao.commons.client.CommonClient;
import com.yeezhao.commons.kafka.ProducerData;
import com.yeezhao.commons.util.LoadConfig;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

/**
 * Created by makun on 16/9/7.
 */
public class MysqlReader {

    public static void main(String[] args) {
        LoadConfig loadConfig = new LoadConfig(args[0]);

        String destination = LoadConfig.DESTINATION;
        String ipList = LoadConfig.CANAL_SERVER_LIST;
        String filter = LoadConfig.FILTER;
        int type = LoadConfig.TYPE;//连接类型(1:单节点模式，2：集群模式)

        CommonClient commonClient = new CommonClient(destination, ipList, type, filter);
        ProducerData producerData = new ProducerData();
        Producer<String, String> producer = producerData.getInstance();

        int batchSize = LoadConfig.BATCH_SIZE;
        while (true) {
            commonClient.process(batchSize);
            for (String hBaseEntity : commonClient.gethBaseEntities()) {
                System.out.println(hBaseEntity);
                KeyedMessage<String, String> data = new KeyedMessage<String, String>(LoadConfig.KAFKA_TOPIC, hBaseEntity);
                producer.send(data);
            }
        }
    }
}
