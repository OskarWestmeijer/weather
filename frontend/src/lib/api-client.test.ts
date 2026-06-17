import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { getLocations, getOverviewLocations, getWeather } from './api-client';

const API = 'https://api.weather.oskar-westmeijer.com';

function fakeFetch(body: unknown, ok = true) {
	return vi.fn().mockResolvedValue({
		ok,
		json: () => Promise.resolve(body)
	});
}

describe('getOverviewLocations', () => {
	it('fetches the overview endpoint and returns the parsed response', async () => {
		const fetchFn = fakeFetch({ overview: [] });

		const result = await getOverviewLocations(fetchFn);

		expect(fetchFn).toHaveBeenCalledWith(`${API}/overview`);
		expect(result).toEqual({ overview: [] });
	});

	it('throws when the response is not ok', async () => {
		const fetchFn = fakeFetch(null, false);

		await expect(getOverviewLocations(fetchFn)).rejects.toThrow(
			'Failed to load overview locations'
		);
	});
});

describe('getLocations', () => {
	it('fetches the locations endpoint and returns the parsed response', async () => {
		const fetchFn = fakeFetch({ locations: [] });

		const result = await getLocations(fetchFn);

		expect(fetchFn).toHaveBeenCalledWith(`${API}/locations`);
		expect(result).toEqual({ locations: [] });
	});

	it('throws when the response is not ok', async () => {
		const fetchFn = fakeFetch(null, false);

		await expect(getLocations(fetchFn)).rejects.toThrow('Failed to load locations');
	});
});

describe('getWeather', () => {
	beforeEach(() => {
		vi.useFakeTimers();
		vi.setSystemTime(new Date('2024-11-23T12:00:00.000Z'));
	});

	afterEach(() => {
		vi.useRealTimers();
	});

	it('requests weather for the location since 24h ago', async () => {
		const fetchFn = fakeFetch({ weatherData: [] });

		await getWeather(42, fetchFn);

		expect(fetchFn).toHaveBeenCalledWith(
			`${API}/weather?locationId=42&from=2024-11-22T12:00:00.000Z`
		);
	});

	it('throws when the response is not ok', async () => {
		const fetchFn = fakeFetch(null, false);

		await expect(getWeather(42, fetchFn)).rejects.toThrow('Failed to load weather');
	});
});
