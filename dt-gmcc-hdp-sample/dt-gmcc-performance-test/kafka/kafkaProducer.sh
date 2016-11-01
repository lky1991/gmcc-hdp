#!/usr/bin/env bash

if [ $# -ne 3 ]; then
    echo "please input topic name and messages num and producer numbers"
    exit 1
fi

ZOOKEEPER = vm2.datatub.com:2181,vm1.datatub.com:2181,vm3.datatub.com:2181
BROKER = vm3.datatub.com:6667

cd /usr/hdp/current/kafka-broker/bin
./kafka-topics.sh --create --zookeeper $ZOOKEEPER --replication-factor 1  --topic $1
./kafka-producer-perf-test.sh --messages $2 --message-size 100  --batch-size 100 --topics $1 --threads $3 --broker-list $BROKER --security-protocol PLAINTEXTSASL