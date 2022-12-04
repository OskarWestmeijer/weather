# Weather-Api
![Main release worklflow](https://github.com/OskarWestmeijer/weather-api/actions/workflows/main-release.yml/badge.svg)

This application provides weather information for predefined locations. This repository contains the frontend and
backend service. In production the backend connects to the native PostgreSQL database.
The proxy routing to this service is handled by another private infrastructure repository.

This service requests the public OpenWeatherApi once a minute for the current weather data. The response data is stored
in the database. The frontend and backend Api can be reached via the internet in Https.

The weather data is display in user friendly charts in the ReactJs frontend.

This Readme holds extensive information on the backend service. Please note that there is also a Readme for the frontend.

Link to production UI. [https://oskar-westmeijer.com/weather](https://oskar-westmeijer.com/weather)

## Technologies used

```
- Java
- Maven
- Spring-Boot
- Hibernate
- ReactJS
- Docker and docker-compose
- Testcontainers, PostgreSQL & Wiremock
- OpenWeatherApi (Api access)
```

## Local development

### Build & test

This projects uses testcontainers. It will automatically inject the sql templates from the resources folder during
startup.

```
./mvnw clean verify
```

### Run application

Docker-compose initializes the dependencies on startup. There will be no connections to actual production systems.
Wiremock mocks the OpenWeatherApi requests. Use the correct docker-compose template!

```
docker-compose -f cdev.yml up -d
./mvnw spring-boot:run
```

## Deployment

### Release image

Build a new target folder. Publish the image to Dockerhub. In order to deploy, remember to increase the tag version in
the cprod.yml

```
./mvnw clean package
docker build -t oskarwestmeijer/weather-api:{new_version} .
docker push oskarwestmeijer/weather-api:{new_version}
```

### Deploy new image

Follow these steps. Information on how to access the production server is on private Notion pages.

```
# ssh on Server
# pull https://github.com/OskarWestmeijer/weather-api

docker-compose -f cprod.yml down
docker-compose -f cprod.yml up -d
```

## Credentials storage

The application needs credentials to access the database and OpenWeatherApi. These properties are referenced in the
application.yml.

For tests and local development the app uses the 'dev' profile. This will use simple placeholders. No further action
required.

In production the app uses the 'prod' profile. This requires the actual values. The values are stored in an .env file,
which is located in the root of the project folder.

During startup the service will reference these through the docker-compose production template. If
changes are required, these need to be added manually to the .env file.

## c4-model

The weather-api frontend hosts a documentation section. Please navigate there for more insights.

### System context diagram

![Alt c4-model system context diagram](frontend/public/images/c4_context.svg)

### Container diagram

![Alt c4-model container diagram](frontend/public/images/c4_container.svg)