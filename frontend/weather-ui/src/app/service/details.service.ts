import { Injectable } from '@angular/core';
import { Weather } from '../model/weather.model';
import { ChartData } from '../model/chart-data.model';
import { ChartType } from '../model/chart-type.enum';

@Injectable({
    providedIn: 'root'
})
export class DetailsService {
    /**
     * Increase readability of the ui-chart by calculating hourly means for weather types.
     * @param weatherData
     * @returns
     */
    public toChartDataMap(weatherData: Weather[]): Map<ChartType, ChartData[]> {
        const hourlyWeather: Map<string, Weather[]> = this.groupByHour(weatherData);
        const chartDataMap = this.calcHourlyMeanByType(hourlyWeather);

        // Reverse each ChartType array so latest hour comes first
        chartDataMap.forEach((arr, chartType) => {
            chartDataMap.set(chartType, arr.reverse());
        });

        return chartDataMap;
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
        // hour = "2025-09-26T19" (ISO string truncated to hour)
        const dateObj = new Date(hour + ':00:00Z'); // make a valid UTC Date
        const hours = dateObj.getUTCHours().toString().padStart(2, '0');
        const minutes = '00';

        let timeLabel = `${hours}:${minutes}`;

        // Add date every 6 hours (00, 06, 12, 18)
        if (dateObj.getUTCHours() % 6 === 0) {
            const day = dateObj.getUTCDate().toString().padStart(2, '0');
            const month = Intl.DateTimeFormat('en', { month: 'short', timeZone: 'UTC' }).format(dateObj);
            timeLabel = `${hours}:${minutes} ${month} ${day}`;
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
