package com.yeezhao.commons.kafka;

import com.yeezhao.commons.util.LoadConfig;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by $wally on 2016/9/13.
 * 为了保证数据的强有序性，在创建topic时只能有一个分片
 * 为了保证数据的完整性，要使用同步的方式传输数据（ack=-1）
 */
public class ProducerData {
    private Producer<String, String> producer = null;

    public ProducerData() {
        this.producer = new Producer<String, String>(createProducerConfig());
    }

    /**
     * 创建Producer的配置文件
     *
     * @return
     */
    private ProducerConfig createProducerConfig() {
        Properties properties = new Properties();
        //broker列表地址
        properties.put("metadata.broker.list", LoadConfig.KAFKA_METADATA_BROKER_LIST);
        properties.put("producer.type", LoadConfig.KAFKA_PRODUCER_TYPE);//使用同步的方式传输数据
        properties.put("request.required.acks", LoadConfig.KAFAK_REQUEST_REQUIRED_ACK);// 触发acknowledgement机制，保证数据至少被发送一次到服务器

        //指定message的序列化编码类（该编码类不是固定不变的，可以根据不同的业务需求来定制开发message的序列化编码类）
        properties.put("serializer.class", LoadConfig.KAFKA_SERIALIZER_CLASS);
        return new ProducerConfig(properties);
    }

    public Producer<String, String> getInstance() {
        return producer;
    }
}
