test:
	@ ./mvnw test

package:
	@ ./mvnw clean package -DskipTests

docker-image-build: package
	@ docker build -t product-api-travis .

run: docker-image-build
	@ docker-compose up -d

stop:
	@ docker-compose down -v

deploy: docker-image-build
	@ docker login --username=_ --password=$$DOCKER_REGISTRY_PASS registry.heroku.com
	@ docker image tag product-api-travis:latest registry.heroku.com/product-api-travis/web:1
	@ docker image push registry.heroku.com/product-api-travis/web:1
	@ make deploy_on_heroku IMAGE_ID=$$(docker image inspect registry.heroku.com/product-api-travis/web:1 -f {{.Id}})

deploy_on_heroku:
	@ curl -X PATCH \
			-H "Authorization: Bearer $$DOCKER_REGISTRY_PASS" \
			-H "Content-Type: application/json" \
			-H "Accept:application/vnd.heroku+json; version=3.docker-releases" \
			-d '{ "updates": [{"type": "web",  "docker_image": "$(IMAGE_ID)"}] }' \
			https://api.heroku.com/apps/product-api-travis/formation

