FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace/app

COPY pom.xml ./
COPY src ./src
RUN mvn -B -DskipTests clean package

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=build /workspace/app/target/cctv-1.0.0.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
