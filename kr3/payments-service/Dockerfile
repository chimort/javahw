FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build
COPY . .

RUN mvn install -f common-events/pom.xml -DskipTests
RUN mvn clean package -f payments-service/pom.xml -DskipTests
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /build/payments-service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
