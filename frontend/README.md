# weather-ui

Frontend for the weather-api.

## Technologies

```
- Sveltekit (CSR) & Vite
- Vitest & Playwright
- Tailwind & DaisyUi
- Nginx
```

## Build & test

### Vitest unit tests

```bash
npm install
npm run test:unit
```

### Playwright e2e tests

E2e tests run against a local [WireMock](https://wiremock.org) instance instead of the real
backend (mappings/fixtures in `wiremock/`), so they need Docker/Podman available. Playwright's
config (`playwright.config.ts`) starts WireMock via `docker compose up` and builds/serves the app
automatically — no manual setup needed beyond having Docker running.

I develop on Linux Fedora, which does not natively support playwright. Use distrobox.

#### Prerequisites

```bash
sudo dnf install distrobox
mkdir ~/distrobox
distrobox create \
--name ubuntu --image ubuntu:24.04 \
--home ~/distrobox/ubuntu \
--additional-packages "git vim nodejs npm"
```

#### Test execution

```bash
distrobox enter ubuntu
npx playwright install --with-deps
npm run test:e2e
distrobox stop ubuntu
```

If running inside distrobox, the container also needs access to the host's Docker/Podman socket
for the WireMock `webServer` step to be able to start it.

### Type checking & linting

```bash
npm run check    # svelte-check
npm run lint      # prettier --check + eslint
npm run format     # prettier --write
```

## Local development

```bash
npm install
npm run dev
```

## Update dependencies

Use ncu to update the dependencies. `npm install -g npm-check-updates`

```bash
# list possible updates
ncu

# granular updates
ncu -u --target=patch
ncu -u --target=minor

# run major updates
ncu -u
npm install
```
