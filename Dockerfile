
#izgradnja frontenda (React + Vite)
FROM node:20 AS frontend
WORKDIR /fe
COPY frontend/frontend/package*.json ./
RUN npm ci
COPY frontend/frontend/ ./
RUN npm run build

#izgradnja backenda
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY backend/ backend/
COPY --from=frontend /fe/dist/ backend/src/main/resources/static/
RUN cd backend && mvn -q -DskipTests package

#pokretanje
FROM eclipse-temurin:21-jre
COPY --from=build /app/backend/target/*.jar app.jar
CMD ["java", "-jar", "/app.jar"]