# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jdk-slim

# Add a volume to store application data
VOLUME /tmp

# Copy the application JAR file into the container
COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar

# Expose the service on port 8082
EXPOSE 8082

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/order-service.jar"]