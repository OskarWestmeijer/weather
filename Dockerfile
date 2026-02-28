FROM eclipse-temurin:25.0.1_8-jre-alpine-3.22

LABEL maintainer="Oskar Westmeijer"

# Install bash
RUN apk add --no-cache bash

# Create non-root user
RUN addgroup -S app && adduser -S app -G app

USER app

WORKDIR /app
COPY target/app.jar .

ENV JAVA_OPTS="-Xms750m -Xmx750m"

# Add entrypoint script for custom process name
COPY --chmod=755 entrypoint.sh /entrypoint.sh

ENTRYPOINT ["/bin/bash", "/entrypoint.sh"]