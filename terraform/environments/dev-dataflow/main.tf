module "dataflow" {
  source                  = "../../modules/dataflow"
  project_name            = local.project_name
  location                = local.location
  compute-service-account = "544918740170-compute@developer.gserviceaccount.com"
  firestoreCollection     = "mike"
  subscription            = "projects/mikes-demo/subscriptions/dataflow_cloudrunner"
}