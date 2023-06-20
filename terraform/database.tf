# Create a VPC network
resource "google_compute_network" "peering_network" {
  name                    = "private-network"
  auto_create_subnetworks = "false"
}

# Create an IP address
resource "google_compute_global_address" "private_ip_address" {
  name          = "private-ip-address"
  purpose       = "VPC_PEERING"
  address_type  = "INTERNAL"
  prefix_length = 16
  network       = google_compute_network.peering_network.id
}

# Create a private connection
resource "google_service_networking_connection" "default" {
  network                 = google_compute_network.peering_network.id
  service                 = "servicenetworking.googleapis.com"
  reserved_peering_ranges = [google_compute_global_address.private_ip_address.name]
}

resource "google_compute_network_peering_routes_config" "peering_routes" {
  peering              = google_service_networking_connection.default.peering
  network              = google_compute_network.peering_network.name
  import_custom_routes = true
  export_custom_routes = true
}

resource "google_sql_database" "weather_database" {
  name     = "weather-database"
  instance = google_sql_database_instance.weather_database.name
}

resource "google_sql_database_instance" "weather_database" {
  count            = var.db_alive
  name             = "weather-database"
  region           = var.project_region
  database_version = "POSTGRES_15"

  depends_on = [google_service_networking_connection.default]

  settings {
    tier            = "db-f1-micro"
    disk_size       = 10
    disk_autoresize = false
    ip_configuration {
      ipv4_enabled    = true
      private_network = google_compute_network.peering_network.id
    }
  }

  deletion_protection = false
}

resource "google_sql_user" "weather_api" {
  name     = "weather-api"
  instance = google_sql_database_instance.weather_database.name
  password = random_password.weather_api.result
}

resource "random_password" "weather_api" {
  length  = 32
  special = false
}