#!/usr/bin/env bash

if [ $# -ne 2 ]; then
    echo "please input file size and table name"
    exit 1
fi

PRINCIPLE = hdp@DATATUB.COM
KEYTAB = /etc/security/hdfs.keytab
CONFIGPATH = /home/hdpop/wally/hdp.properties
DATAPATH = /tmp/2G_user_location_data.csv

sh runkrb.sh -service TestFile -size $1 -krb true -princ $PRINCIPLE -key $KEYTAB -config $CONFIGPATH

#HQL
hive<<EOF
create table if not exists $2 \( LA_CODE String,BSCAD00 String,PROVINCE_NAME_CH String,
CITY_NAME_CH String,TOWN_NAME_CH String,CELL_NAME_CN String\) row format delimited  fields terminated by ',' STORED AS TEXTFILE;

LOAD DATA LOCAL INPATH $DATAPATH  OVERWRITE INTO TABLE  $2;

select min(la_code),max(bscad00),count(*) as total from $2 group by city_name_ch order by total;
EOF


