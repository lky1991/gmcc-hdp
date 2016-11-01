#!/usr/bin/env bash

if [ $# -ne 1 ]; then
    echo "please input file num"
    exit 1
fi

JARNAME = hadoop-mapreduce-client-jobclient-tests.jar
hadoop jar /usr/hdp/current/hadoop-mapreduce-client/$JARNAME nnbench -operation create_write -maps 50 -reduces 50 -blockSize 1 -bytesToWrite 0 -numberOfFiles $1 -replicationFactorPerFile 3 -readFileAfterOpen true
