<script lang="ts">
	import Chart from 'chart.js/auto';
	import { toChartDataMap } from '$lib/transform';
	import { getWeather } from '$lib/api-client';
	import type { ChartData } from '$lib/types';
	import type { Location } from '$lib/types/locations';

	let page = $props();

	let locations: Location[] = $derived(page.data.locations ?? []);
	let selected: Location | null = $derived(page.data.selected ?? locations[0] ?? null);
	// eslint-disable-next-line @typescript-eslint/no-explicit-any -- avoids a cascade of unrelated null-safety errors, see chart rendering below
	let weather: any = $derived(page.data.weather ?? null);

	let canvas: HTMLCanvasElement | undefined = $state();
	let chart: Chart | undefined;

	async function onSelectChange(event: Event) {
		const id = Number((event.target as HTMLSelectElement).value);
		selected = locations.find((l) => l.locationId === id) ?? selected;

		const raw = await getWeather(id);
		weather = toChartDataMap(raw.weatherData);
	}

	$effect(() => {
		if (!canvas || !weather) return;
		createChart();
	});

	function createChart() {
		if (!canvas) return;
		chart?.destroy();

		const windData = weather.get('WIND_SPEED').map((d: ChartData) => d.data);
		const tempData = weather.get('TEMPERATURE').map((d: ChartData) => d.data);
		const humData = weather.get('HUMIDITY').map((d: ChartData) => d.data);
		const timeLabels = weather.get('TEMPERATURE').map((d: ChartData) => d.recordedAt);

		chart = new Chart(canvas, {
			data: {
				labels: timeLabels,
				datasets: [
					{
						type: 'line',
						label: 'Temperature',
						data: tempData,
						borderColor: 'rgba(255, 159, 64, 1)',
						backgroundColor: 'rgba(255, 159, 64, 0.2)',
						tension: 0.3,
						yAxisID: 'y'
					},
					{
						type: 'bar',
						label: 'Humidity',
						data: humData,
						backgroundColor: 'rgba(54, 162, 235, 0.4)',
						borderWidth: 0,
						yAxisID: 'y1'
					}
				]
			},
			options: {
				responsive: true,
				maintainAspectRatio: false,
				scales: {
					// LEFT Y AXIS (Temperature)
					y: {
						type: 'linear',
						position: 'left',
						ticks: {
							color: 'black',
							callback: (val) => `${val}°C`
						},
						title: {
							display: true,
							text: 'Temperature (°C)',
							color: 'black'
						},
						grid: { display: false }
					},

					// RIGHT Y AXIS (Humidity)
					y1: {
						type: 'linear',
						position: 'right',
						ticks: { color: 'black' },
						min: 0,
						max: 100,
						title: {
							display: true,
							text: 'Humidity (%)',
							color: 'black'
						},
						grid: { drawOnChartArea: false }
					},

					// BOTTOM X AXIS (Wind speed)
					x: {
						position: 'bottom',
						offset: true,
						ticks: {
							color: 'black',
							callback: (_val, index) => `${windData[index]} km/h`
						},
						grid: { display: false }
					},

					// TOP X AXIS (Time labels)
					xTop: {
						position: 'top',
						offset: true,
						ticks: {
							color: 'black',
							callback: (_val, index) => timeLabels[index]
						},
						grid: { display: false }
					}
				},

				plugins: {
					legend: {
						labels: { color: 'black' },
						onClick: () => {} // disable toggling
					},
					tooltip: {
						callbacks: {
							afterBody: (items) => {
								const index = items[0].dataIndex;
								return `Wind: ${windData[index]} km/h`;
							}
						}
					}
				}
			}
		});
	}
</script>

<div>
	<div class="max-w-4xl mx-auto mt-10 p-4">
		<h1 class="text-3xl font-bold mb-6 text-center">
			Weather Details for {selected?.cityName}, {selected?.countryCode}
		</h1>

		<div class="mb-6 flex justify-center">
			<select
				id="location-select"
				class="select select-bordered w-full max-w-xs"
				onchange={onSelectChange}
			>
				{#each locations as loc (loc.locationId)}
					<option value={loc.locationId} selected={loc.locationId === selected?.locationId}>
						{loc.cityName} ({loc.countryCode})
					</option>
				{/each}
			</select>
		</div>
	</div>

	{#if weather}
		<div class="chart-wrapper">
			<canvas bind:this={canvas}></canvas>
		</div>
	{:else}
		<p class="text-center text-gray-500">Loading weather data...</p>
	{/if}
</div>

<style>
	.chart-wrapper {
		width: 95%;
		max-width: 1200px;
		aspect-ratio: 2.7 / 1;
		margin: 2.5rem auto 0 auto; /* same as mt-10 + centering */
		display: flex;
		justify-content: center;
		align-items: center;
	}

	@media (max-width: 640px) {
		.chart-wrapper {
			aspect-ratio: auto;
			height: 400px;
		}
	}

	canvas {
		width: 100% !important;
		height: 100% !important;
	}
</style>
