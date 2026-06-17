# build-stage
FROM node:24.12.0-alpine3.23 as build-stage

WORKDIR /usr/local/app
COPY ./ ./
RUN npm install
RUN npm run build

# production-stage
FROM nginx:1.31.1-alpine-slim as production-stage

COPY --from=build-stage /usr/local/app/build /usr/share/nginx/html
COPY nginx/nginx.conf /etc/nginx/nginx.conf
CMD ["nginx", "-g", "daemon off;"]