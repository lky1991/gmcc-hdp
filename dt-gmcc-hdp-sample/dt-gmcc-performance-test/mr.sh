#!/usr/bin/env bash

if [ $# -ne 2 ]; then
    echo "please input test directory and job nums"
    exit 1
fi

hadoop jar /usr/hdp/current/hadoop-mapreduce-client/hadoop-mapreduce-client-jobclient-tests.jar mrbench -baseDir $1 -maps 100 -reduces 200 -numRuns $2