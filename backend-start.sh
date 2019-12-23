#!/usr/bin/env bash

docker run -d \
    --restart=always \
    --name=gateway \
    -p 8080:8080 \
    -e 'DB_CONN_STR=jdbc:oracle:thin:@//132.145.xxx.xxx:1521/BotDevDB_iad1hb.sub01110857210.devopsvcn.oraclevcn.com' \
    -e 'DB_USER=c##botadmin' \
    -e 'DB_PASS=BotWelcome123$$' \
    hysunhe/botgateway:latest
