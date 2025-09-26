# build-stage
FROM node:22.19.0-alpine3.22 as build-stage

WORKDIR /usr/local/app
COPY ./ ./
RUN npm install
RUN npm run build

# production-stage
FROM nginx:1.29.1-alpine3.22-slim as production-stage

COPY --from=build-stage /usr/local/app/dist/weather-ui/browser /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]