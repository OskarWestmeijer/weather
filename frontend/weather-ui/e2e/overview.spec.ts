import { test, expect } from '@playwright/test';

test('overview page location tiles display correct data', async ({ page }) => {
    await page.goto('/');

    const firstCard = page.locator('a.block .card').first();

    // City
    const cityName = firstCard.locator('div.text-xl.font-bold');
    await expect(cityName).toHaveText(/Kangasala/);

    // Temperature
    const temperature = firstCard.locator('div.mt-3.text-5xl');
    await expect(temperature).toHaveText('-5°C');

    // Humidity
    const humidity = firstCard.locator('div.mt-4 div.flex-col:has(span:text-is("Humidity")) span').nth(1);
    await expect(humidity).toHaveText('95%');

    // Wind
    const wind = firstCard.locator('div.mt-4 div.flex-col:has(span:text-is("Wind")) span').nth(1);
    await expect(wind).toHaveText('3.08 km/h');

    // Timestamp
    const timestamp = firstCard.locator('div.mt-4.text-xs');
    await expect(timestamp).toHaveText(/23\.11\.24, 17:32 UTC/);

    // Click and verify navigation
    await firstCard.click();
    await expect(page).toHaveURL(/\/details\?locationId=\d+/);
});
