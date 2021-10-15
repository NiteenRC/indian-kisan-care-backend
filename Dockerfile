#FROM openjdk:8-jdk-alpine
FROM adoptopenjdk/openjdk11:jre-11.0.9.1_1-alpine@sha256:b6ab039066382d39cfc843914ef1fc624aa60e2a16ede433509ccadd6d995b1f
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} smart-accounting-book-backend.jar
ENTRYPOINT ["java","-jar","/smart-accounting-book-backend.jar"]