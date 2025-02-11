FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy project source code
COPY . .

# Build the JAR file inside the container
RUN ./mvnw clean package -DskipTests

# Copy the built JAR file
COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080
