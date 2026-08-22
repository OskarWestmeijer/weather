<script lang="ts">
	import { onMount } from 'svelte';
	import 'leaflet/dist/leaflet.css';
	import type * as LeafletNS from 'leaflet';
	import type { OverviewLocation } from '$lib/types/overview';
	import { temperatureColor } from '$lib/temperature';

	let {
		locations,
		highlight = null,
		onhighlight
	}: {
		locations: OverviewLocation[];
		highlight?: number | null;
		onhighlight?: (locationId: number | null) => void;
	} = $props();

	/** How far above its dot a temperature chip floats, in pixels. */
	const CHIP_OFFSET_Y = -26;

	type Leader = { line: LeafletNS.Polyline; lat: number; lon: number };

	let container: HTMLDivElement;
	let unavailable = $state(false);
	let ready = $state(false);
	let plotted = $state(0);

	// Leaflet handles pan/zoom/touch/keyboard/attribution itself, so none of that lives here.
	let L: typeof LeafletNS | null = null;
	let map: LeafletNS.Map | null = null;
	let layer: LeafletNS.LayerGroup | null = null;
	let markers: Record<number, LeafletNS.Marker> = {};
	let leaders: Leader[] = [];

	onMount(() => {
		let disposed = false;
		let observer: ResizeObserver | null = null;

		(async () => {
			try {
				const mod = await import('leaflet');
				L = (mod.default ?? mod) as typeof LeafletNS;
			} catch {
				unavailable = true;
				return;
			}
			if (disposed) return;

			map = L.map(container, {
				zoomControl: true,
				attributionControl: true,
				scrollWheelZoom: true,
				worldCopyJump: true,
				minZoom: 1,
				maxZoom: 12
			});

			// Leaflet defers adding layers until the map has a view, so set one up front.
			// fitBounds overrides it as soon as there is at least one usable coordinate.
			map.setView([30, 10], 2);

			L.tileLayer('https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png', {
				attribution: '&copy; OpenStreetMap &middot; &copy; CARTO',
				subdomains: 'abcd',
				maxZoom: 19
			}).addTo(map);

			layer = L.layerGroup().addTo(map);

			// Chips are positioned in screen space, so they need redrawing after every move.
			map.on('zoomend viewreset moveend', () => {
				drawLeaders();
				declash();
			});

			observer = new ResizeObserver(() => map?.invalidateSize());
			observer.observe(container);

			ready = true;
		})();

		return () => {
			disposed = true;
			observer?.disconnect();
			map?.remove();
			map = null;
			layer = null;
			markers = {};
			leaders = [];
		};
	});

	/** Anchor each leader line to the chip it points at, honouring any declash nudge. */
	function drawLeaders() {
		if (!L || !map) return;
		const chips = container.querySelectorAll<HTMLElement>('.wx-pin');
		leaders.forEach(({ line, lat, lon }, i) => {
			const nudge = chips[i] ? parseFloat(chips[i].style.marginTop || '0') : 0;
			const from = map!.latLngToContainerPoint([lat, lon]);
			const to = map!.containerPointToLatLng(L!.point(from.x, from.y + CHIP_OFFSET_Y + nudge));
			line.setLatLngs([[lat, lon], to]);
		});
	}

	/** Safety net: any chips still colliding get pushed apart vertically. */
	function declash() {
		const chips = [...container.querySelectorAll<HTMLElement>('.wx-pin')];
		if (chips.length < 2) return;
		chips.forEach((c) => (c.style.marginTop = ''));

		for (let pass = 0; pass < 3; pass++) {
			const boxes = chips.map((c) => c.getBoundingClientRect());
			let moved = false;
			for (let i = 0; i < chips.length; i++) {
				for (let j = i + 1; j < chips.length; j++) {
					const a = boxes[i];
					const b = boxes[j];
					const overlapX = Math.min(a.right, b.right) - Math.max(a.left, b.left);
					const overlapY = Math.min(a.bottom, b.bottom) - Math.max(a.top, b.top);
					if (overlapX > 0 && overlapY > 0) {
						const lower = a.top <= b.top ? j : i;
						const current = parseFloat(chips[lower].style.marginTop || '0');
						chips[lower].style.marginTop = `${current + overlapY + 5}px`;
						moved = true;
					}
				}
			}
			if (!moved) break;
		}
		drawLeaders();
	}

	function applyHighlight() {
		for (const [locationId, marker] of Object.entries(markers)) {
			const chip = marker.getElement()?.querySelector('.wx-pin');
			chip?.classList.toggle('on', Number(locationId) === highlight);
		}
	}

	// Plot pins whenever the location set changes; fitBounds frames whatever exists.
	$effect(() => {
		const points = locations;
		if (!ready || !L || !map || !layer) return;

		layer.clearLayers();
		markers = {};
		leaders = [];

		const coords: [number, number][] = [];
		for (const location of points) {
			const lat = Number(location.latitude);
			const lon = Number(location.longitude);
			if (!Number.isFinite(lat) || !Number.isFinite(lon)) continue;

			const color = temperatureColor(location.temperature);

			const line = L.polyline(
				[
					[lat, lon],
					[lat, lon]
				],
				{ color: '#233554', weight: 1.5, opacity: 0.45, interactive: false }
			).addTo(layer);
			leaders.push({ line, lat, lon });

			L.circleMarker([lat, lon], {
				radius: 4.5,
				color: '#fff',
				weight: 2,
				fillColor: color,
				fillOpacity: 1,
				interactive: false,
				className: 'wx-dot'
			}).addTo(layer);

			const chip = `<div class="wx-pin" style="background:${color};transform:translate(-50%, calc(-50% + ${CHIP_OFFSET_Y}px))">${location.temperature}°</div>`;
			const marker = L.marker([lat, lon], {
				icon: L.divIcon({
					className: '',
					html: chip,
					// null keeps Leaflet from forcing a fixed box on the chip.
					iconSize: null as unknown as LeafletNS.PointExpression
				}),
				riseOnHover: true
			}).addTo(layer);

			marker.on('mouseover', () => onhighlight?.(location.locationId));
			marker.on('mouseout', () => onhighlight?.(null));

			markers[location.locationId] = marker;
			coords.push([lat, lon]);
		}

		plotted = coords.length;
		if (coords.length) {
			map.fitBounds(L.latLngBounds(coords), { padding: [58, 58], maxZoom: 6 });
		}

		// Chips need a frame to lay out before they can be measured for collisions.
		requestAnimationFrame(() => {
			declash();
			applyHighlight();
		});
	});

	// Keep the map chip in sync with whichever rail row is hovered.
	$effect(() => {
		void highlight;
		if (!ready) return;
		applyHighlight();
	});
