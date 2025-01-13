FROM eclipse-temurin:23-jre-alpine
LABEL authors="ZyphenVisuals"
LABEL org.opencontainers.image.source="https://github.com/ZyphenVisuals/TweeterAPI"

RUN apk --no-cache add curl
HEALTHCHECK CMD curl --fail http://localhost:${PORT}/api/health || exit 1

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]