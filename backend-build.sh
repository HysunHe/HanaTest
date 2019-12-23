#!/usr/bin/env bash

TAG=`date '+%Y-%m-%d-%H-%M-%S'`

docker build . -t hysunhe/ssp:${TAG}
docker tag hysunhe/ssp:${TAG}   hysunhe/ssp:latest
