import { describe, expect, it } from 'vitest';
import { toChartDataMap } from './transform';
import type { Weather } from './types';

function reading(
	recordedAt: string,
	temperature: number,
	humidity: number,
	windSpeed: number
): Weather {
	return { id: recordedAt, recordedAt, temperature, humidity, windSpeed };
}

describe('toChartDataMap', () => {
	it('returns an entry per chart type', () => {
		const map = toChartDataMap([reading('2024-11-22T18:05:00Z', 1, 80, 4)]);

		expect([...map.keys()]).toEqual(['TEMPERATURE', 'HUMIDITY', 'WIND_SPEED']);
	});

	it('averages readings within the same hour', () => {
		const map = toChartDataMap([
			reading('2024-11-22T14:05:00Z', 1, 80, 4),
			reading('2024-11-22T14:45:00Z', 3, 84, 6)
		]);

		expect(map.get('TEMPERATURE')).toEqual([{ recordedAt: '14:00', data: '2' }]);
		expect(map.get('HUMIDITY')).toEqual([{ recordedAt: '14:00', data: '82' }]);
		expect(map.get('WIND_SPEED')).toEqual([{ recordedAt: '14:00', data: '5' }]);
	});

	it('keeps separate hours in chronological order', () => {
		const map = toChartDataMap([
			reading('2024-11-22T14:00:00Z', 1, 80, 4),
			reading('2024-11-22T15:00:00Z', 2, 81, 5),
			reading('2024-11-22T16:00:00Z', 3, 82, 6)
		]);

		expect(map.get('TEMPERATURE')!.map((d) => d.recordedAt)).toEqual(['14:00', '15:00', '16:00']);
		expect(map.get('TEMPERATURE')!.map((d) => d.data)).toEqual(['1', '2', '3']);
	});

	it('appends the date to the label every 6 hours (UTC)', () => {
		const map = toChartDataMap([
			reading('2024-11-22T05:00:00Z', 1, 80, 4),
			reading('2024-11-22T06:00:00Z', 1, 80, 4)
		]);

		expect(map.get('TEMPERATURE')!.map((d) => d.recordedAt)).toEqual(['05:00', '06:00 Nov 22']);
	});

	it('returns an empty map when there is no weather data', () => {
		const map = toChartDataMap([]);

		expect(map.get('TEMPERATURE')).toEqual([]);
		expect(map.get('HUMIDITY')).toEqual([]);
		expect(map.get('WIND_SPEED')).toEqual([]);
	});
});
