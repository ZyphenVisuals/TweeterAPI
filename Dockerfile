FROM eclipse-temurin:23-jre-alpine
LABEL authors="ZyphenVisuals"
LABEL org.opencontainers.image.source="https://github.com/ZyphenVisuals/TweeterAPI"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]