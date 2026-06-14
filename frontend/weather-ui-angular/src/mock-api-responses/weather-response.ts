import { WeatherResponse } from 'src/app/model/weather-response.model';

export const expectedHelsinkiWeatherResponse: WeatherResponse = {
    locationId: 4,
    cityName: 'Helsinki',
    country: 'Finland',
    weatherData: [
        {
            id: 'a24a8128-e7b8-4eff-8a73-1d2d63593f46',
            temperature: 10.0,
            humidity: 85,
            windSpeed: 2.57,
            recordedAt: '2023-11-19T11:00:15.793186Z'
        },
        {
            id: 'd8a9f33e-d5ae-4f79-b3e8-35319044592e',
            temperature: 10.0,
            humidity: 85,
            windSpeed: 2.57,
            recordedAt: '2023-11-19T10:55:15.512857Z'
        }
    ]
};
