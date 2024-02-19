FROM eclipse-temurin:17-jdk-alpine
EXPOSE 81
VOLUME /tmp
COPY bi-service/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
