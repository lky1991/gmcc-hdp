#!/usr/bin/env bash

if [ $# -ne 3 ]; then
    echo "please input reduce num and inputpath and outpath"
    exit 1
fi

HDP_VERSION = 2.3.4.0-3485
hadoop jar /usr/hdp/$HDP_VERSION/hadoop-mapreduce/hadoop-mapreduce-examples.jar terasort -Dmapred.reduce.tasks=$1 $2 $3

