#!/usr/bin/env bash
principal=hdpop/vm3.datatub.com@DATATUB.COM
keytab=/home/hdpop/makun/hdpop.keytab

DATE=$(date +%Y%m%d%X)

echo "生成1G的测试文件"
sh runkrb.sh -service TestFile -size 1  -krb true -princ "$principal" -keytab "$keytab"

echo "HDFS写入测试"
sh runkrb.sh -service HDFS -type write -filepath /tmp/2G_user_location_data.csv -krb true -princ "$principal" -keytab "$keytab"

echo "HDFS读取测试"
sh runkrb.sh -service HDFS -type read -filepath /tmp/2G_user_location_data.csv -krb true -princ "$principal" -keytab "$keytab"

echo "MR测试"
sh runkrb.sh -service MR -input /tmp/2G_user_location_data.csv -output /tmp/output_test -krb true -princ "$principal" -keytab "$keytab"

echo "Hive创建数据库测试"
sh runkrb.sh -service HIVE -type create -tbName table2g -krb true -princ "$principal" -keytab "$keytab"

echo "Hive插入数据测试"
sh runkrb.sh -service HIVE -type insert -tbName table2g -krb true -princ "$principal" -keytab "$keytab"

echo "Hive读取数据测试"
sh runkrb.sh -service HIVE -type read -tbName table2g -krb true -princ "$principal" -keytab "$keytab"

echo "Hive删除数据库测试"
sh runkrb.sh -service HIVE -type delete -tbName table2g -krb true -princ "$principal" -keytab "$keytab"

echo "HBase创建表测试"
sh runkrb.sh -service HBase -type createTb -tbName table2g  -krb true -princ "$principal" -keytab "$keytab"

echo "HBase插入数据"
sh runkrb.sh -service HBase -type put -tbName table2g -krb true -princ "$principal" -keytab "$keytab"

echo "HBase获取数据"
sh runkrb.sh -service HBase -type get -tbName table2g -key row10 -krb true -princ "$principal" -keytab "$keytab"

echo "HBase删除表"
sh runkrb.sh -service HBase -type delTb -tbName table2g -krb true -princ "$principal" -keytab "$keytab"

echo "Kafka生产数据"
sh runkrb.sh -service KafkaProducer -jaas path_2_jaas_file -broker broker_host:6667 -topic table2g -krb true -princ "$principal" -keytab "$keytab"

echo "Kafka消费数据"
sh runkrb.sh -service KafkaConsumer -jaas path_2_jaas_file -broker broker_host:6667 -topic table2g -krb true -princ "$principal" -keytab "$keytab"

echo "Spark测试"
sh runkrb.sh -service Spark -input /tmp/2G_user_location_data.csv -output /tmp/2G_user_location_data_output/$DATE  -krb true -princ "$principal" -keytab "$keytab"

echo "SparkStreaming测试"
sh runkrb.sh -service SparkStreaming -krb true -princ "$principal" -keytab "$keytab"

echo "Oozie测试"
sh runkrb.sh -service Oozie -job /home/examples/apps/hive2/job.properties -krb true -princ "$principal" -keytab "$keytab"