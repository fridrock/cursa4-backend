FROM gradle:8.4.0-jdk17 AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN gradle dependencies || return 0

COPY src ./src

RUN gradle clean build --no-daemon

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar


EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar"]