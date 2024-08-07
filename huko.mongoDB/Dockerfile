# Use the official Maven image as the base image
FROM maven:3.8.5-openjdk-17-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and the source code into the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean install

# Use the official OpenJDK image as the base image for the runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/target/*.jar app.jar

# Specify the entry point for the application
ENTRYPOINT ["java", "-jar", "app.jar"]
