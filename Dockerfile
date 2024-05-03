FROM openjdk:17
#FROM openjdk:21

## Info about maintainers
LABEL maintainer="Ali Artukov"

## Run java application
COPY target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
EXPOSE 8080