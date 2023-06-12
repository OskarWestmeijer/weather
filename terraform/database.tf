resource "google_sql_database" "weather_database" {
  name     = "weather-database"
  instance = google_sql_database_instance.weather_database.name
}

resource "google_sql_database_instance" "weather_database" {
  name             = "weather-database"
  region           = var.project_region
  database_version = "POSTGRES_15"
  settings {
    tier = "db-f1-micro"
  }

  deletion_protection = "true"
}