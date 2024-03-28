# Step 1: Specify the base image
FROM openjdk:17-jdk-alpine

# Step 2: A volume point to /tmp because it is where spring boot creates working directories for Tomcat by default
VOLUME /server

# Step 3: Copy the jar file into the image
COPY dietideals-server.jar /server/dietideals-server.jar

# Step 4 Expose the port for the database
EXPOSE 5432

# Step 5: Expose the port which the server will listen
EXPOSE 8080

# Step 6: Run the jar file
ENTRYPOINT ["java","-jar","/server/dietideals-server.jar"]