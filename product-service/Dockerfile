# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Add a volume to store application data
VOLUME /tmp

# Copy the application JAR file into the container
COPY target/product-service-0.0.1-SNAPSHOT.jar product-service.jar

# Expose the service on port 8081
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/product-service.jar"]