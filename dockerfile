
    FROM eclipse-temurin:21-jdk AS build

    WORKDIR /app
    
    COPY . .
    
    RUN chmod +x mvnw
    
    RUN ./mvnw clean package -DskipTests
    
    FROM eclipse-temurin:21-jdk AS runtime
    
    WORKDIR /app
    
    COPY --from=build /app/target/*.jar app.jar
    
    EXPOSE 8081
    
    ENTRYPOINT ["java", "-jar", "app.jar"]
    