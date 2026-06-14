// src/lib/api/weather.ts
import type { OverviewLocationsResponse } from '$lib/types/overview';
import type { LocationsResponse } from '$lib/types/locations';
import type { WeatherResponse } from '$lib/types/weather';

const API = 'https://api.weather.oskar-westmeijer.com';

export async function getOverviewLocations(fetchFn = fetch): Promise<OverviewLocationsResponse> {
    const res = await fetchFn(`${API}/overview`);
    if (!res.ok) throw new Error('Failed to load overview locations');
    return res.json();
}

export async function getLocations(fetchFn = fetch): Promise<LocationsResponse> {
    const res = await fetchFn(`${API}/locations`);
    if (!res.ok) throw new Error('Failed to load locations');
    return res.json();
}

export async function getWeather(locationId: number, fetchFn = fetch): Promise<WeatherResponse> {
    const now = Date.now();
    const from = new Date(now - 24 * 60 * 60 * 1000).toISOString();

    const url = `${API}/weather?locationId=${locationId}&from=${from}`;
    const res = await fetchFn(url);

    if (!res.ok) throw new Error('Failed to load weather');
    return res.json();
}
