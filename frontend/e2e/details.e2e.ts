import { expect, test } from '@playwright/test';

test('shows weather details for the first location by default', async ({ page }) => {
	await page.goto('/details');

	await expect(page.getByRole('heading')).toContainText('Lübeck, GER');
	await expect(page.locator('#location-select option')).toHaveCount(5);
	await expect(page.locator('canvas')).toBeVisible();
});

test('updates the heading when a different location is selected', async ({ page }) => {
	await page.goto('/details');
	await expect(page.getByRole('heading')).toContainText('Lübeck, GER');

	await page.locator('#location-select').selectOption('2');

	await expect(page.getByRole('heading')).toContainText('Hamburg, GER');
	await expect(page.locator('canvas')).toBeVisible();
});
