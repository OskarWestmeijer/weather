<script lang="ts">
	import { resolve } from '$app/paths';
	import WeatherMap from '$lib/WeatherMap.svelte';
	import { TEMPERATURE_GRADIENT, temperatureBarWidth, temperatureColor } from '$lib/temperature';

	let { data } = $props();
	let overviewLocations = $derived(data.overviewLocations);

	// Locations arrive sorted coldest first, so the ends of the list are the scale bounds.
	let scaleMin = $derived(
		overviewLocations.length ? Math.min(...overviewLocations.map((l) => l.temperature)) : 0
	);
	let scaleMax = $derived(
		overviewLocations.length ? Math.max(...overviewLocations.map((l) => l.temperature)) : 0
	);

	let highlight = $state<number | null>(null);
</script>

<div class="mx-auto w-full max-w-[1240px] px-4 py-7">
	{#if overviewLocations.length === 0}
		<div
			class="flex min-h-[320px] items-center justify-center rounded-2xl bg-[#f4f5f7] text-[15px] text-[#6b7480]"
		>
			No weather data available right now.
		</div>
	{:else}
		<div class="grid gap-5 lg:grid-cols-[1.18fr_1fr]">
			<!-- MAP -->
			<div class="flex flex-col gap-2.5 rounded-2xl bg-[#f4f5f7] p-3.5">
				<div class="min-h-[320px] flex-1 overflow-hidden rounded-[11px] bg-[#e7ebf1]">
					<WeatherMap
						locations={overviewLocations}
						{highlight}
						onhighlight={(locationId) => (highlight = locationId)}
					/>
				</div>
				<div class="flex items-center gap-2.5 px-1 pb-0.5">
					<span class="hidden text-[13px] whitespace-nowrap text-[#6b7480] sm:inline">
						Leaflet · drag to pan, scroll to zoom
					</span>
					<span class="h-[7px] flex-1 rounded-[4px]" style="background:{TEMPERATURE_GRADIENT}"
					></span>
					<span class="text-[13px] whitespace-nowrap text-[#6b7480]">
						{scaleMin}° → {scaleMax}°
					</span>
				</div>
			</div>

			<!-- RANKED RAIL -->
			<div class="flex flex-col overflow-hidden rounded-2xl bg-[#f4f5f7]">
				<div class="flex items-baseline px-[18px] pt-4 pb-2.5">
					<span class="text-[15px] font-bold whitespace-nowrap text-[#233554]">
						Coldest → warmest
					</span>
				</div>

				{#each overviewLocations as location (location.locationId)}
					<a
						href={resolve(`/details?locationId=${location.locationId}`)}
						class="grid grid-cols-[1fr_96px] items-center gap-3 border-t border-[#e6e9ef] px-[18px] py-[13px] transition-colors"
						class:bg-[#eef1f5]={highlight === location.locationId}
						onmouseenter={() => (highlight = location.locationId)}
						onmouseleave={() => (highlight = null)}
						onfocus={() => (highlight = location.locationId)}
						onblur={() => (highlight = null)}
					>
						<div class="flex min-w-0 flex-col gap-1.5">
							<span class="truncate text-base font-bold text-[#233554]">
								{location.cityName}
								<span class="font-medium text-[#6b7480]">{location.countryCode}</span>
							</span>
							<span class="block h-1.5 rounded-[3px] bg-[#e3e7ed]">
								<span
									class="block h-1.5 rounded-[3px]"
									style="width:{temperatureBarWidth(
										location.temperature,
										scaleMin,
										scaleMax
									)}%;background:{temperatureColor(location.temperature)}"
								></span>
							</span>
						</div>
						<div class="flex flex-col items-end gap-0.5">
							<span class="text-[23px] font-extrabold text-[#233554]">
								{location.temperature}°
							</span>
							<span class="text-[13px] whitespace-nowrap text-[#6b7480]">
								{location.windSpeed.toFixed(1)} km/h
							</span>
						</div>
					</a>
				{/each}

				<div
					class="mt-auto flex items-center justify-end border-t border-[#e6e9ef] px-[18px] py-[13px]"
				>
					<span class="text-sm font-semibold whitespace-nowrap text-[#233554]">
						Open details →
					</span>
				</div>
			</div>
		</div>
	{/if}
</div>
