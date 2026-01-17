FROM gradle:8.14.0-jdk21 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle

COPY src ./src

RUN gradle buildd -x test

FROM amazoncorretto:21
WORKDIR /apps

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]