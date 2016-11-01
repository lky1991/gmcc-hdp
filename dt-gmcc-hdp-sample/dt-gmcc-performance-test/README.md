##Hadoop基线性能测试脚本
###一、IAAS层
####（1）磁盘IO
* 写性能
 - sh diskWrite.sh a b
 - a：文件名，b:文件大小单位为1G
* 读性能 
 - sh diskRead.sh a
 - a:文件路径
####（2）网络IO
* sh networkIO.sh a b c
* a:主机ip，b：源文件名，c:目标文件名
###二、HAAS层
####（1）HDFS读、写基线性能测试
 - sh hdfsIO.sh a b c
 - a:测试目录，b:文件个数，c:文件大小
####（2）HDFS负载测试
- sh nameNode.sh a
- a:文件数目
####（3）mapReduce性能测试
- sh mr.sh a b
- a:测试目录，b:任务个数
####（4）综合性能测试
* 数据生成
 - sh dataGen.sh a b
 - a:生成测试数据的行数，b:生成数据的存放目录
* 数据排序
 - sh teraSort.sh a b c
 - a:排序的reduce数目，b：待排序的数据存放目录，c：排序后数据的存放目录
* 排序验证
 - sh teraValide.sh a b c 
 - a:reduce数目，b:数据的输入目录，c:输出目录
####（5）HBase
* 顺序写
 - sh sequentialWrite.sh  a
 -　a:客户端的并发数
* 顺序读
 - sh sequentialRead.sh a
 - a:客户端的并发数
* 随机写
 - sh randomWrite.sh a
 - a:客户端的并发数
* 随机读
 - sh randomRead.sh a
 - a:客户端的并发数
####(6)Hive
 - sh hiveTest1.sh a b
 - a:查询数据量大小，b:表名
####(7)kafka
* 生产者
 - sh kafkaProducer.sh a b c
 - a:topic名称，b:消息数据，c：生产者个数
* 消费者
 - sh kafkaConsumer.sh a b c
 - a:topic名称，b:消费者个数
* 消息大小
 - sh kafkaMessageSize.sh a b
 - a:topic名称，b:消息大小
* topic分片
 - sh kafkaPartition.sh a b
 - a:topic名称，b:topic 分片个数
* topic 备份个数
 - sh kafkaReplication.sh a b
 - a:topic名称，b:topic备份个数
####(8)spark
- sh sparkTest.sh a b c
- a:文件大小，b:文件名，c:executor个数



