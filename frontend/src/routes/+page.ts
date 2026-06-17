import type { PageLoad } from './$types';
import { getOverviewLocations } from '$lib/api-client';

export const load: PageLoad = async ({ fetch }) => {
    let overviewLocations;

    try {
        overviewLocations = (await getOverviewLocations(fetch)).overview ?? [];
    } catch {
        return { overviewLocations: [] };
    }

    // Sort by temperature
    overviewLocations.sort((a, b) => a.temperature - b.temperature);

    // Round temperatures
    overviewLocations = overviewLocations.map((item) => ({
        ...item,
        temperature: Math.round(item.temperature)
    }));

    return { overviewLocations };
};
