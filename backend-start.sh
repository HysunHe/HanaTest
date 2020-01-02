#!/usr/bin/env bash

docker run -d \
    --restart=always \
    --name=ssp \
    -p 8180:8080 \
    -e 'DB_CONN_STR=jdbc:oracle:thin:@db201912241112_medium?TNS_ADMIN=/u01/app/Wallet' \
    -e 'DB_USER=admin' \
    -e 'DB_PASS=Welcome!123456' \
    hysunhe/ssp:latest
