<script lang="ts">
	const props = $props();
	const overviewLocations = props.data.overviewLocations;

	function formatDateUTC(timestamp: string) {
		const d = new Date(timestamp);
		return d.toLocaleString('de-DE', {
			day: '2-digit',
			month: '2-digit',
			year: '2-digit',
			hour: '2-digit',
			minute: '2-digit',
			timeZone: 'UTC'
		});
	}
</script>

<div class="w-full flex justify-center">
	<div class="mt-10 grid gap-6 px-4 sm:grid-cols-2 lg:grid-cols-3">
		{#each overviewLocations as location (location.locationId)}
			<a href={`/details?locationId=${location.locationId}`} class="block">
				<div
					class="card bg-base-200 shadow-xl rounded-2xl p-6 transition hover:shadow-2xl hover:scale-105 cursor-pointer"
				>
					<div class="text-xl font-bold text-center">
						{location.cityName}
						<span class="opacity-60 text-base">({location.countryCode})</span>
					</div>

					<div class="mt-3 text-5xl font-extrabold text-center">
						{location.temperature}°C
					</div>

					<div class="mt-4 flex justify-around text-sm">
						<div class="flex flex-col items-center">
							<span class="font-semibold">Humidity</span>
							<span>{location.humidity}%</span>
						</div>
						<div class="flex flex-col items-center">
							<span class="font-semibold">Wind</span>
							<span>{location.windSpeed} km/h</span>
						</div>
					</div>

					<div class="mt-4 text-xs text-center opacity-70">
						Recorded at: {formatDateUTC(location.recordedAt)} UTC
					</div>
				</div>
			</a>
		{/each}
	</div>
</div>
