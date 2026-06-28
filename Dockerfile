# Etapa 1: Compilación
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Consumer 2 escucha en 8083
EXPOSE 8083

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]