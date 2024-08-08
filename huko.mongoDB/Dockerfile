# Use an official Maven image to build the application
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
COPY --from=build /app/target/huko.mongoDB-0.0.1-SNAPSHOT.jar app.jar

# Expose the new port (e.g., 5002)
EXPOSE 5002

# Run the application on the new port
CMD ["java", "-jar", "app.jar", "--server.port=5002"]
