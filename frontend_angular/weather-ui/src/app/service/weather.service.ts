import { Injectable } from '@angular/core';
import { Weather } from '../model/weather.model';
import { ChartData } from '../model/chart-data.model';
import { ChartType } from '../model/chart-type.enum';

@Injectable({
    providedIn: 'root'
})
export class WeatherService {
    /**
     * Increase readability of the ui-chart by calculating hourly means for weather types.
     * @param weatherData
     * @returns
     */
    public toChartDataMap(weatherData: Weather[]): Map<ChartType, ChartData[]> {
        const hourlyWeather: Map<string, Weather[]> = this.groupByHour(weatherData);
        return this.calcHourlyMeanByType(hourlyWeather);
    }

    /**
     * Truncates timestamp strings down to hour precision in order to calculate hourly mean weather values.
     * example: 2023-10-27T19:11:21.738405Z -> 2023-10-27T19
     * @param weatherData
     */
    private groupByHour(weatherData: Weather[]): Map<string, Weather[]> {
        const hourlyWeather = new Map<string, Weather[]>();
        weatherData.forEach((weather) => {
            const hour = weather.recordedAt.slice(0, 13);
            hourlyWeather.has(hour) ? hourlyWeather.get(hour)?.push(weather) : hourlyWeather.set(hour, [weather]);
        });
        return hourlyWeather;
    }

    private calcHourlyMeanByType(hourlyWeather: Map<string, Weather[]>): Map<ChartType, ChartData[]> {
        const chartDataMap = this.initChartMap();

        hourlyWeather.forEach((items: Weather[], hour: string) => {
            let temparatureSum = 0;
            let humiditySum = 0;
            let windSpeedSum = 0;

            items.forEach((weather) => {
                temparatureSum += weather.temperature;
                humiditySum += weather.humidity;
                windSpeedSum += weather.windSpeed;
            });

            chartDataMap.get(ChartType.TEMPERATURE)?.unshift(this.toChartData(hour, temparatureSum, items.length));
            chartDataMap.get(ChartType.HUMIDITY)?.unshift(this.toChartData(hour, humiditySum, items.length));
            chartDataMap.get(ChartType.WIND_SPEED)?.unshift(this.toChartData(hour, windSpeedSum, items.length));
        });
        return chartDataMap;
    }

    /**
     * Map the calculated values to a UI presentation.
     *
     * @param hour received as '2023-10-27T19'
     * @param propertySum
     * @param propertyCount
     * @returns
     */
    private toChartData(hour: string, propertySum: number, propertyCount: number): ChartData {
        let timeLabel = hour.slice(11, 13).concat(':00');

        if (timeLabel === '00:00') {
            const day = hour.slice(8, 10);
            const month = Intl.DateTimeFormat('en', { month: 'short' }).format(new Date(hour.slice(5, 7)));
            timeLabel = month.concat(' ').concat(day);
        }

        const propertyMeanAsLabel = (propertySum / propertyCount).toFixed(0).toString();
        return { recordedAt: timeLabel, data: propertyMeanAsLabel };
    }

    private initChartMap(): Map<ChartType, ChartData[]> {
        const chartDataMap = new Map<ChartType, ChartData[]>();
        chartDataMap.set(ChartType.TEMPERATURE, []);
        chartDataMap.set(ChartType.HUMIDITY, []);
        chartDataMap.set(ChartType.WIND_SPEED, []);
        return chartDataMap;
    }
}
