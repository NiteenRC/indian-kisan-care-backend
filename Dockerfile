FROM maven:3.5-jdk-8
FROM java:8
WORKDIR /app
COPY target/*.jar /app/indian-kisan-care-backend.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","indian-kisan-care-backend.jar"]