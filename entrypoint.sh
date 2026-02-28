#!/bin/sh
echo "[INFO] Starting Weather Backend..."
exec -a weather-backend java ${JAVA_OPTS} -jar /app.jar