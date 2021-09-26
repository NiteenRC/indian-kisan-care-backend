FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} smart-accounting-book-backend.jar
ENTRYPOINT ["java","-jar","/smart-accounting-book-backend.jar"]