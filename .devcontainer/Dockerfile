# Etapa de compilación con Java 21 y Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN chmod +x ./mvnw && ./mvnw -DskipTests clean package

# Etapa de ejecución: solo JDK (más liviano)
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
