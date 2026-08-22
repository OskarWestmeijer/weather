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
				<div
					tabindex="0"
					role="button"
					class="btn btn-circle btn-ghost text-white hover:border-transparent hover:bg-white/10 hover:text-white"
					aria-label="Open menu"
				>
					☰
				</div>

				<ul
					class="menu dropdown-content z-50 mt-3 w-56 menu-lg rounded-box bg-base-100 p-2 text-black shadow"
				>
					<li><a href="/">📍 Overview</a></li>
					<li><a href="/details">📈 Details</a></li>
					<li><a href="https://api.weather.oskar-westmeijer.com" target="_blank">🚀 API-doc</a></li>
					<li>
						<a href="https://github.com/OskarWestmeijer/weather" target="_blank">
							<img alt="Github logo" class="inline size-4" src="/github.svg" /> Project on Github
						</a>
					</li>
				</ul>
			</div>

			<!-- Desktop brand -->
			<div class="hidden lg:flex">
				<a
					href="/"
					class="btn rounded-lg btn-ghost text-lg font-bold text-white hover:border-transparent hover:bg-white/10 hover:text-white"
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
				class="btn rounded-lg btn-ghost text-lg font-bold text-white hover:border-transparent hover:bg-white/10 hover:text-white lg:hidden"
			>
				⛅ Weather
			</a>

			<!-- Desktop links -->
			<div class="hidden gap-2 lg:flex">
				<a
					href="/"
					class="btn btn-ghost text-lg text-white hover:border-transparent hover:bg-white/10 hover:text-white"
					>📍 Overview</a
				>
				<a
					href="/details"
					class="btn btn-ghost text-lg text-white hover:border-transparent hover:bg-white/10 hover:text-white"
					>📈 Details</a
				>
			</div>
		</div>

		<!-- END -->
		<div class="navbar-end hidden gap-2 lg:flex">
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
	<main class="flex min-h-0 flex-grow flex-col">
		{@render children()}
	</main>

	<!-- FOOTER -->
	<footer class="mt-auto footer footer-center py-6">
		<a href="https://oskar-westmeijer.com" target="_blank" class="text-base">
			Created by Oskar Westmeijer 🐨 2026
		</a>
	</footer>
</div>
