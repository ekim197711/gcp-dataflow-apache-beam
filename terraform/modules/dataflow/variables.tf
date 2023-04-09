variable "project_name" {
  type = string
}

variable "subscription" {
  type = string
}
variable "firestoreCollection" {
  type = string
}
variable "location" {
  type = string
}
variable "enable_services" {
  type    = set(string)
  default = ["datastore.googleapis.com", "bigquery.googleapis.com"]
}
variable "compute-service-account" {
  type = string
}

variable "roles_for_compute_service_account" {
  type    = set(string)
  default = [
    "roles/dataflow.developer",
    "roles/dataflow.admin",
    "roles/dataflow.worker",
    "roles/pubsub.admin",
    "roles/firebasestorage.admin",
    "roles/firebase.admin"
  ]
}
