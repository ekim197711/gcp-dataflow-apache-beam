package com.ursolutions.dataflow.beam.pipeline.step.firestore;

import com.google.firestore.v1.Document;
import com.google.firestore.v1.Value;
import com.google.firestore.v1.Write;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.options.ValueProvider;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.windowing.BoundedWindow;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class CreateDocument extends DoFn<HashMap, Write> implements Serializable {
    private final ValueProvider<String> project;
    private final ValueProvider<String> firestoreDatabase;
    private final ValueProvider<String> firestoreCollection;

    @ProcessElement
    public void apply(@Element HashMap element, ProcessContext c, BoundedWindow window) {
        String name = "projects/" + project.get() + "/databases/" + firestoreDatabase.get()
                + "/documents/" + firestoreCollection.get() + "/" + UUID.randomUUID();
        log.info("Document name set to: {}", name);

        Document.Builder builder = Document.newBuilder()
                .setName(name);

        for (Object key : element.keySet()) {
            if (element.get(key) == null) {
                log.warn("This key {} had a null value.", key);
            } else {
                builder = builder.putFields(key.toString(), Value.newBuilder().setStringValue(element.get(key).toString()).build());
            }
        }
        Document document = builder.build();
        Write build = Write.newBuilder().setUpdate(document).build();
        c.output(build);
    }
}