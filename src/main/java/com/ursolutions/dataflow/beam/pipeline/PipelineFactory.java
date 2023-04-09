package com.ursolutions.dataflow.beam.pipeline;

import com.ursolutions.dataflow.beam.pipeline.step.converttojava.PayloadToRequestObject;
import com.ursolutions.dataflow.beam.pipeline.step.firestore.CreateDocument;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.firestore.FirestoreIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.ValueProvider;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;
import java.util.HashMap;


@Slf4j
@NoArgsConstructor
public class PipelineFactory implements Serializable {
    public Pipeline build(CustomOptions options) {
        Pipeline p = Pipeline.create(options);
        PCollection<HashMap> hashMapPCollection = pickupIncomingMessagesFromTopicAndConvertToHashMap(options.getSubscription(), p);
        incomingRequestsToFirestore(options.getProjectName(), options.getFirestoreCollection(),
                hashMapPCollection);
        return p;
    }

    private PCollection<HashMap> pickupIncomingMessagesFromTopicAndConvertToHashMap(
            ValueProvider<String> subscription, Pipeline p) {
        return p.apply("topic_messages",
                        PubsubIO
                                .readMessagesWithMessageId()
                                .fromSubscription(subscription)
                )
                .apply("enriched_java_object",
                        ParDo.of(new PayloadToRequestObject())
                );
    }

    private void incomingRequestsToFirestore(
            ValueProvider<String> project,
            ValueProvider<String> firestoreCollection, PCollection<HashMap> pipeline) {
        pipeline
                .apply("firestore_write_document",
                        ParDo.of(new CreateDocument(project,
                                ValueProvider.StaticValueProvider.of("(default)"),
                                firestoreCollection)))
                .apply("persist_in_firestore", FirestoreIO.v1().write().batchWrite().build())
        ;
    }


}
