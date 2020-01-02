#!/usr/bin/env bash

echo "Deleting old code..."
rm -rf /home/oracle/hysun-workspace/HanaTest

sleep 1

echo "pull latest code"
cd /home/oracle/hysun-workspace
git clone https://github.com/HysunHe/HanaTest.git

sleep 1

echo "Producing new image..."
cd /home/oracle/hysun-workspace/HanaTest
chmod +x *.sh

./redeploy.sh

cd