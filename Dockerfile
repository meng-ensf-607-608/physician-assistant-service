FROM maven:3.9.9-eclipse-temurin-23-alpine AS build
WORKDIR /physician-assistant-service
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM eclipse-temurin:23-jre-alpine
COPY --from=build /physician-assistant-service/target/*.jar physician-assistant-service.jar
ENTRYPOINT ["java", "-jar", "physician-assistant-service.jar"]
