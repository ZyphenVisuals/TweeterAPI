FROM eclipse-temurin:23-alpine
LABEL authors="ZyphenVisuals"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]