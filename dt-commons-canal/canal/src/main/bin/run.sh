#!/usr/bin/env bash

CLASS=com.yeezhao.commons.kafka.ConsumerData
JARNAME=dt-commons-canal-1.0-SNAPSHOT-jar-with-dependencies.jar

print_usage(){
echo sh run.sh  ./canal.properties canal.log
}

if [ $# -ne 2 ]; then
    print_usage
    exit 1
fi

CONFIGFILENAME=$1
LOGFILE=$2

echo java -cp $JARNAME $CLASS $CONFIGFILENAME
nohup java -cp $JARNAME $CLASS $CONFIGFILENAME > $LOGFILE &