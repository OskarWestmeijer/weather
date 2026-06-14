import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
    const res = await fetch('https://api.weather.oskar-westmeijer.com/overview');

    if (!res.ok) {
        return { overviewLocations: [] };
    }

    const data = await res.json();

    let overviewLocations = data.overview ?? [];

    // Sort by temperature
    overviewLocations.sort((a, b) => a.temperature - b.temperature);

    // Round temperatures
    overviewLocations = overviewLocations.map((item) => ({
        ...item,
        temperature: Math.round(item.temperature)
    }));

    return { overviewLocations };
};
