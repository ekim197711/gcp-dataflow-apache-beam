package com.ursolutions.dataflow.beam.pipeline;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.Validation;
import org.apache.beam.sdk.options.ValueProvider;

public interface CustomOptions extends DataflowPipelineOptions {
    @Description("Subscription to retrieve messages from")
    @Validation.Required
    ValueProvider<String> getSubscription();

    void setSubscription(ValueProvider<String> subscription);

    @Description("Project name")
    @Validation.Required
    ValueProvider<String> getProjectName();

    void setProjectName(ValueProvider<String> valueProvider);

    @Description("Firestore collection name")
    @Validation.Required
    ValueProvider<String> getFirestoreCollection();

    void setFirestoreCollection(ValueProvider<String> valueProvider);

}
