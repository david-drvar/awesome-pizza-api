FROM amazoncorretto:17.0.7-alpine
COPY target/awesome-pizza-api-0.0.1-SNAPSHOT.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]