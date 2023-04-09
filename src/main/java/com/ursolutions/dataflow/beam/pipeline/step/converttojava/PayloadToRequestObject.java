package com.ursolutions.dataflow.beam.pipeline.step.converttojava;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.DoFn;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

@Slf4j
@NoArgsConstructor
public class PayloadToRequestObject extends DoFn<PubsubMessage, HashMap> implements Serializable {
    @ProcessElement
    public void apply(@Element PubsubMessage inputMessage, ProcessContext processContext) {
        if (inputMessage == null) {
            log.warn("This message is null so don't handle it");
            return;
        } else if (inputMessage.getPayload().length == 0) {
            log.warn("Payload was empty... {}", inputMessage.getMessageId());
            return;
        }
        String jsonInput = new String(inputMessage.getPayload());
        log.info("Convert this into Java Object ->{}<-", jsonInput);
        HashMap enrichedFirestore = null;
        try {
            enrichedFirestore = new ObjectMapper().readValue(jsonInput, HashMap.class);
            log.info("Add payload for continued firestore processing!");
            processContext.output(enrichedFirestore);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}