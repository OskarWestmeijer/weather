# Weather-Api

![main branch](https://github.com/OskarWestmeijer/weather-api/actions/workflows/main-build-test-release-deploy.yml/badge.svg)

This application provides weather information for predefined locations. This repository contains the frontend and
backend service. In production the backend connects to the native PostgreSQL database.
The proxy routing to this service is handled by another private infrastructure repository.

This service requests the public OpenWeatherApi once a minute for the current weather data. The response data is stored
in the database. The frontend and backend Api can be reached via the internet in Https.

The weather data is displayed on charts in the ReactJs frontend.

This Readme holds extensive information on the backend service. Please note that there is also
a [frontend Readme](frontend/README.md).

Link to production UI. [https://oskar-westmeijer.com/weather](https://oskar-westmeijer.com/weather)

## Technologies used

```
- Java, Maven & Spring-Boot
- Spring Data JPA, Liquibase, Lombok & Mapstruct 
- PostgreSQL, Testcontainers, Wiremock & Prometheus
- Nginx & ReactJS
- Github Actions, Docker and Docker-Compose
```

## Local development

### Build & test

This projects uses testcontainers. It will automatically inject the required resources to the started containers.

```
./mvnw clean verify
```

### Run application

Docker-compose initializes the dependencies on startup. There will be no connections to actual production systems.
Wiremock mocks the OpenWeatherApi requests. Use the correct docker-compose template!

```
docker-compose up -d
./mvnw spring-boot:run 
```

## Deployment

### Release image

The image will be released by Github actions. Every push (merge) to main will trigger a build & release.

## Documentation

A couple c4-model diagrams describe the architecture.
Furthermore, the deployed Weather-Api frontend(https://oskar-westmeijer.com/weather/documentation) hosts a documentation
section with more details.

### c4-model System context diagram

![Alt c4-model system context diagram](frontend/public/images/c4_context.svg)

### c4-model Container diagram

![Alt c4-model container diagram](frontend/public/images/c4_container.svg)