
#izgradnja
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY backend/ backend/
COPY frontend/frontend/ backend/src/main/resources/static/
RUN cd backend && mvn -q -DskipTests package

#pokretanje
FROM eclipse-temurin:21-jre
COPY --from=build /app/backend/target/*.jar app.jar
CMD ["java", "-jar", "/app.jar"]