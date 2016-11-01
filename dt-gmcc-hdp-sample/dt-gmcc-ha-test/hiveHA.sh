#!/usr/bin/env bash

if [ $# -ne 2 ]; then
    echo "please input host name"
    exit 1
fi

ssh $1
su hive
kill -9 `jps |grep HiveServer2|awk -F ' ' '{print $1}'`
kill -9 `jps |grep HiveMetatore|awk -F ' ' '{print $1}'`

hive<<EOF
show tables;
EOF