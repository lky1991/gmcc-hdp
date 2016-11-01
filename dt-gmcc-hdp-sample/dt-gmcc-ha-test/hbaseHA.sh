#!/usr/bin/env bash

if [ $# -ne 2 ]; then
    echo "please input host name and (HMaster or HRegionServer)"
    exit 1
fi

ssh $1
su hbase
kill -9 `jps |grep $2|awk -F ' ' '{print $1}'`



