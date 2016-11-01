#!/usr/bin/env bash

DATE=$(date +%Y%m%d%X)

echo "生成1G的测试文件"
sh run.sh -service TestFile -size 1

echo "HDFS写入测试"
sh run.sh -service HDFS -type write -filepath /tmp/2G_user_location_data.csv

echo "HDFS读取测试"
sh run.sh -service HDFS -type read -filepath /tmp/2G_user_location_data.csv

echo "MR测试"
sh run.sh -service MR -input /tmp/2G_user_location_data.csv -output /tmp/output_test/$DATE

echo "Hive创建数据库测试"
sh run.sh -service HIVE -type create -dbName table2g

echo "Hive插入数据测试"
sh run.sh -serivce HIVE -type insert -dbName table2g

echo "Hive读取数据测试"
sh run.sh -service HIVE -type read -dbName table2g

echo "Hive删除数据库测试"
sh run.sh -service HIVE -type delete -dbName table2g

echo "HBase创建表测试"
sh run.sh -service HBase -type createTb -tbName table2g

echo "HBase插入数据"
sh run.sh -service HBase -type put -tbName table2g

echo "HBase获取数据"
sh run.sh -service HBase -type get -tbName table2g -key row10

echo "HBase删除表"
sh run.sh -service HBase -type delTb -tbName table2g

echo "Spark测试"
sh run.sh -service Spark -input /tmp/2G_user_location_data.csv -output /tmp/output_test/$DATE

echo "SparkStreaming测试"
sh run.sh -service SparkStreaming

echo "Oozie测试"
sh run.sh -service Oozie -job /home/examples/apps/hive2/job.properties

echo "Kafka生产测试"
sh run.sh -service Kafka -type producer -broker broker_host:6667 -topic table2g

echo "Kafka生产数据测试"
sh run.sh -service Kafka -type producer -topic table2g

echo "Kafka消费数据测试"
sh run.sh -service Kafka -type consumer -topic table2g