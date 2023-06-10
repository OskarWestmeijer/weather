# Weather-Api

![Main release worklflow](https://github.com/OskarWestmeijer/weather-api/actions/workflows/build-test-release-deploy.yml/badge.svg?branch=main)

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
- Hibernate, Lombok, Testcontainers & Wiremock
- Prometheus & PostgreSQL
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
docker-compose -f cdev.yml up -d
./mvnw spring-boot:run 
// starts spring boot in dev profile, see pom.xml
```

## Deployment

### Release image

The image will be released by Github actions. Every push (merge) to main will trigger a build & release.

### Deploy new image

Follow these steps. Information on how to access the production server is on private Notion pages.

```
# ssh on Server
# pull https://github.com/OskarWestmeijer/weather-api

docker-compose -f cprod.yml down
docker-compose -f cprod.yml up -d
```

## Credentials storage

The application needs credentials to access the Database and OpenWeatherApi. These properties are referenced in the
application.yml.

For tests and local development the app uses the 'dev' profile. This will use simple placeholders. No further action
required.

In production the app uses the 'prod' profile. This requires the actual values. The values are stored in an .env file,
which is located in the root of the project folder.

During startup the service will reference these through the docker-compose production template. If
changes are required, these need to be added manually to the .env file.

## c4-model

A couple c4-model diagrams describe the architecture.
Furthermore, the deployed Weather-Api frontend(https://oskar-westmeijer.com/weather/documentation) hosts a documentation
section with more details.

### System context diagram

![Alt c4-model system context diagram](frontend/public/images/c4_context.svg)

### Container diagram

![Alt c4-model container diagram](frontend/public/images/c4_container.svg)