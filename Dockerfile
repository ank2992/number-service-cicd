FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/*.jar

WORKDIR /opt/app

COPY ${JAR_FILE} number-service.jar

ENTRYPOINT ["java","-jar","number-service.jar"]