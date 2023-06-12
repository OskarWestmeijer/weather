resource "google_compute_network" "peering_network" {
  name                    = "private-network"
  auto_create_subnetworks = "false"
}

resource "google_sql_database" "weather_database" {
  name     = "weather-database"
  instance = google_sql_database_instance.weather_database.name
}

resource "google_sql_database_instance" "weather_database" {
  name             = "weather-database"
  region           = var.project_region
  database_version = "POSTGRES_15"
  settings {
    tier      = "db-f1-micro"
    disk_size = 10
    ip_configuration {
      ipv4_enabled = false
      private_network = google_compute_network.peering_network.id
    }
  }

  deletion_protection = "true"
}