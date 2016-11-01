#!/usr/bin/env bash

CLASS=com.yeezhao.commons.mysql.MysqlReader
JARNAME=dt-commons-canal-client-1.0-SNAPSHOT-jar-with-dependencies.jar

print_usage(){
echo sh run.sh  ./canal_client.properties  canal_client.log
}

if [ $# -ne 2 ]; then
    print_usage
    exit 1
fi

CONFIGFILEPATH=$1
LOGFILE=$2

echo java -cp $JARNAME $CLASS $CONFIGFILEPATH
nohup java -cp $JARNAME $CLASS $CONFIGFILEPATH > $LOGFILE &
