# WeatherUi

## Build & Test

```bash
ng build --configuration=development
ng test
```

## Local development

The serve command defaults to a development build.

```bash
ng build --configuration=development
ng serve
```

## Nginx in production

The Weather-Ui container mounts a nginx.conf in production. Without it, a http 5XX error is shown. Locally this could not be reproduced.
I assume the proxy routing in production might cause this.

The build command defaults to a production build.

```bash
ng build
ng serve --configuration=production
```

## Linter and Prettier

```
ng lint
npx prettier -w .
```

## Updating dependencies

```bash
npm install -g npm-check-updates
ncu

# granular updates
ncu -u --target=patch
ncu -u --target=minor

ncu -u
npm update --save
```
