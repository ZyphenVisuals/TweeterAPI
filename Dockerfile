FROM eclipse-temurin:23-jre-alpine
LABEL authors="ZyphenVisuals"
LABEL org.opencontainers.image.source="https://github.com/ZyphenVisuals/TweeterAPI"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

HEALTHCHECK CMD curl --fail http://localhost:${PORT}/api/health || exit 1

ENTRYPOINT ["java", "-jar", "/app.jar"]