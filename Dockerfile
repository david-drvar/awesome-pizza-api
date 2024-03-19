## Use OpenJDK 17 as the base image
#FROM amazoncorretto:17.0.7-alpine
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the JAR file into the container at /app
#COPY target/awesome-pizza-api-0.0.1-SNAPSHOT.jar /app/awesome-pizza-api-0.0.1-SNAPSHOT.jar
#
## Expose the port your application runs on
#EXPOSE 8080
#
## Command to run the application
#CMD ["java", "-jar", "awesome-pizza-api-0.0.1-SNAPSHOT.jar"]


FROM amazoncorretto:17.0.7-alpine
COPY target/awesome-pizza-api-0.0.1-SNAPSHOT.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]