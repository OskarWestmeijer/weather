# build-stage environment
# Image has NODE_VERSION=18.13.0
FROM node:lts-alpine3.16 as build-stage
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY frontend/package.json ./
COPY frontend/package-lock.json ./
RUN npm install
COPY frontend/. ./
RUN npm run build

# production-stage environment
FROM nginx:1.25.1-alpine3.17-slim
ENV NODE_ENV=production
COPY --from=build-stage /app/build /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]