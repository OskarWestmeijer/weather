export interface Location {
	locationId: number;
	localZipCode: string;
	cityName: string;
	country: string;
	countryCode: string;
	lastImportAt: string;
}

export interface LocationsResponse {
	locations: Location[];
}
