import { Weather } from './weather.model';

export interface WeatherResponse {
    cityName: string;
    localZipCode: string;
    country: string;
    weatherData: Weather[];
}
