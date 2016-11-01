##基于kerberos的Spark Streaming开发指南
####一、开发注意事项
* 在创建JavaDStream对象时，如果数据源是文件类型，要注意文件路径的书写格式（本地文件前要加上前缀file://，HDFS中的文件前要加前缀hdfs://），
  否者文件中的数据不能读入到JavaDStream对象中。

####二、特定案例开发
#####案例说明
	对ambari的日志文件统计每小时的产生量

#####开发流程
- 创建SparkConf对象
- 创建JavaStreamingContext对象
- 创建JavaDStream对象，并对JavaDStream进行转换和计算
- 输出统计结果

#####案例的执行方法
   	sh runkrb.sh -service SparkStreaming -krb true  -princ principle -keytab keytab的路径 -config 配置文件路径
	例如：sh runkrb.sh -service SparkStreaming -krb true -princ "$principal" -keytab "$keytab" -config /home/hdpop/wally/target/hdp.properties

####三、总结
#####在Kerberos和非Kerberos场景下的主要区别
通过在Kerberos和非Kerberos集群环境下测试发现：

* 从依赖的配置项的角度对比：相同
* 从开发程序的角度对比：相同
* 从提交任务的角度对比：不同
  - 在Kerberos场景下，提交任务前要先使用principal&keytab进行用户身份认证，否则会出现Kerberos认证失败的错误，进而导致任务提交失败