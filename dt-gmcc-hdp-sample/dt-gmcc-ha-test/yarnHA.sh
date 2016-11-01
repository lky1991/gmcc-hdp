#!/usr/bin/env bash


if [ $# -ne 2 ]; then
    echo "please input host name and (ResourceManager or NodeManager)"
    exit 1
fi

ssh $1
su yarn
kill -9 `jps |grep $2|awk -F ' ' '{print $1}'`

hadoop jar /usr/hdp/current/hadoop-mapreduce-client/hadoop-mapreduce-client-jobclient-tests.jar mrbench -baseDir $1 -maps 100 -reduces 200 -numRuns 1
