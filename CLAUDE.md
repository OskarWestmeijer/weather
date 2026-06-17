# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

Weather data API + frontend. A Spring Boot backend harvests current weather from
`openweathermap.org` for a fixed set of locations, stores it in PostgreSQL, and exposes it via a
REST API documented with OpenAPI. `frontend/` is a SvelteKit app (the previous Angular UI has been
fully removed — there is only one frontend now).

## Backend (Spring Boot / Java 25)

### Build & test

```bash
# requires Docker running (Testcontainers spins up Postgres for ITs)
./mvnw clean verify
```

- Unit tests: `*Test.java`, run by surefire as part of the normal `test` phase.
- Integration tests: `*IT.java`, run by failsafe's `integration-test`/`verify` goals (only
  triggered by `verify`, not `test`).
- Run a single test: `./mvnw test -Dtest=ClassName#methodName`
- Run a single IT: `./mvnw verify -Dit.test=ClassName#methodName`
- Checkstyle (Google checks) runs in the `validate` phase and fails the build on violation —
  it's not optional/advisory.
- Coverage report: `./mvnw clean verify` then open `target/site/jacoco/index.html`.
- Surefire report: `./mvnw clean site` then open `target/site/surefire-report.html`.

### Run locally

```bash
docker compose up -d   # Postgres + Wiremock (mocks openweathermap.org)
./mvnw spring-boot:run  # active profile: local (see application-local.yml)
```

The `local` profile points the OpenWeather client at the Wiremock container
(`localhost:9000/data/2.5`), so there's no dependency on the real external API or production
systems when developing locally.

### OpenAPI code generation

Server interfaces, client, and model classes are **generated at build time**, not hand-written —
don't look for or hand-edit generated controllers/clients:

- `src/main/resources/public/weather-api.yml` → generates the server-side `*Api` interfaces
  (`westmeijer.oskar.weatherapi.openapi.server.*`) that controllers implement.
- `src/main/resources/openapi/openweatherapi.yml` → generates the outbound client
  (`westmeijer.oskar.weatherapi.openapi.client.*`) used to call openweathermap.org.

To change a REST contract, edit the relevant `.yml` spec file, not the generated Java. Generated
code is excluded from Jacoco coverage.

### Architecture: hexagonal (ports & adapters)

Code is split into `domain`, `application`, `infrastructure`, enforced by ArchUnit tests
(`src/test/java/.../archunit/ArchUnitHexagonalTest.java`):

- `domain` — model classes (`Location`, `Weather`, `Overview`, `WeatherFeedPage`) and domain
  exceptions. Must not depend on `application` or `infrastructure`.
- `application.ports.{inbound,outbound}` — interfaces only (enforced by ArchUnit). Inbound ports
  are use cases (`GetWeatherUseCase`, `GetLocationUseCase`, `GetOverviewUseCase`,
  `ImportWeatherUseCase`); outbound ports are dependencies the application needs
  (`WeatherRepository`, `LocationRepository`, `ImportWeatherClient`).
- `application.services` — use-case implementations (`WeatherService`, `LocationService`,
  `OverviewService`, `WeatherImportJob`). May depend only on `domain` — never on
  `infrastructure`.
- `infrastructure.adapters.inbound.rest` — controllers implementing the generated `*Api`
  interfaces, plus DTO mappers (MapStruct) translating between domain models and
  generated request/response DTOs.
- `infrastructure.adapters.outbound.persistence` — JPA repositories/entities plus mappers
  between entities and domain models.
- `infrastructure.adapters.outbound.restclient` — wraps the generated OpenWeatherApi client.
- Infrastructure may call into `application.ports.*` but is forbidden from reaching into
  `application.services.*` directly (forces it through the use-case interfaces).

When adding a new use case: define the port interface first, implement it as a `@Service` in
`application.services`, then wire a controller/adapter in `infrastructure` against the interface
— not the implementation.

### Weather import job

`WeatherImportJob` (`application/services`) is a `@Scheduled(fixedDelay = 60000)` job that pulls
the next location due for refresh (`LocationService.getNextImportLocation()`), fetches latest
weather via `ImportWeatherClient`, and saves it. Scheduling is wired through `SchedulingConfig`
and gated by `app.scheduling.enable` (defaults to enabled) — disabled in integration tests so
Testcontainers-based tests aren't racing the scheduler. Job failures are caught and split into
`OpenWeatherApiRequestException` vs. general exceptions, each incrementing a distinct
`import.job` Micrometer counter rather than failing the app.

### Database

