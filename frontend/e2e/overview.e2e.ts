import { expect, test } from '@playwright/test';

const railRows = 'a[href^="/details?locationId="]';

test('ranks locations coldest first in the rail', async ({ page }) => {
	await page.goto('/');

	const rows = page.locator(railRows);
	await expect(rows).toHaveCount(5);

	const firstRow = rows.first();
	await expect(firstRow).toContainText('Kangasala');
	await expect(firstRow).toContainText('-5°');
});

test('shows the temperature legend spanning the coldest and warmest reading', async ({ page }) => {
	await page.goto('/');

	await expect(page.getByText('My favourite places')).toBeVisible();
	await expect(page.getByText('coldest → warmest')).toBeVisible();
	await expect(page.getByText('-5° → 6°')).toBeVisible();
});

test('renders the leaflet map with a pin per location', async ({ page }) => {
	await page.goto('/');

	const map = page.getByTestId('weather-map');
	await expect(map).toBeVisible();
	await expect(map).toHaveClass(/leaflet-container/);
	await expect(map.locator('.wx-pin')).toHaveCount(5);
	await expect(map.locator('.wx-pin').first()).toHaveText('-5°');
});

// Leaflet only adds layers once the map has a center/zoom. If that view is ever
// missing the basemap silently disappears, so assert its attribution is present.
test('adds the basemap tile layer', async ({ page }) => {
	await page.goto('/');

	const map = page.getByTestId('weather-map');
	await expect(map.locator('.leaflet-control-attribution')).toContainText('CARTO');
	await expect(map.locator('.leaflet-tile-pane .leaflet-layer')).toHaveCount(1);
});

test('navigates to the details page when a rail row is clicked', async ({ page }) => {
	await page.goto('/');

	await page.locator(railRows).first().click();

	await expect(page).toHaveURL(/\/details\?locationId=\d+/);
});

test('shows the main navigation links', async ({ page }) => {
	await page.goto('/');

	await expect(page.getByRole('link', { name: 'Overview' }).first()).toBeVisible();
	await expect(page.getByRole('link', { name: 'Details' }).first()).toBeVisible();
});
