<script lang="ts">
	import './layout.css';
	let { children } = $props();
</script>

<svelte:head>
	<link rel="icon" href="/favicon.ico" type="image/x-icon" />
	<title>Weather</title>
</svelte:head>

<div class="flex min-h-screen flex-col bg-base-100">
	<!-- NAVIGATION -->
	<div class="navbar bg-secondary text-white shadow-sm">
		<!-- START -->
		<div class="navbar-start">
			<!-- Mobile hamburger -->
			<div class="dropdown lg:hidden">
				<div tabindex="0" role="button" class="btn btn-ghost btn-circle" aria-label="Open menu">
					☰
				</div>

				<ul
					class="menu menu-lg dropdown-content bg-base-100 rounded-box z-50 mt-3 w-56 p-2 shadow text-black"
				>
					<li><a href="/">📍 Overview</a></li>
					<li><a href="/details">📈 Details</a></li>
					<li><a href="https://api.weather.oskar-westmeijer.com" target="_blank">🚀 API-doc</a></li>
					<li>
						<a href="https://github.com/OskarWestmeijer/weather" target="_blank">
							<img alt="Github logo" class="size-4 inline" src="/github.svg" /> Project on Github
						</a>
					</li>
				</ul>
			</div>

			<!-- Desktop brand -->
			<div class="hidden lg:flex">
				<a
					href="/"
					class="btn btn-ghost rounded-lg text-lg font-bold"
				>
					⛅ Weather
				</a>
			</div>
		</div>

		<!-- CENTER -->
		<div class="navbar-center">
			<!-- Mobile brand -->
			<a
				href="/"
				class="btn btn-ghost rounded-lg text-lg font-bold lg:hidden"
			>
				⛅ Weather
			</a>

			<!-- Desktop links -->
			<div class="hidden lg:flex gap-2">
				<a href="/" class="btn btn-ghost text-lg">📍 Overview</a>
				<a href="/details" class="btn btn-ghost text-lg">📈 Details</a>
			</div>
		</div>

		<!-- END -->
		<div class="navbar-end hidden lg:flex gap-2">
			<a href="https://api.weather.oskar-westmeijer.com" target="_blank" class="btn rounded-lg"
				>🚀 API-doc</a
			>

			<a href="https://github.com/OskarWestmeijer/weather" target="_blank">
				<button class="btn rounded-lg">
					<img alt="Github logo" class="size-6" src="/github.svg" />
				</button>
			</a>
		</div>
	</div>

	<!-- MAIN CONTENT -->
	<main class="flex-grow">
		{@render children()}
	</main>

	<!-- FOOTER -->
	<footer class="footer-center footer py-6 mt-auto">
		<a href="/" class="text-base">
			Created by Oskar Westmeijer 🐨 2025
		</a>
	</footer>
</div>
