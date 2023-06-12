variable "project_id" {
  description = "Name / Id of my GCP project"
  type        = string
  default     = "westmeijer-oskar"
}

variable "project_region" {
  description = "Default region"
  type        = string
  default     = "europe-west3"
}

variable "project_zone" {
  description = "Default zone"
  type        = string
  default     = "europe-west3-a"
}

variable "service_image" {
  description = "Image to be deployed"
  default     = "organization/name:tag"
}

variable "service_name" {
  description = "Service name"
  default     = "weather-api"
}