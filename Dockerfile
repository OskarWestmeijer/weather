FROM eclipse-temurin:25.0.1_8-jre-alpine-3.22

COPY target/app.jar /app.jar
ENV JAVA_OPTS="-Xms750m -Xmx750m"

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]