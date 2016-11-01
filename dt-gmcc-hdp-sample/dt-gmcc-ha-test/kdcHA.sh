#!/usr/bin/env bash

if [ $# -ne 2 ]; then
    echo "please input keytab path and principal"
    exit 1
fi

/etc/rc.d/init.d/krb5kdc stop
kdestory
kinit -kt $1 $2
hadoop fs -ls /