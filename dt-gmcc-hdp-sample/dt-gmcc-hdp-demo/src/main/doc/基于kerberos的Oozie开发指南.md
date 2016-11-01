##基于Kerberos的Oozie开发指南  
####一、开发注意事项
- 工作流的编写（workflow.xml）
- 配置文件的定义（job.properties）
####二、特定案例开发
#####案例说明
	在工作流中定义一个action执行hive数据库表的数据插入以及查询工作

#####开发流程
* 定义工作流workflow.xml
* 定义配置文件job.properties
* 上传工作流以及配置文件到HDFS中
* 执行Oozie任务（命令行方式）
  - oozie  job -oozie http://Oozie.server.ip:11000/oozie/  -config  ./job.properties  -run

#####案例的执行方法
 	sh runkrb.sh -serviceOozie -job /xx/xx/job.properties  -krb true  -princ principle -keytab keytab的路径 -config 配置文件路径
	例如：sh runkrb.sh -service Oozie -job /home/examples/apps/hive2/job.properties -krb true -princ "$principal" -keytab "$keytab" -config /home/hdpop/wally/target/hdp.properties

####三、总结
####在Kerberos和非Kerberos场景下的主要区别
通过在Kerberos和非Kerberos集群环境下测试发现：

* 从依赖的配置项的角度对比：不同
  - 命令行方式执行Oozie任务相同
  - Java API方式
     - 需要添加配置项oozie.authentication.type=Kerberos
  
* 从开发程序的角度对比：相同
* 从提交任务的角度对比：不同
   - 在Kerberos场景下，提交任务前要先使用principal&keytab进行用户身份认证，否则会出现Kerberos认证失败的错误，进而导致任务提交失败