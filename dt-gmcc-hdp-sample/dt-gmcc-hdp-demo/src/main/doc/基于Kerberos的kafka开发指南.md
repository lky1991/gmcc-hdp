##基于Kerberos的Kafka开发指南  
####一、依赖的配置项
#####（1）producer对象
* metadata.broker.list（broker列表地址）
* serializer.class（指定message的序列化编码类）
* security.protocol（指定使用的安全认证协议）
* kafka安全策略配置（kafka_client_jaas.conf）

#####（2）consumer对象
* zookeeper.connect（依赖的Zookeeper服务器地址）
* group.id（consumer对象所属的group.id）
* security.protocol（指定使用的安全认证协议）
* kafka安全策略配置（kafka_client_jaas.conf）

####二、开发注意事项
* 在创建producer对象前一定要保证metadata.broker.list的ip和端口正确无误，否则将不能生产数据到kafka队列中。
* 在创建consumer对象前一定要保证Zookeeper服务器地址正确无误，否则将不能从kafka队列中消费数据。
* 使用开发的Kafka API要和安装的Kafka版本保持一致，否则将不能正确创建producer和consumer对象。
* 导包时要区分导的包是用于Scala开发还是Java开发，否则将不能正确创建producer和consumer对象。
 - kafka.producer.Produce：使用Scala语言进行开发
 - kafka.javaapi.producer.Producer：使用Java语言进行开发
 - kafka.consumer.Consumer：使用Scala语言进行开发
 - kafka.javaapi.consumer.ConsumerConnector：使用Java语言进行开发

	
####特定案例开发
#####案例说明
    生产者生产数据、消费者消费数据

#####案例执行方法
    1)生产数据
        sh runkrb.sh -service KafkaProducer  -jass  /xx/xx/kafka_client_jaas_.conf -broker host:port -topic topic1 -krb true  -princ principle -keytab keytab的路径 -config 配置文件路径
       例如： sh runkrb.sh -service KafkaProducer -jaas /home/hdpop/wally/kafka_client_jaas_2.conf  -broker vm3.datatub.com:6667 -topic table2g -krb true -princ "$principal" -keytab "$keytab" -config /home/hdpop/wally/target/hdp.properties
    
    
    2)消费数据
		sh runkrb.sh -service KafkaConsumer  -jass  /xx/xx/kafka_client_jaas_.conf -broker host:port -topic topic1 -krb true  -princ principle -keytab keytab的路径 -config 配置文件路径
       例如：sh runkrb.sh -service KafkaConsumer -jaas /home/hdpop/wally/kafka_client_jaas_2.conf  -broker vm3.datatub.com:6667 -topic test -krb true -princ 
     "$principal" -keytab "$keytab" -config /home/hdpop/wally/target/hdp.properties

####三、总结
####在Kerberos和非Kerberos场景下的主要区别
通过在Kerberos和非Kerberos集群环境下测试发现：

* 从依赖的配置项的角度对比：不同
   - 需要添加使用的安全认证协议
* 从开发程序的角度对比：相同
* 从提交任务的角度对比：不同
  - 在Kerberos场景下：kafka的producer和consumer在提交任务时要添加上参数-Djava.security.auth.login.config=/xx/xx/kafka_client_jaas.conf进行kerberos身份认证
     
   


 



