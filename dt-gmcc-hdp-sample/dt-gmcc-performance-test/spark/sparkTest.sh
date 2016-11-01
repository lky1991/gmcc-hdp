#!/usr/bin/env bash

if [ $# -ne 3 ]; then
    echo "please input file size and file name and executor num"
    exit 1
fi

PRINCIPLE = hdp@DATATUB.COM
KEYTAB = /etc/security/hdfs.keytab
CONFIGPATH =/home/hdpop/wally/hdp.properties

sh runkrb.sh -service TestFile -size $1 -krb true -princ $PRINCIPLE -key $KEYTAB -config $CONFIGPATH

HADOOPPATH = /tmp/$2
hadoop fs -copyFromLocal /tmp/2G_user_location_data.csv  $HADOOPPATH
spark-submit  --class org.apache.spark.examples.JavaWordCount  --num-executors $3  --master yarn-cluster /usr/hdp/current/spark-client/lib/spark-examples*.jar   $HADOOPPATH