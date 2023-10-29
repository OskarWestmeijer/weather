import { Injectable } from '@angular/core';
import { Weather } from '../model/weather.model';
import { ChartData } from '../model/chart-data.model';
import { ChartType } from '../model/chart-type.enum';

@Injectable({
    providedIn: 'root'
})
export class WeatherService {
    constructor() {}

    public transformToMap(weatherData: Weather[]): Map<ChartType, ChartData[]> {
        const temperatureModel: ChartData[] = weatherData.map((weather: Weather) => ({
            data: weather.temperature,
            recordedAt: weather.recordedAt
        }));
        const humidityModel: ChartData[] = weatherData.map((weather: Weather) => ({
            data: weather.humidity,
            recordedAt: weather.recordedAt
        }));
        const windSpeedModel: ChartData[] = weatherData.map((weather: Weather) => ({
            data: weather.windSpeed,
            recordedAt: weather.recordedAt
        }));
        const weatherMap = new Map();
        weatherMap.set(ChartType.TEMPERATURE, temperatureModel);
        weatherMap.set(ChartType.HUMIDITY, humidityModel);
        weatherMap.set(ChartType.WIND_SPEED, windSpeedModel);
        return weatherMap;
    }
}
