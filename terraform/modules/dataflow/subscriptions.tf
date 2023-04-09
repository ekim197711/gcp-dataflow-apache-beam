resource "google_pubsub_topic" "direct_runner" {
  name = "dataflow_directrunner"
}

resource "google_pubsub_subscription" "direct_runner" {
  name  = "dataflow_directrunner"
  topic = google_pubsub_topic.direct_runner.name
}

resource "google_pubsub_topic" "cloud_runner" {
  name = "dataflow_cloudrunner"
}

resource "google_pubsub_subscription" "cloud_runner" {
  name  = "dataflow_cloudrunner"
  topic = google_pubsub_topic.cloud_runner.name
}

