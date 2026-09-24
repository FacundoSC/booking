FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy root Maven files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Resolve dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy project sources
COPY . .

# Build
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENV PORT=8083
EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]