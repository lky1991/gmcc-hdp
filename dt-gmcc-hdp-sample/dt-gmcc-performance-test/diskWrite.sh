#!/usr/bin/env bash

if [ $# -ne 2 ]; then
 echo "please input file name and file size"
 exit 1
fi

dd if=/dev/zero of=/tmp/$1 bs=1G count=$2 conv=fdatasync