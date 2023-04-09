package com.ursolutions.dataflow.beam.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

@Slf4j
public class CloudRunnerMain {
    public static void main(String[] args) {
        log.info("Main BEGIN");
        CustomOptions initialOptions =
                PipelineOptionsFactory.fromArgs(args).withValidation().as(CustomOptions.class);
        initialOptions.setRunner(DataflowRunner.class);
        Pipeline pipeline = new PipelineFactory().build(initialOptions);
        log.info("Main Run");
        pipeline.run();
        log.info("Main END");
    }


}