Liquibase manages schema (`src/main/resources/db/changelog/`), changelog entries are numbered and
should not be edited once merged — add a new changelog file instead. `preliquibase/default.sql`
seeds data before Liquibase runs. Active profile controls Liquibase context (`e2e` for local,
`prod` for production).

## Frontend (SvelteKit, in `frontend/`)

```bash
cd frontend
npm run dev            # vite dev server
npm run build           # production build (static adapter)
npm run check            # svelte-kit sync + svelte-check (type checking)
npm run lint              # prettier --check + eslint
npm run format             # prettier --write
npm run test:unit          # vitest
npm run test:e2e            # playwright install + playwright test
npm run test                # vitest run + playwright e2e
```

- Uses Svelte 5 runes mode (forced via `vite.config.ts` `compilerOptions.runes`, except inside
  `node_modules`).
- **CSR only, no SSR/prerendering.** There is no `svelte.config.js` — the SvelteKit Vite plugin's
  options (including the adapter) are passed inline in `vite.config.ts`'s `sveltekit({...})` call
  instead, which silently takes precedence over `svelte.config.js` if one were ever added back.
  The adapter is `@sveltejs/adapter-static` with `fallback: 'index.html'` (SPA mode — `/` and
  `/details` are dynamic and can't be prerendered). `npm run build` writes a single
  `build/index.html` shell plus hashed `_app/` assets; there is no per-route HTML file.
- Styling: Tailwind v4 (`@tailwindcss/vite`) + daisyUI.
- Charts: chart.js (weather data is transformed into hourly-averaged series — see
  `toChartDataMap` in `src/lib/transform.ts`, which buckets readings by hour and computes the mean
  temperature/humidity/wind speed per bucket, in chronological order).
- Data loading happens in SvelteKit `+page.ts` loaders (`PageLoad`), which call the backend API
  directly via `src/lib/api-client.ts` — there is no separate frontend backend-for-frontend layer.
  The API base URL is `import.meta.env.VITE_API_BASE_URL`, falling back to the production API —
  e2e tests override it to point at the local WireMock instance (see below).
- Svelte 5.25+'s **writable `$derived`** is used in `details/+page.svelte` for state that should
  both track a prop and be locally overridable (e.g. `selected`, `weather`) — e.g.
  `let weather = $derived(page.data.weather ?? null)`, then later reassigned directly in an event
  handler. Don't reintroduce the older `$state` + `$effect` resync workaround for this; ESLint
  (`svelte/prefer-writable-derived`) flags it.

### Tests

- Unit tests (Vitest): colocated `*.test.ts` files, e.g. `src/lib/transform.test.ts`,
  `src/lib/api-client.test.ts`, `src/routes/page-load.test.ts` (tests the `+page.ts` loader).
  Route-loader test files are named `page-load.test.ts`, not `+page.test.ts` — a `+`-prefixed
  filename gets flagged by the SvelteKit Vite plugin as a reserved name even for non-route
  suffixes.
- E2e tests (Playwright): files matching `**/*.e2e.{ts,js}` under `frontend/e2e/`. These run
  against a **real local WireMock instance**, not browser-level `page.route()` mocking — the app
  build/preview can execute `load()` outside the browser's network stack in ways Playwright can't
  intercept, so route-mocking silently doesn't work here. `playwright.config.ts` defines two
  `webServer` entries: one starts WireMock via `docker compose up` (config/fixtures in
  `frontend/wiremock/`, originally copied from the old Angular app's wiremock setup), the other
  builds and previews the app with `VITE_API_BASE_URL` pointed at it. Requires Docker/Podman with
  compose support; on rootless podman the wiremock volume mount needs the `:Z` SELinux relabel
  suffix in `frontend/docker-compose.yml` or the mappings silently fail to load (mappings list
  comes back empty, every request 404s).

## Deployment

Production uses a separate compose file `cprod.yml` (distinct from the dev `docker-compose.yml`,
which only provides Postgres + Wiremock for local backend dev). `deploy.sh` tears down and
recreates the `weather-db`, `weather-api`, `weather-ui` containers from prebuilt
`oskarwestmeijer/weather-api:latest` / `weather-ui:latest` images and restarts the
`reverse-proxy` container. The backend image's entrypoint (`entrypoint.sh`) execs `java` as
process name `weather-backend`.

`frontend/Dockerfile` builds the SvelteKit static output and serves it with nginx
(`frontend/nginx/nginx.conf`, which must be explicitly `COPY`'d into the image — it's easy to add
the file without wiring it in and end up running nginx's stock config instead, which has no SPA
fallback and 404s on every route but `/`). The nginx config's `try_files $uri /index.html;` is
what makes client-side routes like `/details` work — it must keep matching whatever `fallback`
filename `vite.config.ts`'s adapter call uses.
