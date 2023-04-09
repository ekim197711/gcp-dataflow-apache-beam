package com.ursolutions.dataflow.beam.pipeline.directrunner;

import com.ursolutions.dataflow.beam.pipeline.CustomOptions;
import com.ursolutions.dataflow.beam.pipeline.PipelineFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.ValueProvider;

@Slf4j
public class DirectRunnerMain {
    public static void main(String[] args) {
        log.info("Main BEGIN");
        CustomOptions options =
                PipelineOptionsFactory.fromArgs(args).as(CustomOptions.class);
        configureDirectRun(options);

        Pipeline pipeline = new PipelineFactory().build(options);
        log.info("Main Run");
        PipelineResult.State state = pipeline.run().waitUntilFinish();
        log.info("The state ended up being {}", state);
        log.info("Main END");
    }

    private static void configureDirectRun(CustomOptions options) {
        options.setProjectName(ValueProvider.StaticValueProvider.of("mikes-demo"));
        options.setProject("mikes-demo");
        options.setSubscription(ValueProvider.StaticValueProvider.of("projects/mikes-demo/subscriptions/dataflow_directrunner"));
        options.setFirestoreCollection(ValueProvider.StaticValueProvider.of("directrun"));
        options.setTempLocation("gs://mikes-dataflow-template/tempLocation/");
    }


}
