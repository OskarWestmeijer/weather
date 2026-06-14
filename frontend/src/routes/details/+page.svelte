<script lang="ts">
	import Chart from 'chart.js/auto';
	import { toChartDataMap } from '$lib/transform';
	import { getLocations, getWeather } from '$lib/api-client';

	// LEGAL: top‑level props initialization
	let page = $props();

	// SAFE local state
	let locations = page.data.locations ?? [];
	let selected = page.data.selected ?? locations[0] ?? null;

	let weather = $state(page.data.weather ?? null);

	console.log('Initial locations:', locations);

	let canvas;
	let chart;

	async function onSelectChange(event: Event) {
		const id = Number((event.target as HTMLSelectElement).value);

		const raw = await getWeather(id);
		weather = toChartDataMap(raw.weatherData);
	}

	$effect(() => {
		if (!canvas || !weather) return;
		createChart();
	});

	function createChart() {
		console.log('Creating chart with weather data: ', weather);
		chart?.destroy();

		const windData = weather.get('WIND_SPEED')!.map((d) => d.data);
		const tempData = weather.get('TEMPERATURE')!.map((d) => d.data);
		const humData = weather.get('HUMIDITY')!.map((d) => d.data);

		const timeLabels = weather.get('TEMPERATURE')!.map((d) => d.recordedAt);
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
				maintainAspectRatio: false
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
			<select class="select select-bordered w-full max-w-xs" on:change={onSelectChange}>
				{#each locations as loc (loc.locationId)}
					<option value={loc.locationId} selected={loc.locationId === selected?.locationId}>
						{loc.cityName} ({loc.countryCode})
					</option>
				{/each}
			</select>
		</div>

		{#if weather}
			<div class="relative h-96">
				<canvas bind:this={canvas}></canvas>
			</div>
		{:else}
			<p class="text-center text-gray-500">Loading weather data...</p>
		{/if}
	</div>
</div>
