FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY bi-service/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
