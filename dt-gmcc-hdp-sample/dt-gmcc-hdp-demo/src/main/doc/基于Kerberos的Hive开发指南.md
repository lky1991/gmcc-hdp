##基于Kerberos的Hive开发指南  
####一、依赖的配置项
* HiveServer的地址
* hiveServer的principal

####二、开发注意事项
* 在Kerberos场景下，通过beeline命令进行hiveServer的连接需要加上HiveServer的principal，否则将会kerberos认证失败，进而导致连接hiveServer失败
* 连接HiveServer之前要正确配置HiveServer的地址，否则不能连接到HiveServer
* Hive不能像关系数据库那样直接进行插入操作，只能通过加载文件做批量插入

####三、特定案例开发
#####案例说明
    利用2G小区的维表数据在Hive上实现了表的创建、删除，表中数据的查询以及向表中插入数据

#####核心开发方法说明
* connHive() ：连接hive数据库
* createHiveTable（）：创建table
* searchHiveTable（）：查询table中数据
* insertHiveTable（）：向table中插入数据
* deleteHiveTable（）：删除table
* closeResource()：关闭hive数据库
#####案例执行方法
 	 1）创建表
     sh runkrb.sh -service Hive -type  createTb  -tbName  table名称  -krb true  -princ principle -keytab keytab的路径  -config 配置文件路径
     例如：sh runkrb.sh -service Hive -type  createTb  -tbName hdptable  -krb true  -princ user/host@local_domain -keytab  /./user.keytab  -config /home/hdpop/wally/target/hdp.properties
 
     2）向表中插入数据
	  sh runkrb.sh -service Hive -type insert  -tbName  table名称  -krb true  -princ principle -keytab keytab的路径 -config 配置文件路径
      例如： sh runkrb.sh -service Hive -type insert  -tbName hdptable    -krb true  -princ user/host@local_domain -keytab  /./user.keytab -config /home/hdpop/wally/target/hdp.properties

     3）查询表中数据
      sh runkrb.sh -service Hive -type search  -tbName table名称  -key 查询条件  -krb true  -princ principle -keytab keytab的路径  -config 配置文件路径
      例如：sh runkrb.sh -service Hive -type search  -tbName hdptable -key 肇庆市   -krb true  -princ user/host@local_domain -keytab  /./user.keytab -config /home/hdpop/wally/target/hdp.properties

     4）删除表中数据
      sh runkrb.sh -service Hive -type deTb  -tbName  table名称  -krb true  -princ principle -keytab keytab的路径  -config 配置文件路径
      例如：sh runkrb.sh -service Hive -type deTb  -tbName hdptable   -krb true  -princ user/host@local_domain -keytab  /./user.keytab -config /home/hdpop/wally/target/hdp.properties

     
#####开发流程
- 创建hive数据库的连接
- 创建Statement对象
- 编写待执行的sql语句
- 执行sql语句，输出返回结果

####四、总结
#####在Kerberos和非Kerberos场景下的主要区别
通过在Kerberos和非Kerberos集群环境下测试发现：

* 从依赖的配置项的角度对比：相同
* 从开发程序的角度对比：不同
  - 两种场景下的不同主要体现在hiveServer的连接上：
    - 非kerberos场景下连接url的书写为:jdbc:hive2://hive2_host:port/database;user=User;password=userPassword
    - Kerberos场景下连接url的书写为：jdbc:hive2://hive2_host:port/database;principal=hive/hive2_host@YOUR-REALM.COM
      
* 从提交任务的角度对比：不同
   - 在Kerberos场景下，提交任务前要先使用principal&keytab进行用户身份认证，否则会出现Kerberos认证失败的错误，进而导致任务提交失败


  
