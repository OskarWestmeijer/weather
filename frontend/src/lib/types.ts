export type { Weather } from './types/weather';

export type ChartType = 'TEMPERATURE' | 'HUMIDITY' | 'WIND_SPEED';

export interface ChartData {
	recordedAt: string;
	data: string;
}
