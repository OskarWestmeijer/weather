import { Weather } from './weather';

export interface WeatherResponse {
    cityName: string;
    localZipCode: string;
    country: string;
    weatherData: Weather[];
}
