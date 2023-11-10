# WeatherUi

## Linter and Prettier

```
ng lint
npx prettier -w .
```

## Development server

```
ng test
ng serve
# goto http://localhost:4200/
```

## Nginx in production

The Weather-Ui container mounts a nginx.conf in production. Without it, a http 5XX error is shown. Locally this could not be reproduced.
I assume the proxy routing in production might cause this.

## Updating dependencies

```
npm install -g npm-check-updates
ncu
ncu -u
npm update --save
```
