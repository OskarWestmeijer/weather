# build-stage environment
FROM node:lts-alpine3.16 as build-stage
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json ./
COPY package-lock.json ./
RUN npm install
COPY . ./
RUN npm run build

# production-stage environment
FROM nginx:1.23.1-alpine
ENV NODE_ENV=production
COPY --from=build-stage /app/build /usr/share/nginx/html/weather
COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf
CMD ["nginx", "-g", "daemon off;"]