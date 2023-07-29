# Weather-Api

![main branch](https://github.com/OskarWestmeijer/weather-api/actions/workflows/main-build-test-release-deploy.yml/badge.svg)

## Introduction

This Api provides Weather data for predefined locations. The data is harvested every minute
from [https://openweathermap.org/](https://openweathermap.org/) and stored in a database.

It serves as a personal hobby project. The goal is to investigate and practice Api-design with Spring-Boot.
The OpenApi specification is documented with Redoc. [https://api.oskar-westmeijer.com](https://api.oskar-westmeijer.com)

In addition, a ReactJs frontend is available. It displays the weather data in charts over
time. [https://ui.oskar-westmeijer.com](https://ui.oskar-westmeijer.com)

This project has no commercial intentions and is free to access for everyone.

### Technologies used

This repository contains the frontend and backend service. In production the backend connects to the hosts PostgreSQL database.
Proxy routing to these services is handled by another private proxy service.

```
- Java, Maven & Spring-Boot
- Spring Data JPA & Liquibase 
- Lombok, Mapstruct & OpenApi Generator 
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

## OpenApi Generator

The Controller interfaces and related response models are generated at compile phase. The Api specification files are located in this
directory `src/main/resources/openapi`. This project
uses Redoc for Api documentation purposes, after startup reachable on these urls.

- http://localhost:8080
- https://api.oskar-westmeijer.com

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