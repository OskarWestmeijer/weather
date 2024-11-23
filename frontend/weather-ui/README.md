# WeatherUi

## Build & Test

```bash
npm run build:dev
npm run test
```

## Local development

The serve command defaults to a development build.

```bash
npm run build:dev
npm run start:dev
```

## Nginx in production

The Weather-Ui container mounts a nginx.conf in production. Without it, a http 5XX error is shown. Locally this could not be reproduced.
I assume the proxy routing in production might cause this.

The build command defaults to a production build.

```bash
npm run build
npm run start
```

## Linter and Prettier

```bash
npm run lint
npx prettier -w .
```

## Updating dependencies

Use angular cli `npm install -g @angular/cli@18`. (adjust to required version)

```bash
npm run ng:version
```

Use ncu to check on possible versions. `npm install -g npm-check-updates`


```bash
ncu

# granular updates
ncu -u --target=patch
ncu -u --target=minor

ncu -u
```

## Configuration files overview

```
- angular.json - provides workspace-wide and project-specific configuration defaults
- tsconfig.json - specifies the base TypeScript and Angular compiler options that all projects in the workspace inherit
- tsconfig.app.json - app specific (types are configured), extends tsconfig
- tsconfig.spec.json - app (test) specific (types are configured), extends tsconfig
```
