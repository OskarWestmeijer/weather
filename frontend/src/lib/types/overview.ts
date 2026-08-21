export interface OverviewLocation {
	locationId: number;
	cityName: string;
	countryCode: string;
	latitude: string;
	longitude: string;
	temperature: number;
	humidity: number;
	windSpeed: number;
	recordedAt: string;
}

export interface OverviewLocationsResponse {
	overview?: OverviewLocation[];
}
