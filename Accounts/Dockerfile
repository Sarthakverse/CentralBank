############################################
# STAGE 1 — BUILD (Maven + JDK)
############################################
FROM eclipse-temurin:17-jdk-alpine AS build

# Install required tools
RUN apk add --no-cache bash

# Set work directory
WORKDIR /build

# Copy pom and download dependencies first (Docker cache optimization)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw -B dependency:go-offline

# Copy source code
COPY src src

# Build the application (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests

############################################
# STAGE 2 — RUNTIME (JRE only)
############################################
FROM eclipse-temurin:17-jre-alpine

# Create non-root user (SECURITY BEST PRACTICE)
RUN addgroup -S spring && adduser -S spring -G spring

# App directory
WORKDIR /app

# Copy jar from build stage
COPY --from=build /build/target/*jar app.jar

# Change ownership
RUN chown -R spring:spring /app

# Switch to non-root user
USER spring

# Expose application port
EXPOSE 8080

# JVM tuning for containers
ENV JAVA_OPTS="\
-XX:+UseContainerSupport \
-XX:MaxRAMPercentage=75 \
-XX:+UseG1GC \
-Djava.security.egd=file:/dev/./urandom"

# Run Spring Boot
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
