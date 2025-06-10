FROM maven:3.9.4-eclipse-temurin-17 AS builder

WORKDIR /build

COPY ./common-events /build

RUN mvn clean install -DskipTests
