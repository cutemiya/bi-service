FROM eclipse-temurin:17-jdk-alpine
EXPOSE 80
VOLUME /tmp
COPY bi-service/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
