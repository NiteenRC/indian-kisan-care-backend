FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} indian-kisan-care-backend.jar
ENTRYPOINT ["java","-jar","/indian-kisan-care-backend.jar"]