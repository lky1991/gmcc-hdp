##基于kerberos的Spark 开发指南
####一、开发注意事项
* 如果处理的结果保存在HDFS中，指定的结果输出目录一定不要存在，否则会出现org.apache.hadoop.mapred.FileAlreadyExistsException异常
* 如果要想让最后的输出结果保存在一个文件中，在结果输出之前要重新设置RDD的分区个数(RDD.repartition(1))

####二、特定案例开发
#####案例说明
	首先，将本地的（2G小区的维表.csv）数据写入到HDFS中某一个具体的文件中，然后开发Spark程序对广东省每个市的2G小区的数量进行统计。

#####核心开发类说明
* SplitFunction:提取输入文件中的城市名
* PairsFunction：将String转换成Tuple类型
* ReduceFunction：根据key值对Tuple中的value进行汇总
#####开发流程
- 创建SparkConf对象
- 创建sparkContext对象
- 创建RDD对象，并对RDD进行转换和计算
- 将计算结果保存到hdfs的文件中     
#####案例的执行方法
    sh runkrb.sh -service Spark -input  inputPath -output outputpath  -krb true  -princ principle -keytab keytab的路径 -config 配置文件路径
	例如：sh runkrb.sh -service Spark -input /tmp/2G_user_location_data.csv -output /tmp/2G_user_location_data_output/$DATE  -krb true -princ "$principal" -keytab "$keytab" -config /home/hdpop/wally/target/hdp.properties

####三、总结
#####在Kerberos和非Kerberos场景下的主要区别
通过在Kerberos和非Kerberos集群环境下测试发现：

* 从依赖的配置项的角度对比：相同
* 从开发程序的角度对比：相同
* 从提交任务的角度对比：不同
  - 在Kerberos场景下，提交任务前要先使用principal&keytab进行用户身份认证，否则会出现Kerberos认证失败的错误，进而导致任务提交失败
    

