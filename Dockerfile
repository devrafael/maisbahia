FROM maven:3.8.8-amazoncorretto-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -X -DskipTests

FROM openjdk:17-ea-10-jdk-slim
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/target/*.jar ./maisbahia.jar



ENTRYPOINT [ "java", "-jar", "maisbahia.jar" ]
