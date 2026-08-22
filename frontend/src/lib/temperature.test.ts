import { describe, expect, it } from 'vitest';
import { temperatureBarWidth, temperatureColor, temperatureRatio } from './temperature';

describe('temperatureColor', () => {
	it('clamps below the coldest ramp stop', () => {
		expect(temperatureColor(-12)).toBe('#3f79b8');
		expect(temperatureColor(0)).toBe('#3f79b8');
	});

	it('clamps above the warmest ramp stop', () => {
		expect(temperatureColor(40)).toBe('#e0623c');
	});

	it('interpolates between two stops', () => {
		// halfway between 0 (#3f79b8) and 10 (#4d86c0)
		expect(temperatureColor(5)).toBe('rgb(70,128,188)');
	});
});

describe('temperatureRatio', () => {
	it('places the coldest reading at 0 and the warmest at 100', () => {
		expect(temperatureRatio(-5, -5, 6)).toBe(0);
		expect(temperatureRatio(6, -5, 6)).toBe(100);
	});

	it('places a mid reading proportionally', () => {
		expect(temperatureRatio(5, 0, 20)).toBe(25);
	});

	it('returns a full bar when every reading is identical', () => {
		expect(temperatureRatio(7, 7, 7)).toBe(100);
	});

	it('clamps readings outside the scale', () => {
		expect(temperatureRatio(-40, 0, 20)).toBe(0);
		expect(temperatureRatio(99, 0, 20)).toBe(100);
	});
});

describe('temperatureBarWidth', () => {
	it('keeps the coldest bar visible', () => {
		expect(temperatureBarWidth(-5, -5, 6)).toBe(4);
	});

	it('leaves bars above the floor untouched', () => {
		expect(temperatureBarWidth(5, 0, 20)).toBe(25);
	});
});
