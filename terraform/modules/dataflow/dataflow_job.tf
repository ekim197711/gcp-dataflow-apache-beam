resource "google_project_iam_member" "roles-for-the-pipeline-service-account" {
  for_each = var.roles_for_compute_service_account
  project  = "${var.project_name}"
  role     = each.key
  member   = "serviceAccount:${var.compute-service-account}"
}
resource "google_compute_network" "vpc_network_default" {
  name        = "default"
  description = "Default network for the project"
}
resource "google_compute_firewall" "default" {
  name    = "default-firewall"
  network = google_compute_network.vpc_network_default.name

  allow {
    protocol = "icmp"
  }

  allow {
    protocol = "tcp"
    ports    = ["80", "8080", "1000-2000", "12345-12346"]
  }

  source_tags = ["web", "dataflow", "compute"]
}
resource "google_storage_bucket" "dataflow-template-and-temporary-files" {
  name                        = "mikes-dataflow-template"
  location                    = var.location
  force_destroy               = true
  public_access_prevention    = "enforced"
  uniform_bucket_level_access = true
  project                     = var.project_name
  storage_class               = "STANDARD"
  versioning {
    enabled = false
  }
}
resource "random_id" "random" {
  byte_length = 8
  keepers     = {
    uuid = uuid()
  }
}

resource "google_dataflow_job" "mikes_incoming" {
  name                    = "mikes-dataflow-${random_id.random.id}"
  template_gcs_path       = "${google_storage_bucket.dataflow-template-and-temporary-files.url}/templates/mikes-dataflow"
  temp_gcs_location       = "${google_storage_bucket.dataflow-template-and-temporary-files.url}/temporary/mikes-dataflow"
  enable_streaming_engine = true
  on_delete               = "cancel"

  parameters = {
    subscription        = var.subscription
    project             = "mikes-demo"
    projectName         = "mikes-demo"
    firestoreCollection = "cloudrun"
    tempLocation        = "gs://mikes-dataflow-template/tempLocation/"

    #    Firestore Data

  }
}


