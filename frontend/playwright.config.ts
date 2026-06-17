import { defineConfig } from '@playwright/test';

const WIREMOCK_URL = 'http://localhost:8080';

export default defineConfig({
	use: { baseURL: 'http://localhost:4173' },
	webServer: [
		{
			command: 'docker compose up',
			url: `${WIREMOCK_URL}/__admin/mappings`,
			reuseExistingServer: true
		},
		{
			command: 'npm run build && npm run preview',
			port: 4173,
			env: { VITE_API_BASE_URL: WIREMOCK_URL }
		}
	],
	testMatch: '**/*.e2e.{ts,js}'
});
