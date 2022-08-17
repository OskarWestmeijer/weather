# weather-api

This app requests every minute the OpenWeatherApi for current weather data. It only fetches information for my home town
Luebeck (Germany).

The setup contains a database, where the temperatures are stored after each request. Everything is dockerized. I am
using the free plan from OpenWeatherApi, only 60 possible requests per hour.

This app provides an endpoint to request the stored temperatures for Luebeck.

### technologies used

```
- Java
- Maven
- Spring-Boot
- Hibernate
- Docker and docker-compose
- PostgreSQL
- Testcontainers & Wiremock
- OpenWeatherApi (Api access)
- Github Actions for CI
```

### build & test

This projects uses testcontainers with pre initialized data.

```
./mvnw clean verify
```

### how-to run on local machine

Docker compose initializes the database on startup.

```
docker-compose -f compose-dev.yml down
docker-compose -f compose-dev.yml up -d

./mvnw spring-boot:run

```

### release image

Build a new target folder. Publish image to Dockerhub. Remember to increase the tag version in the compose-prod.yml

```
./mvnw clean package
docker login
docker build -t oskarwestmeijer/weather-api:1.1.1 .
docker push oskarwestmeijer/weather-api:1.1.1
```

### deployment

```
ssh on Server

pull https://github.com/OskarWestmeijer/weather-api

docker-compose -f compose-prod.yml down
docker-compose -f compose-prod.yml up -d
```