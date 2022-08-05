FROM eclipse-temurin:17.0.3_7-jdk

COPY target/app.jar /app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar","/app.jar"]