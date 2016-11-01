package com.yeezhao.commons.kafka;

import com.alibaba.fastjson.JSON;
import com.yeezhao.commons.entity.HBaseEntity;
import com.yeezhao.commons.hbase.HBaseUtil;
import com.yeezhao.commons.util.LoadConfig;
import kafka.consumer.*;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by $wally on 2016/9/13.
 */
public class ConsumerData {
    private ConsumerConnector consumer = null;
    private HBaseUtil hBaseUtil = new HBaseUtil();
    protected final static Logger logger = LoggerFactory.getLogger(ConsumerData.class);

    public ConsumerData() {
        this.consumer = Consumer.createJavaConsumerConnector(createConsumerConfig());
    }

    /**
     * 创建Consumer的配置文件
     *
     * @return
     */
    public ConsumerConfig createConsumerConfig() {
        Properties properties = new Properties();

        //依赖的Zookeeper服务器地址
        properties.put("zookeeper.connect", LoadConfig.KAFKA_CONSUMER_ZOOKEEPER_CONNECT);
        //consumer对象所属的group.id
        properties.put("group.id", LoadConfig.KAFKA_CONSUMER_GROUP_ID);

        return new ConsumerConfig(properties);
    }

    /**
     * Consumer 消费数据
     */
    public void consumerData(String topic) throws IOException {
        Whitelist whitelist = new Whitelist(topic);
        List<KafkaStream<byte[], byte[]>> partitions = consumer.createMessageStreamsByFilter(whitelist);

        for (KafkaStream<byte[], byte[]> partition : partitions) {//遍历分区片
            ConsumerIterator<byte[], byte[]> iterator = partition.iterator();
            while (iterator.hasNext()) {
                MessageAndMetadata<byte[], byte[]> next = iterator.next();
                String message = new String(next.message(), "utf-8");
                System.out.println(message);
                synDataToHBase(message);//同步数据
                consumer.commitOffsets();
            }
        }
    }

    public void synDataToHBase(String message) {
        message = message.trim();
        if (message.length() == 0) {
            logger.error("---syn data failure---");
            return;
        }

        HBaseEntity hBaseEntity = JSON.parseObject(message, HBaseEntity.class);
        String opertion = null;//操作类型
        String db = null;//数据库名
        String table = null;//表
        String rowkey = null;//主键
        ArrayList<String> column = null;//字段
        ArrayList<String> data = null;//字段对应的数值
        StringBuilder sb = new StringBuilder();

        opertion = hBaseEntity.getType();
        db = hBaseEntity.getDb();

        if (opertion == null) {
            logger.error("---syn data failure---");
            return;
        }

        if (opertion.equals("QUERY")) {
            hBaseUtil.createNameSpace(db);
            logger.info("---create namespace success---");
        } else {
            table = hBaseEntity.getTable();

            sb.append(db);
            sb.append(":");
            sb.append(table);
            table = sb.toString();

            if (opertion.equals("CREATE")) {
                hBaseUtil.create(table);
                logger.info("---create table success---");
            } else if (opertion.equals("ERASE")) {
                hBaseUtil.deleteTable(table);
                logger.info("---delete table success---");
            } else {
                rowkey = hBaseEntity.getRowKey();
                column = hBaseEntity.getColumnList();
                data = hBaseEntity.getDataList();

                if (opertion.equals("INSERT") || opertion.equals("UPDATE")) {
                    hBaseUtil.putRow(table, rowkey, column, data);
                    logger.info("---update data success---");
                } else if (opertion.equals("DELETE")) {
                    hBaseUtil.delRow(table, rowkey);
                    logger.info("---delete data success---");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        LoadConfig loadConfig = new LoadConfig(args[0]);
        ConsumerData demo = new ConsumerData();
        demo.consumerData(LoadConfig.KAFKA_TOPIC);
    }
}
