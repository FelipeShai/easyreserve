# Use the official Gradle image with JDK 21 to build the application
FROM gradle:8.4-jdk21 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the project into the container
COPY . .

# Build the application artifact
RUN gradle build -x test

# Use a minimal Eclipse Temurin JDK 21 image to run the application
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the JAR generated in the build stage
COPY --from=builder /app/build/libs/easyreserve-0.0.1-SNAPSHOT.jar /app/easyreserve.jar

# Expose port 8080
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "easyreserve.jar"]

LABEL authors="iagoomes"