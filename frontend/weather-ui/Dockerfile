# build-stage
FROM node:20.9.0-alpine3.18 as build-stage

WORKDIR /usr/local/app
COPY ./frontend/weather-ui ./
RUN npm install
RUN npm run build

# production-stage
FROM nginx:1.25.3-alpine3.18-slim as production-stage

COPY --from=build-stage /usr/local/app/dist/weather-ui /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]