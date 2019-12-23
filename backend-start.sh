#!/usr/bin/env bash

docker run -d \
    --restart=always \
    --name=ssp \
    -p 8080:8080 \
    -e 'DB_CONN_STR=jdbc:oracle:thin:@odaflexadw_medium?TNS_ADMIN=/u01/app/Wallet' \
    -e 'DB_USER=admin' \
    -e 'DB_PASS=BotWelcome123$$' \
    hysunhe/ssp:latest
