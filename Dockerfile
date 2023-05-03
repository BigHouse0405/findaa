FROM openjdk:17-jdk-slim-buster

ENV SPRING_PROFILES_ACTIVE="container"
WORKDIR /app

COPY .gradle/ .gradle
COPY gradlew build.gradle gradle ./
COPY gradle/wrapper ./gradle/wrapper
COPY src ./src

RUN apt-get update -y && apt-get install -y binutils && \
    chmod +x gradlew && \
    ./gradlew wrapper && \
    ./gradlew bootJar --no-daemon

RUN ls -la build/libs
RUN ls -la gradle

CMD ["sh", "-c", "java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} build/libs/app-0.0.1-SNAPSHOT.jar"]
