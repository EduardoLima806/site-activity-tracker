FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM java:8
EXPOSE 8080
ADD ./target/gateway-api-0.0.1-SNAPSHOT.jar gateway-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","gateway-api-0.0.1-SNAPSHOT.jar"]