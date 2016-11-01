##基于Kerberos的MR开发指南
####一、开发注意事项
* 提交MR任务的系统用户要与principle用户一致，否则会导致任务提交失败
####二、特定案例开发
#####案例说明
	首先，将本地的（2G小区的维表.csv）数据写入到HDFS中某一个具体的文件中，然后开发MR程序
    对广东省每个市的2G小区的数量进行统计。

#####核心开发类说明
* TokenizerMapper：提取每个小区所属城市，并生成tuple（城市，1）
* IntSumReducer：对相同城市的tuple进行合并

#####开发流程
* 开发Map类
* 开发Reduce类
* 配置Job任务的属性，指定Map类、指定Reduce类、指定结果输出类型，添加数据的输入路径，添加结果的输出路径

#####案例的执行方法
 	sh runkrb.sh -service MR   -input  inputPath  -output  outputPath -krb true  -princ principle -keytab keytab的路径  -config 配置文件路径  
	例如：sh runkrb.sh -service MR  -input  /tmp/wally.txt  -output /tmp/output/  -krb true  -princ principle -keytab keytab的路径  -config /home/hdpop/wally/target/hdp.properties

####三、总结
#####在Kerberos和非Kerberos场景下的主要区别
通过在Kerberos和非Kerberos集群环境下测试发现：

* 从依赖的配置项的角度对比：相同
* 从开发程序的角度对比：相同
* 从提交任务的角度对比：不同
     - 在Kerberos场景下，提交任务前要先使用principal&keytab进行用户身份认证，否则会出现Kerberos认证失败的错误，进而导致任务提交失败