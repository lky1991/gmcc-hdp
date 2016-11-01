#!/usr/bin/env bash

if [ $# -ne 2 ]; then
    echo "please input data lines and data directory"
    exit 1
fi

HDP_VERSION = 2.3.4.0-3485
hadoop jar /usr/hdp/$HDP_VERSION/hadoop-mapreduce/hadoop-mapreduce-examples.jar teragen -D mapreduce.job.maps=100 $1 $2