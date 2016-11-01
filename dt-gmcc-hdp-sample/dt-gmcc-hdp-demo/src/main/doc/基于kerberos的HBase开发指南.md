##基于Kerberos的HBase开发指南
####一、依赖的配置项
* HBase依赖的Zookeeper服务器地址
* HBase依赖的Zookeeper的通信端口
* HBase在其依赖的Zookeeper上的目录
* HBase集群Master在kerberos中的principal
* HBase集群Regionserver在kerberos中的principal
* HBase安全认证方式
* Hadoop安全认证方式

####二、开发注意事项
 * 在创建HBaseAdmin、HTable对象前一定要保证依赖的配置项正确配置,否则会出现连接不上HBase集群的错误
 * 在使用完HBaseAdmin和HTable后一定要及时关闭，因为每创建一个HBaseAdmin和HTable对象，JVM会在JVM内存中创建一个线程对象的同时也会创建一个操作系统线程，
 而这个操作系统线程使用的内存不是JVM中的内存，而是操作系统中剩下的内存，如果过多的HBaseAdmin和HTable线程没有及时释放，会出现 unable to create new native thread
 的错误。

####三、特定案例开发
#####案例说明
	利用2G小区的维表数据在HBase上实现了表的创建、删除，表中数据的查询以及向表中插入数据
#####案例中HBase的表设计

* 主键的设计原则
  - 唯一性
  - 通过实验发现：如果操作该表的业务偏向读，最好将该表的rowkey设计成连续的序号，这样可以使数据顺序存储在region中，从而可以使用scan方法快速获取表中数据；
   如果操作该表的业务偏向写，最好将rowkey用散列方式表示，从而使数据存储在不同的region中，这样的存储方式不但能够减少了region的频繁分片，而且可以提高数据写的并发性。
* 本案例主键的设计说明
  - 由于操作该表的业务偏向读，所以将该表的rowkey设计成连续的序号（row1，row2，....）
#####核心开发方法说明
* create():在HBase中创建表
* put():向表中插入一条数据
* get():根据RowKey查找表中数据
* delete():删除HBase中的表
* initTable()：初始化表数据
#####开发流程
* 创建HBase的配置文件
* 创建HBaseAdmin对象，通过HBaseAdmin对象在HBase中进行表的创建和删除
* 创建HTable对象，通过HTable对象对特定的表进行增、删、改、查

#####案例执行方法
	1）创建表
       sh runkrb.sh -service HBase -type createTb -tbName tableName(表名) -krb true  -princ principle -keytab keytab的路径  -config 配置文件路径
	   例如： sh runkrb.sh -service HBase -type createTb -tbName mobile_table  -krb true  -princ user/host@local_domain -keytab  /./user.keytab -config /home/hdpop/wally/target/hdp.properties
	2）向表中插入数据
        sh runkrb.sh -service HBase -type  put    -tbName  tableName(表名)  -krb true  -princ principle -keytab keytab的路径  -config 配置文件路径
		例如： sh runkrb.sh -service HBase -type  put    -tbName  mobile_table  -krb true  -princ user/host@local_domain -keytab  /./user.keytab -config /home/hdpop/wally/target/hdp.properties
    3）根据RowKey在表中查找数据
		sh runkrb.sh -service HBase -type  get   -tbName  tableName(表名)  -key  RowKey（主键）-krb true  -princ principle -keytab keytab的路径  -config 配置文件路径
		例如： sh runkrb.sh -service HBase -type  get   -tbName  mobile_table  -key  row10  -krb true  -princ user/host@local_domain -keytab  /./user.keytab -config /home/hdpop/wally/target/hdp.properties
    4）删除表
	   sh runkrb.sh -service HBase -type   delTb   -tbName  tableName(表名)  -krb true  -princ principle  -keytab  keytab的路径  -config 配置文件路径
	   例如：sh runkrb.sh -service HBase -type   delTb   -tbName  mobile_table  -krb true  -princ user/host@local_domain  -keytab  /./user.keytab  -config /home/hdpop/wally/target/hdp.properties

####四、总结
#####在Kerberos和非Kerberos场景下的主要区别
通过在Kerberos和非Kerberos集群环境下测试发现：

* 从依赖的配置项的角度对比：不同
     - 在Kerberos场景下需要增加以下配置项
        - hadoop.security.authentication->Kerberos
        - hbase.security.authentication->Kerberos
        - hbase.master.kerberos.principal->hbase/host@local_domain
        - hbase.regionserver.kerberos.principal->hbase/host@local_domain
	  
* 从开发程序的角度对比：相同
* 从提交任务的角度对比：不同
    - 在Kerberos场景下，提交任务前要先使用principal&keytab进行用户身份认证，否则会出现Kerberos认证失败的错误，进而导致任务提交失败