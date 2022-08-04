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
- Spring Boot
- Hibernate
- Docker and docker-compose
- PostgreSQL
- Testcontainers
- OpenWeatherApi (Api access)
- Github Actions for CI
```

### run all tests

This projects uses testcontainers with pre initialized data.

```
start docker daemon

./mvnw clean verify
```

### how-to run on local machine

Docker compose initializes the database on startup.

```
start docker daemon

docker-compose down --volumes

docker-compose up

./mvnw spring-boot:run
```

### access database container

```
docker exec -it <db> sh

psql -U username1
\l (list databases)
\c carrental
\d (list tables)
```

### private link for further documentation

https://www.notion.so/Openweather-API-0c064fb6e37144c38cd1cca9b6ade21d

Inject database files this way.
cat ./2_schema.sql | docker exec -i database psql -U username1