import { beforeEach, describe, expect, it, vi } from 'vitest';
import type { Location } from '$lib/types/locations';

vi.mock('$lib/api-client', () => ({
	getLocations: vi.fn(),
	getWeather: vi.fn()
}));

const { getLocations, getWeather } = await import('$lib/api-client');
const { load } = await import('./+page');

const locations = [
	{
		locationId: 1,
		localZipCode: '1',
		cityName: 'Lübeck',
		country: 'Germany',
		countryCode: 'GER',
		lastImportAt: '2024-11-23T12:00:00Z'
	},
	{
		locationId: 2,
		localZipCode: '2',
		cityName: 'Hamburg',
		country: 'Germany',
		countryCode: 'GER',
		lastImportAt: '2024-11-23T12:00:00Z'
	}
];

async function runLoad(locationId?: string) {
	const url = new URL(`http://localhost/details${locationId ? `?locationId=${locationId}` : ''}`);
	const result = await load({ fetch, url } as Parameters<typeof load>[0]);
	return result as { locations: Location[]; selected: Location };
}

describe('details page load', () => {
	beforeEach(() => {
		vi.mocked(getLocations).mockResolvedValue({ locations });
		vi.mocked(getWeather).mockResolvedValue({
			locationId: 1,
			cityName: 'Lübeck',
			country: 'Germany',
			weatherData: [],
			pagingDetails: { pageRecordsCount: 0, totalRecordsCount: 0, hasNewerRecords: false }
		});
	});

	it('defaults to the first location when no locationId is given', async () => {
		const result = await runLoad();

		expect(result.selected.cityName).toBe('Lübeck');
	});

	it('selects the location matching the locationId query param', async () => {
		const result = await runLoad('2');

		expect(result.selected.cityName).toBe('Hamburg');
	});

	it('falls back to the first location when the locationId does not match any location', async () => {
		const result = await runLoad('999');

		expect(result.selected.cityName).toBe('Lübeck');
	});

	it('fetches weather for the selected location', async () => {
		await runLoad('2');

		expect(getWeather).toHaveBeenCalledWith(2, fetch);
	});
});
