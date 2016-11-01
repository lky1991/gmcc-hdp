#!/usr/bin/env bash

if [ $# -ne 1 ]; then
    echo "please input file name"
    exit 1
fi

time cat $1 > /dev/null