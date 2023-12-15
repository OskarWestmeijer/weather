FROM eclipse-temurin:17.0.9_9-jre-alpine

COPY app.jar /app.jar
ENV JAVA_OPTS="-Xms750m -Xmx750m"

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]