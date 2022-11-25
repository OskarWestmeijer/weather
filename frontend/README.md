# Weather-Ui

This ReactJS deployment depends on the Weather-Api backend service. It displays the weather data in graphs. Furthermore,
it offers pages for CSV export and documentation.

## Local development

I need to look up the command on how-to run the ReactJs frontend. It will connect to the production backend.

## Deployment

The deployment is managed in the root of the project. The required docker-compose.yml is located here. Update the
Weather-Ui tag to the new version. Please refer to the Backend Readme for the actual deployment steps.

### Release image

```
npm run build

docker build -t oskarwestmeijer/weather-ui:{new_version} .
docker push oskarwestmeijer/weather-ui:{new_version}
```