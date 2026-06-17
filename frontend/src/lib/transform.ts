import type { ChartData, ChartType, Weather } from './types';

export function toChartDataMap(weatherData: Weather[]): Map<ChartType, ChartData[]> {
	const hourly = groupByHour(weatherData);
	return calcHourlyMeanByType(hourly);
}

function groupByHour(weatherData: Weather[]): Map<string, Weather[]> {
	const hourly = new Map<string, Weather[]>();

	for (const w of weatherData) {
		const hour = w.recordedAt.slice(0, 13); // "2025-09-26T19"
		if (!hourly.has(hour)) hourly.set(hour, []);
		hourly.get(hour)!.push(w);
	}
	return hourly;
}

function calcHourlyMeanByType(hourly: Map<string, Weather[]>): Map<ChartType, ChartData[]> {
	const map = initChartMap();

	for (const [hour, items] of hourly.entries()) {
		let temp = 0,
			hum = 0,
			wind = 0;

		for (const w of items) {
			temp += w.temperature;
			hum += w.humidity;
			wind += w.windSpeed;
		}

		map.get('TEMPERATURE')!.push(toChartData(hour, temp, items.length));
		map.get('HUMIDITY')!.push(toChartData(hour, hum, items.length));
		map.get('WIND_SPEED')!.push(toChartData(hour, wind, items.length));
	}

	return map;
}

function toChartData(hour: string, sum: number, count: number): ChartData {
	const date = new Date(hour + ':00:00Z');

	const hours = date.getUTCHours().toString().padStart(2, '0');
	const minutes = '00';

	let label = `${hours}:${minutes}`;

	// Add date every 6 hours
	if (date.getUTCHours() % 6 === 0) {
		const day = date.getUTCDate().toString().padStart(2, '0');
		const month = Intl.DateTimeFormat('en', { month: 'short', timeZone: 'UTC' }).format(date);
		label = `${hours}:${minutes} ${month} ${day}`;
	}

	return {
		recordedAt: label,
		data: (sum / count).toFixed(0)
	};
}

function initChartMap(): Map<ChartType, ChartData[]> {
	return new Map([
		['TEMPERATURE', []],
		['HUMIDITY', []],
		['WIND_SPEED', []]
	]);
}
