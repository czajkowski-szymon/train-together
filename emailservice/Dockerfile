FROM maven:3.9.7-amazoncorretto-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /build/target/emailservice-1.0.0.jar /app/

EXPOSE 8081

CMD java -jar emailservice-1.0.0.jar