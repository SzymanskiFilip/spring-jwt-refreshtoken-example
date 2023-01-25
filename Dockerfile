FROM openjdk:17

WORKDIR /app

COPY pom.xml /app
COPY .mvn /app/.mvn
COPY mvnw /app

RUN ./mvnw dependency:go-offline

COPY src /app/src

CMD ["./mvnw", "spring-boot:run"]