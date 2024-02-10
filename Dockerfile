FROM eclipse-temurin:21.0.2_13-jre-alpine

COPY target/app.jar /app.jar
ENV JAVA_OPTS="-Xms750m -Xmx750m"

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]