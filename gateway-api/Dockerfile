FROM java:8
EXPOSE 8080
RUN mvn install
ADD ./target/gateway-api-0.0.1-SNAPSHOT.jar gateway-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","gateway-api-0.0.1-SNAPSHOT.jar"]