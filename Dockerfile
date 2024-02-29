FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/cathyBank-0.0.1-SNAPSHOT.jar demo.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","demo.jar"]
