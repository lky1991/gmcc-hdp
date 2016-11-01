#!/usr/bin/env bash

if [ $# -ne 2 ]; then
    echo "please input host name and (NameNode or DataNode)and file name"
    exit 1
fi

ssh $1
su hdfs
kill -9 `jps |grep $2|awk -F ' ' '{print $1}'`

hadoop fs -put $3 /tmp/
hadoop fs -cat /tmp/$3

