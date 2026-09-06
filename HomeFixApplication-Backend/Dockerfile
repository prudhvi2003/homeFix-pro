# Step 1: Use Maven with Eclipse Temurin to build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Step 2: Use a supported, lightweight Java runtime to run the app
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the compiled .jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Command to start the app
ENTRYPOINT ["java", "-jar", "app.jar"]