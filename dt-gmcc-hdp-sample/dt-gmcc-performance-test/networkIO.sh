#!/usr/bin/env bash

if [ $# -ne 3 ]; then
    echo "please input host ip and source file name and target file name"
    exit 1
fi

nohup wget http://$1/$2 -O $3 &