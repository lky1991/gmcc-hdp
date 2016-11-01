#!/usr/bin/env bash

if [ $# -ne 3 ]; then
    echo "please input  test directory and file num and file siez"
    exit 1
fi

HDP_VERSION = 2.3.4.0-3485
hadoop jar /usr/hdp/$HDP_VERSION/hadoop-mapreduce/hadoop-mapreduce-client-jobclient-tests.jar TestDFSIO -Dtest.build.data=$1 -write -nrFiles $2  -fileSize $3
hadoop jar /usr/hdp/$HDP_VERSION/hadoop-mapreduce/hadoop-mapreduce-client-jobclient-tests.jar TestDFSIO -Dtest.build.data=$1 -read -nrFiles $32 -fileSize $3