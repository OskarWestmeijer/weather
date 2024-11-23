import { test, expect } from '@playwright/test';

test('navigation links and canvas test', async ({ page }) => {
    // Navigate to the page
    await page.goto('./');

    // Verify that the canvas element with the ID "OverviewChart" is present
    const canvas = await page.locator('#OverviewChart');
    await expect(canvas).toBeVisible();

    const box = await canvas.boundingBox();
    expect(box).not.toBeNull();
    expect(box?.width).toBeGreaterThan(0);
    expect(box?.height).toBeGreaterThan(0);
});
