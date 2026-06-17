import { describe, expect, it, vi } from 'vitest';
import type { OverviewLocation } from '$lib/types/overview';

vi.mock('$lib/api-client', () => ({
	getOverviewLocations: vi.fn()
}));

const { getOverviewLocations } = await import('$lib/api-client');
const { load } = await import('./+page');

async function runLoad() {
	const result = await load({ fetch } as Parameters<typeof load>[0]);
	return result as { overviewLocations: OverviewLocation[] };
}

describe('overview page load', () => {
	it('sorts locations by temperature and rounds them', async () => {
		vi.mocked(getOverviewLocations).mockResolvedValue({
			overview: [
				{
					locationId: 1,
					cityName: 'Hamburg',
					countryCode: 'GER',
					temperature: 3.6,
					humidity: 80,
					windSpeed: 2,
					recordedAt: '2024-11-23T12:00:00Z'
				},
				{
					locationId: 2,
					cityName: 'Helsinki',
					countryCode: 'FIN',
					temperature: -5.4,
					humidity: 91,
					windSpeed: 9,
					recordedAt: '2024-11-23T12:00:00Z'
				}
			]
		});

		const result = await runLoad();

		expect(result.overviewLocations.map((l) => l.cityName)).toEqual(['Helsinki', 'Hamburg']);
		expect(result.overviewLocations.map((l) => l.temperature)).toEqual([-5, 4]);
	});

	it('returns an empty list when the backend has no data', async () => {
		vi.mocked(getOverviewLocations).mockResolvedValue({ overview: undefined });

		const result = await runLoad();

		expect(result.overviewLocations).toEqual([]);
	});

	it('returns an empty list when the request fails', async () => {
		vi.mocked(getOverviewLocations).mockRejectedValue(new Error('network error'));

		const result = await runLoad();

		expect(result.overviewLocations).toEqual([]);
	});
});
