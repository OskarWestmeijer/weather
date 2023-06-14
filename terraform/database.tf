resource "google_compute_network" "peering_network" {
  name                    = "private-network"
  auto_create_subnetworks = "false"
}

resource "google_compute_global_address" "private_ip_address" {
  name          = "private-ip-address"
  purpose       = "VPC_PEERING"
  address_type  = "INTERNAL"
  prefix_length = 16
  network       = google_compute_network.peering_network.id
}

resource "google_service_networking_connection" "default" {
  network                 = google_compute_network.peering_network.id
  service                 = "servicenetworking.googleapis.com"
  reserved_peering_ranges = [google_compute_global_address.private_ip_address.name]
}

resource "google_sql_database" "weather_database" {
  name     = "weather-database"
  instance = google_sql_database_instance.weather_database.name
}

resource "google_sql_database_instance" "weather_database" {
  name             = "weather-database"
  region           = var.project_region
  database_version = "POSTGRES_15"

  depends_on = [google_service_networking_connection.default]

  settings {
    tier      = "db-f1-micro"
    disk_size = 10
    disk_autoresize = false
    ip_configuration {
      ipv4_enabled = false
      private_network = google_compute_network.peering_network.id
    }
  }

  deletion_protection = "true"
}

resource "google_secret_manager_secret" "dev_user_password" {
  secret_id = "dev-user-password"
}

resource "google_sql_user" "dev_user" {
  name     = "developer"
  instance = google_sql_database_instance.weather_database.name
  password = google_secret_manager_secret.dev_user_password
}