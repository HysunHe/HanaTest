#!/bin/sh

echo "Begin..."

mvn clean package && java -jar target/ssp.jar

echo "Done\n"