</script>

<div class="relative h-full w-full" bind:this={container} data-testid="weather-map">
	{#if unavailable}
		<div
			class="absolute inset-0 flex items-center justify-center font-mono text-[13px] text-[#6b7480]"
		>
			map library unavailable offline
		</div>
	{:else if ready && plotted === 0 && locations.length > 0}
		<div class="pointer-events-none absolute inset-x-0 bottom-3 z-[500] flex justify-center px-3">
			<span
				class="rounded-lg bg-white/90 px-3 py-1.5 text-center text-[13px] text-[#6b7480] shadow-sm"
			>
				No coordinates in this API response — nothing to place on the map.
			</span>
		</div>
	{/if}
</div>

<!-- Leaflet builds these nodes imperatively, so the styles cannot be scoped. -->
<style>
	:global(.wx-pin) {
		position: absolute;
		display: flex;
		align-items: center;
		gap: 6px;
		padding: 5px 10px;
		border-radius: 999px;
		color: #fff;
		font:
			700 13px/1 ui-sans-serif,
			system-ui,
			'Segoe UI',
			sans-serif;
		white-space: nowrap;
		box-shadow: 0 2px 8px rgba(35, 53, 84, 0.3);
		transition: box-shadow 0.16s ease;
	}

	:global(.wx-pin.on) {
		box-shadow: 0 6px 18px rgba(35, 53, 84, 0.42);
		z-index: 400;
	}

	:global(.wx-dot) {
		pointer-events: none;
	}

	:global(.leaflet-container) {
		background: #e7ebf1;
		font-family: ui-sans-serif, system-ui, sans-serif;
	}

	:global(.leaflet-control-attribution) {
		font-size: 10px;
		background: rgba(255, 255, 255, 0.78);
	}
</style>
