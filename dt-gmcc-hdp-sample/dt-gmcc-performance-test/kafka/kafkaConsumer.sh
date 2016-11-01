#!/usr/bin/env bash

if [ $# -ne 2 ]; then
    echo "please input topic name and consumer number"
    exit 1
fi


ZOOKEEPER = vm2.datatub.com:2181,vm1.datatub.com:2181,vm3.datatub.com:2181
BROKER = vm3.datatub.com:6667
cd /usr/hdp/current/kafka-broker/bin
./kafka-consumer-perf-test.sh --zookeeper $ZOOKEEPER --messages 1000000 --topic $1 --threads $2 --broker-list $BROKER --consumer.config client-ssl.properties