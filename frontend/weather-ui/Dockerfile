# build-stage
FROM node:22.11.0-alpine3.20 as build-stage

WORKDIR /usr/local/app
COPY ./ ./
RUN npm install
RUN npm run build

# production-stage
FROM nginx:1.26.2-alpine3.20-slim as production-stage

COPY --from=build-stage /usr/local/app/dist/weather-ui/browser /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]