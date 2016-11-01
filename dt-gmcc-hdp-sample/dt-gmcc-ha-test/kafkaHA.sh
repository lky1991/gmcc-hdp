#!/usr/bin/env bash

if [ $# -ne 2 ]; then
    echo "please input host name and topic name"
    exit 1
fi


ssh $1
su kafka
kill -9 `jps |grep Broker|awk -F ' ' '{print $1}'`
ZOOKEEPER = vm2.datatub.com:2181,vm1.datatub.com:2181,vm3.datatub.com:2181

cd /usr/hdp/current/kafka-broker/bin
./kafka-topics.sh --create --zookeeper $ZOOKEEPER --replication-factor 1  --topic $2
