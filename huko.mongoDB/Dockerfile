# Use an official Java runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Package the application
RUN ./mvnw package

# Expose the new port (e.g., 5002)
EXPOSE 5002

# Run the application on the new port
CMD ["java", "-jar", "target/huko.mongoDB-0.0.1-SNAPSHOT.jar", "--server.port=5002"]
