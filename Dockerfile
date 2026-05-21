# 1. 빌드 스테이지
FROM gradle:8.10-jdk21 AS build

WORKDIR /home/src

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar -x test --no-daemon


# 2. 실행 스테이지
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /home/src/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]