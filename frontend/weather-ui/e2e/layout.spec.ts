import { test, expect } from '@playwright/test';

test('navigation links test', async ({ page }) => {
    await page.goto('./');

    await expect(page.getByRole('link', { name: 'Overview' }).nth(0)).toBeVisible();
    await expect(page.getByRole('link', { name: 'Details' }).nth(0)).toBeVisible();
});

test('github link works', async ({ page }) => {
    await page.goto('./');

    await page.locator('a[href="https://github.com/OskarWestmeijer/weather"]').click();

    await page.waitForURL('https://github.com/OskarWestmeijer/weather');
    expect(page.url()).toBe('https://github.com/OskarWestmeijer/weather');
});

test('footer homepage link exists', async ({ page }) => {
    await page.goto('./');

    const homepageLink = await page.getByRole('link', { name: 'Oskar Westmeijer' }).nth(0);
    await expect(homepageLink).toBeVisible();
});
