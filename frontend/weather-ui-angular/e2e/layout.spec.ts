import { test, expect } from '@playwright/test';

test('navigation links test', async ({ page }) => {
    await page.goto('./');

    await expect(page.getByRole('link', { name: 'Overview' }).nth(0)).toBeVisible();
    await expect(page.getByRole('link', { name: 'Details' }).nth(0)).toBeVisible();
});

test('footer homepage link exists', async ({ page }) => {
    await page.goto('./');

    const homepageLink = await page.getByRole('link', { name: 'Oskar Westmeijer' }).nth(0);
    await expect(homepageLink).toBeVisible();
});
