#!/bin/sh

echo "Begin..."

mvn clean package && java -jar target/bot-gateway-springboot.jar

echo "Done\n"
