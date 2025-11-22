# Multi-stage Dockerfile adaptado a Proyecto_SERVICIUDAD (Maven + Java 17)
# Stage 1: build con Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copia pom first to leverage layer cache for dependencies
COPY pom.xml .

# Pre-descargar dependencias para acelerar builds posteriores
RUN mvn -B -DskipTests dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Compilamos y empaquetamos (ajusta opciones si quieres ejecutar tests en CI)
RUN mvn -B -DskipTests clean package

# Stage 2: runtime mínimo con JRE
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Usuario no-root
RUN useradd -ms /bin/bash appuser

# Copiamos el JAR construido (usa wildcard para no depender del nombre exacto)
COPY --from=build /app/target/*.jar app.jar
RUN chown appuser:appuser /app/app.jar

USER appuser

# Puerto por defecto (ajusta si tu aplicación usa otro)
EXPOSE 8080



ENTRYPOINT ["java","-jar","/app/app.jar"]
