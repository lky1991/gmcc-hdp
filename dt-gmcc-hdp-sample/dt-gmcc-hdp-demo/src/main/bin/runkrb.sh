#!/usr/bin/env bash
print_usage ()
{
    echo "Usage: sh run.sh COMMAND"
    echo "where COMMAND is one of the follows:"
    echo "-service TestFile -size <文件大小,G为单位> 生成测试文件"
    echo "-service HBase -type <createTb|delTb|put|get>    -tbName <创建和读取2G用户维表的表名>      -key <HBase rowkey>      -krb <true|false,true表示在kerberos环境下执行>  -princ <kerberos下的principle名称>   -keytab <kerberos下的keytable路径>  进行基于hbase的样例执行"
    echo "-service HDFS  -type <write|read>                -filepath <HDFS读写2G用户维表的文件路径>     -krb <true|false,true表示在kerberos环境下执行>  -princ <kerberos下的principle名称>   -keytab <kerberos下的keytable路径>  进行基于hdfs的样例执行"
    echo "-service MR    -input <HDFS中2G用户维表的文件路径>  -output <存放统计结果的HDFS的文件路径>       -krb <true|false,true表示在kerberos环境下执行>  -princ <kerberos下的principle名称>   -keytab <kerberos下的keytable路径>  进行基于mr的样例执行"
    echo "-service HIVE  -type <create|read|insert|delete>        -dbName <数据库>   -tbName <数据库表>  -key <检索市区名称,例如广州>     -krb <true|false,true表示在kerberos环境下执行>  -princ <kerberos下的principle名称>   -keytab <kerberos下的keytable路径>  进行基于hive的样例执行"
    echo "-service Spark -input <输入HDFS路径> -output <输入HDFS路径> -krb <true|false,true表示在kerberos环境下执行>  -princ <kerberos下的principle名称>   -keytab <kerberos下的keytable路径> 进行基于Spark的计数功能"
    echo "-service SparkStreaming -krb <true|false,true表示在kerberos环境下执行>  -princ <kerberos下的principle名称>   -keytab <kerberos下的keytable路径> 基于SparkStreaming对Ambari-agent日志统计"
    echo "-service KafkaProducer -jaas <Kafka Client对应的jaas文件>  -broker <broker地址> -topic <topic名称>  -krb <true|false,true表示在kerberos环境下执行>  -princ <kerberos下的principle名称>   -keytab <kerberos下的keytable路径> 基于Kafka进行操作"
    echo "-service KafkaConsumer -jaas <Kafka Client对应的jaas文件>  -broker <broker地址> -topic <topic名称>  -krb <true|false,true表示在kerberos环境下执行>  -princ <kerberos下的principle名称>   -keytab <kerberos下的keytable路径> 基于Kafka进行操作"
    echo "-service Oozie -job <执行Oozie任务的job.properties本地路径> -krb <true|false,true表示在kerberos环境下执行>  -princ <kerberos下的principle名称>   -keytab <kerberos下的keytable路径>  执行基于Oozie的任务提交"
    exit 1
}

if [ $# = 0 ] || [ $1 = "help" ]; then
  print_usage
fi

COMMAND=$2

CLASS=com.gmcc.hdp.demo.cli.RunningKrbCli
JARNAME=dt-gmcc-hdp-demo-1.0-SNAPSHOT-jar-with-dependencies.jar
params=$@

if [ "$COMMAND" = "TestFile" ]; then
  echo yarn jar $JARNAME $CLASS $params
  yarn jar $JARNAME $CLASS $params
elif [ "$COMMAND" = "HDFS" ]; then
  echo yarn jar $JARNAME $CLASS $params
  yarn jar $JARNAME $CLASS $params > "$COMMAND"_"$4""_krb.log"
elif [ "$COMMAND" = "HBase" ]; then
  echo yarn jar $JARNAME $CLASS $params
  nohup yarn jar $JARNAME $CLASS $params > "$COMMAND"_"$4""_krb.log" &
elif [ "$COMMAND" = "HIVE" ]; then
  echo yarn jar $JARNAME $CLASS $params
  nohup yarn jar $JARNAME $CLASS $params > "$COMMAND"_"$4""_krb.log" &
elif [ "$COMMAND" = "MR" ]; then
  echo yarn jar $JARNAME $CLASS $params
  nohup yarn jar $JARNAME $CLASS $params > "$COMMAND""_krb.log" &
elif [ "$COMMAND" = "Spark" ]; then
  echo spark-submit --class $CLASS  --master yarn-client $JARNAME ${10} ${12} $4 $6
  nohup spark-submit --class $CLASS  --master yarn-client $JARNAME ${10} ${12} $4 $6  > "$COMMAND""_krb.log" &
elif [ "$COMMAND" = "SparkStreaming" ]; then
  echo spark-submit --class $CLASS --master yarn-client $JARNAME $6 $8 ${10}
  nohup spark-submit --class $CLASS --master yarn-client $JARNAME $6 $8 ${10}> "$COMMAND""_krb.log" &
elif [ "$COMMAND" = "Oozie" ]; then
  CLASS=com.gmcc.hdp.demo.oozie.OozieKrbDemo
  echo yarn jar $JARNAME $CLASS $8 ${10} $4 ${12}
  nohup yarn jar $JARNAME $CLASS $8 ${10} $4 ${12} > "$COMMAND""_krb.log" &
elif [ "$COMMAND" = "KafkaProducer" ]; then
  CLASS=com.gmcc.hdp.demo.kafka.KafkaKrbProducerDemo
  echo java -Djava.security.auth.login.config="$4" -cp $JARNAME $CLASS $6 $8 ${12} ${14}
  nohup java -Djava.security.auth.login.config="$4" -cp $JARNAME $CLASS $6 $8 ${12} ${14} > "$COMMAND""_krb.log" &
elif [ "$COMMAND" = "KafkaConsumer" ]; then
  CLASS=com.gmcc.hdp.demo.kafka.KafkaKrbConsumerDemo
  echo java -Djava.security.auth.login.config="$4" -cp $JARNAME $CLASS $6 $8 ${12} ${14} ${16}
  nohup java -Djava.security.auth.login.config="$4" -cp $JARNAME $CLASS $6 $8 ${12} ${14} ${16}> "$COMMAND""_krb.log" &
else
  print_usage
  exit 1
fi