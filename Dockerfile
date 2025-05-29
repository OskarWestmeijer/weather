FROM eclipse-temurin:21.0.7_6-jre-alpine-3.21

COPY target/app.jar /app.jar
ENV JAVA_OPTS="-Xms750m -Xmx750m"

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]