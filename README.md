# weather-api

This app requests the OpenWeatherApi every minute for current weather data. It only fetches information for my home town
Luebeck (Germany).

The setup contains a postgres database, where the data is stored after each request. Everything is dockerized. I am
using the free plan from OpenWeatherApi, only 60 possible requests per hour.

I display this data in a ReactJs UI. Furthermore the API is directly accessable.

### technologies used

```
- Java
- Maven
- Spring-Boot
- Hibernate
- ReactJS
- Docker and docker-compose
- PostgreSQL
- Testcontainers & Wiremock
- OpenWeatherApi (Api access)
- Github Actions for CI
```

### c4-model

#### system context diagram

![Alt c4-model system context diagram](frontend/public/images/c4_context.svg)

#### container diagram

![Alt c4-model container diagram](frontend/public/images/c4_container.svg)

### build & test

This projects uses testcontainers with pre initialized data.

```
./mvnw clean verify
```

### how-to run on local machine

Docker compose initializes the database on startup.

```
docker-compose -f cdev.yml up -d
./mvnw spring-boot:run
```

### release image

Build a new target folder. Publish image to Dockerhub. Remember to increase the tag version in the compose-prod.yml

```
./mvnw clean package
docker build -t oskarwestmeijer/weather-api:1.4.2 .
docker push oskarwestmeijer/weather-api:1.4.2
```

### deployment

```
ssh on Server

pull https://github.com/OskarWestmeijer/weather-api

docker-compose -f cprod.yml down
docker-compose -f cprod.yml up -d
```

#### .env file

The application folder on the server contains a .env file. This contains api-keys and database credentials.