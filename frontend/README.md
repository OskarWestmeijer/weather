# Weather-UI

This ReactJS deployment depends on the Weather-Api backend service. It displays the weather data in charts. Furthermore,
it offers pages for CSV export and documentation.

## Local development

The service is configured to connect to the production backend API.
Note! This needs to be changed. It is only tolerable since only the HTTP GET method is used on calls to the backend.

```
// nodejs 18.10.0 required. see package.json
npm install

// execute tests
npm test -- --coverage

// start frontend
npm run start
```

## Deployment

Please refer to the Backend Readme for the actual deployment steps.

## Image release

The image will be released by Github actions. The path structure in the Dockerfile therefore is set to the project root.