FROM openjdk:11

WORKDIR /app
COPY ./build/libs/simple-interest-microservice-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "simple-interest-microservice-0.0.1-SNAPSHOT.jar"]