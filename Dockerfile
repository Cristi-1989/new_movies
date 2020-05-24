FROM openjdk:8-jdk

# Create a working directory
RUN mkdir -p /apphome

COPY build/libs/demo-0.0.1-SNAPSHOT.jar /apphome

WORKDIR  /apphome

# Expose port 8080
EXPOSE 8080

CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]