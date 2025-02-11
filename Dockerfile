FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy source code
COPY . .

# Grant execute permissions to mvnw
RUN chmod +x mvnw

# Build the JAR inside the container
RUN ./mvnw clean package -DskipTests

# Rename and move the JAR properly
RUN mv target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080
