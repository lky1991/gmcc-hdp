##Hadoop高可用测试脚本
###（1）HDFS HA
* sh nameNodeHA.sh a b c
* a:主机名，b:NameNode or DataNode,c:文件名
###（2）YARN HA
* sh yarnHA.sh a b
* a:主机名，b:ResourceManager or NodeManager
###(3) HBase HA
* sh hbaseHA.sh a b
* a:主机名，b:HMaster or HRegionServer
###(4) Hive HA
* sh hiveHA.sh a
* a:主机名
###(5) kafka HA
* sh kafkaHA.sh a b
* a:主机名，b:topic 名称
###(6) KDC HA
* sh kdcHA.sh a b
* a:keytab路径，b:principal