/**
 * Temperature colour ramp, taken from the overview redesign.
 * Stops are °C -> hex; values in between are linearly interpolated.
 */
const RAMP: [number, string][] = [
	[0, '#3f79b8'],
	[10, '#4d86c0'],
	[15, '#5990c2'],
	[16, '#4f9fa4'],
	[18, '#a9a565'],
	[20, '#e8a33d'],
	[26, '#e0623c']
];

function mixHex(a: string, b: string, t: number): string {
	const parse = (hex: string) => [1, 3, 5].map((i) => parseInt(hex.slice(i, i + 2), 16));
	const [from, to] = [parse(a), parse(b)];
	return `rgb(${from.map((v, i) => Math.round(v + (to[i] - v) * t)).join(',')})`;
}

export function temperatureColor(temperature: number): string {
	if (temperature <= RAMP[0][0]) return RAMP[0][1];
	for (let i = 1; i < RAMP.length; i++) {
		if (temperature <= RAMP[i][0]) {
			const [low, high] = [RAMP[i - 1], RAMP[i]];
			return mixHex(low[1], high[1], (temperature - low[0]) / (high[0] - low[0]));
		}
	}
	return RAMP[RAMP.length - 1][1];
}

/** CSS gradient matching the ramp, used for the legend bar under the map. */
export const TEMPERATURE_GRADIENT =
	'linear-gradient(90deg,#4d86c0,#4f9fa4,#a9a565,#e8a33d,#e0623c)';

/**
 * Position of `temperature` on a min..max scale, as a 0-100 percentage.
 * Falls back to a full bar when every location reports the same temperature.
 */
export function temperatureRatio(temperature: number, min: number, max: number): number {
	if (max <= min) return 100;
	const clamped = Math.min(Math.max(temperature, min), max);
	return ((clamped - min) / (max - min)) * 100;
}

/** Smallest bar still wide enough to read as a bar rather than as nothing. */
const MIN_BAR_WIDTH = 4;

/**
 * Bar width for the ranked rail. The coldest location sits at ratio 0, which would
 * render an invisible bar, so every bar keeps a small floor.
 */
export function temperatureBarWidth(temperature: number, min: number, max: number): number {
	return Math.max(temperatureRatio(temperature, min, max), MIN_BAR_WIDTH);
}
