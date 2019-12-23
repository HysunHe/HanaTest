#!/usr/bin/env bash

TAG=`date '+%Y-%m-%d-%H-%M-%S'`

docker build . -t hysunhe/botgateway:${TAG}
docker tag hysunhe/botgateway:${TAG}   hysunhe/botgateway:latest
