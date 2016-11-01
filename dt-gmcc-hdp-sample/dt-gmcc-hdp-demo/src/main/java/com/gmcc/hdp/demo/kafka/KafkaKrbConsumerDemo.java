package com.gmcc.hdp.demo.kafka;

import com.gmcc.hdp.demo.util.HDPSampleConfiguration;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by $wally on 2016/7/21.
 */
public class KafkaKrbConsumerDemo {
    public void consumer(String topic) {

//        String topic="jim";
        Properties properties = new Properties();

        //依赖的Zookeeper服务器地址
        properties.put("zookeeper.connect", HDPSampleConfiguration.KAFKA_ZOOKEEPER_CONNECT_LIST);
        //consumer对象所属的group.id
        properties.put("group.id", HDPSampleConfiguration.KAFKA_GROUP_ID);

        ConsumerConnector consumer= Consumer.createJavaConsumerConnector(new ConsumerConfig(properties) );

        Map<String, Integer> topicMap = new HashMap<String, Integer>();
        topicMap.put(topic, 1);

        Map<String, List<KafkaStream<byte[], byte[]>>> streamMap = consumer.createMessageStreams(topicMap);
        KafkaStream<byte[], byte[]> stream = streamMap.get(topic).get(0);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();

        System.out.println("---开始消费数据---");
        while (it.hasNext()) {
            System.out.println("消费数据: " + new String(it.next().message()));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        String topic=args[1];
        Properties properties = new Properties();

        String configPath=args[4];
        HDPSampleConfiguration hdpSampleConfiguration=null;
        if(configPath.isEmpty()){
            System.out.println("please input config path");
            return;
        }else{
            hdpSampleConfiguration=new HDPSampleConfiguration(configPath);
        }

        properties.put("auto.offset.reset", "smallest");
        //依赖的Zookeeper服务器地址
        properties.put("zookeeper.connect", HDPSampleConfiguration.KAFKA_ZOOKEEPER_CONNECT_LIST);
        properties.put("zookeeper.session.timeout.ms", "40000");
        properties.put("zookeeper.sync.time.ms", "200");
        properties.put("auto.commit.interval.ms", "1000");
        //consumer对象所属的group.id
        properties.put("group.id", HDPSampleConfiguration.KAFKA_GROUP_ID);

//        properties.put("metadata.broker.list", HDPSampleConfiguration.KAFKA_BROKER_LIST);
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");
        properties.put("security.protocol", "PLAINTEXTSASL");
        properties.put("rebalance.max.retries", "5");
        properties.put("rebalance.backoff.ms", "1200");

        ConsumerConnector consumer= Consumer.createJavaConsumerConnector(new ConsumerConfig(properties) );

        Map<String, Integer> topicMap = new HashMap<String, Integer>();
        topicMap.put(topic, 1);

        Map<String, List<KafkaStream<byte[], byte[]>>> streamMap = consumer.createMessageStreams(topicMap);
        KafkaStream<byte[], byte[]> stream = streamMap.get(topic).get(0);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();

        System.out.println("---开始消费数据---");
        System.out.println(it.next().message());
        while (it.hasNext()) {
            System.out.println("消费数据: " + new String(it.next().message()));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
