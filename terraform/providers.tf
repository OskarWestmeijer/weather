provider "google" {
  project = var.project_id
  region  = var.project_region
  zone    = var.project_zone
}

terraform {
  backend "gcs" {
    bucket = "westmeijer-oskar-tfstate-bucket"
    prefix = "weather-api"
  }
}
