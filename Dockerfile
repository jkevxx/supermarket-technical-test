FROM eclipse-temurin:21-jdk-ubi10-minimal

WORKDIR /app

COPY ./pom.xml /app
COPY ./.mvn /app/.mvn
COPY ./mvnw /app

# donwload dependencies inside the image
RUN ./mvnw dependency:go-offline

COPY ./src /app/src

RUN ./mvnw clean install -DskipTest

EXPOSE 8080

# EntryPoint only execute when a container is created
ENTRYPOINT ["java", "-jar", "/app/target/supermarket-technical-test-0.0.1-SNAPSHOT.jar"]