import { test, expect } from '@playwright/test';

test('locations select has 5 options', async ({ page }) => {
    // Navigate to the page
    await page.goto('./details');

    const selectElement = page.locator('#location-select');

    await expect(selectElement).toBeVisible();

    const options = selectElement.locator('option');

    await expect(options).toHaveCount(5);
});
