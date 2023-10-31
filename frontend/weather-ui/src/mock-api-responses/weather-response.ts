import { WeatherResponse } from "src/app/model/weather-response.model";

export const expectedHelsinkiWeatherResponse: WeatherResponse = {
    cityName: 'Helsinki',
    localZipCode: '00100',
    country: 'Finland',
    weatherData: [
        {
            temperature: 0.96,
            humidity: 90,
            windSpeed: 4.92,
            recordedAt: '2023-10-30T18:49:45.461955Z'
        },
        {
            temperature: 1.01,
            humidity: 90,
            windSpeed: 4.92,
            recordedAt: '2023-10-30T18:44:45.110622Z'
        }
    ]
};
