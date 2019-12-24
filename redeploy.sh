echo "Compliling package..."
mvn clean package
sleep 1

echo "Stopping ssp..."
docker stop ssp
sleep 1

echo "Removing ssp..."
docker rm ssp
sleep 1

echo "Building Docker image..."
./backend-build.sh
sleep 1

echo "Starging ssp..."
./backend-start.sh
sleep 1

echo "Done"