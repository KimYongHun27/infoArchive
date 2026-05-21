# 1. 빌드 스테이지
FROM gradle:7.6-jdk17 AS build
COPY --chown=gradle:gradle . /home/src
WORKDIR /home/src
RUN ./gradlew build -x test --no-daemon

# 2. 실행 스테이지
FROM openjdk:17-jdk-alpine
EXPOSE 8080
COPY build/libs/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]