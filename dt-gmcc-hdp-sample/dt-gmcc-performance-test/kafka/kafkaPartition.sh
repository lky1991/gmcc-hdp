#!/usr/bin/env bash


if [ $# -ne 2 ]; then
    echo "please input topic name and partition num"
    exit 1
fi

ZOOKEEPER = vm2.datatub.com:2181,vm1.datatub.com:2181,vm3.datatub.com:2181
BROKER = vm3.datatub.com:6667
cd /usr/hdp/current/kafka-broker/bin

./kafka-topics.sh --create --zookeeper $ZOOKEEPER --replication-factor 1 --partition $2 --topic $1
./kafka-producer-perf-test.sh --messages 1000000 --message-size  100  --batch-size 100 --topics $1 --threads 1 --broker-list $BROKER  --security-protocol PLAINTEXTSASL