##基于Kerberos的HDFS 开发指南  
####一、开发注意事项
*  HDFS中文件路径的书写一定要正确，否则会出现文件路径找不到的错误
*  HDFS中文件或目录的读写权限一定要控制好，否则会出现没有权限读、写的错误
####二、特定案例开发
#####案例说明
	将2G小区的维表数据写入到HDFS中，从HDFS的文件中读数据到本地

#####核心开发方法说明
* readHDFSFile：从HDFS的文件中读数据到本地
* upFileToHDFSFromLocal：将本地文件写入到HDFS中
#####开发流程
* 将本地文件写入到HDFS中
   - 创建FileSystem对象
   - 创建FSDataOutputStream对象
   - 使用FSDataOutputStream对象将本地文件写入到HDFS中
* 从HDFS的文件中读数据
   - 创建FileSystem对象
   - 创建FSDataInputStream对象 
   - 使用FSDataInputStream对象从HDFS中读数据到本地
#####案例的执行方法
 	1)sh runkrb.sh -service HDFS  -type  write  -filepath  Path  -krb true  -princ principle -keytab keytab的路径 -config 配置文件路径
	例如：sh runkrb.sh -service HDFS -type write -filepath /tmp/2G_user_location_data.csv -krb true -princ "$principal" -keytab "$keytab" -config /home/hdpop/wally/target/hdp.properties

	
	2)sh runkrb.sh -service HDFS  -type  read  -filepath  Path  -krb true   -princ principle -keytab keytab的路径  -config 配置文件路径
	例如：sh runkrb.sh -service HDFS  -type read -filepath /tmp/wally.txt   -krb true  -princ user/host@local_domain -keytab  /./user.keytab -config /home/hdpop/wally/target/hdp.properties

####三、总结
#####在Kerberos和非Kerberos场景下的主要区别
通过在Kerberos和非Kerberos集群环境下测试发现：

* 从依赖的配置项的角度对比：相同
* 从开发程序的角度对比：相同
* 从提交任务的角度对比：不同
  - 在Kerberos场景下，提交任务前要先使用principal&keytab进行用户身份认证，否则会出现Kerberos认证失败的错误，进而导致任务提交失败