terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = ">=4.50.0"
    }
  }
  backend "gcs" {
    bucket = "mikes-terraform-states"
    prefix = "dataflow"
  }
}
provider "random" {

}
provider "google" {
  project = local.project_name
  region  = local.location
}


