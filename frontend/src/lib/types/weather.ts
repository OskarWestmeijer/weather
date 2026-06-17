export interface Weather {
	id: string;
	temperature: number;
	humidity: number;
	windSpeed: number;
	recordedAt: string;
}

export interface PagingDetails {
	pageRecordsCount: number;
	totalRecordsCount: number;
	hasNewerRecords: boolean;
	nextFrom?: string | null;
	nextLink?: string | null;
}

export interface WeatherResponse {
	locationId: number;
	cityName: string;
	country: string;
	weatherData: Weather[];
	pagingDetails: PagingDetails;
}
