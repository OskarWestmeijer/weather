// src/routes/details/+page.ts
import type { PageLoad } from './$types';
import { getLocations, getWeather } from '$lib/api-client';
import { toChartDataMap } from '$lib/transform';

export const load: PageLoad = async ({ fetch, url }) => {
	const locationId = Number(url.searchParams.get('locationId'));

	const locations = (await getLocations(fetch)).locations;

	let selected = locations[0];
	if (locationId) {
		selected = locations.find((l) => l.locationId === locationId) ?? selected;
	}

	const rawWeather = await getWeather(selected.locationId, fetch);

	return {
		locations,
		selected,
		weather: toChartDataMap(rawWeather.weatherData)
	};
};
