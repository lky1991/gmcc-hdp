#!/usr/bin/env bash

if [ $# -ne 1 ]; then
    echo "please input client number"
    exit 1
fi

hbase pe sequentialRead  $1