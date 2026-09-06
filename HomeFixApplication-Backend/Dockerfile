# Step 1: Use Maven to build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml .
COPY src ./src

# Build the application and skip tests to save time during deployment
RUN mvn clean package -DskipTests

# Step 2: Use a lightweight Java runtime to actually run the app
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the compiled .jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Tell the world that our app runs on port 8080
EXPOSE 8080

# The command to start the application
ENTRYPOINT ["java", "-jar", "app.jar"]