import { expect, test } from '@playwright/test';

test('shows location tiles sorted by temperature, coldest first', async ({ page }) => {
	await page.goto('/');

	const cards = page.locator('a.block .card');
	await expect(cards).toHaveCount(5);

	const firstCard = cards.first();
	await expect(firstCard.locator('div.text-xl.font-bold')).toContainText('Kangasala');
	await expect(firstCard.locator('div.mt-3.text-5xl')).toHaveText('-5°C');
});

test('navigates to the details page when a tile is clicked', async ({ page }) => {
	await page.goto('/');

	await page.locator('a.block .card').first().click();

	await expect(page).toHaveURL(/\/details\?locationId=\d+/);
});

test('shows the main navigation links', async ({ page }) => {
	await page.goto('/');

	await expect(page.getByRole('link', { name: 'Overview' }).first()).toBeVisible();
	await expect(page.getByRole('link', { name: 'Details' }).first()).toBeVisible();
});
