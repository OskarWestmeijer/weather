import { Weather } from './weather.model';

export interface WeatherResponse {
    locationId: number;
    cityName: string;
    country: string;
    weatherData: Weather[];
}
