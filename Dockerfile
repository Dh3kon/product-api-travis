FROM openjdk:11-jre-slim
WORKDIR /products
COPY target/*.jar /products/app.jar
EXPOSE 8080
CMD java -XX:+UseContainerSupport -Xmx512m -jar app.jar