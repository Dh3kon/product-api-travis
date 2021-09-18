package:
	@ mvn clean package -DskipTests

docker-image-build: package
	@ docker build -t product-api-travis .

run: docker-image-build
	@ docker-compose up -d

stop:
	@ docker-compose down -v
