package com.ursolutions.dataflow;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import org.junit.Test;

import java.io.IOException;

public class PlaceMessageOnTopicForCloudRunner {
    private static String PROJECT_ID = "mikes-demo";
    private static String TOPIC_ID = "dataflow_cloudrunner";

    private static String[] PAYLOADS_TO_PLACE_ON_TOPIC = {Payloads.MIKES_PAYLOAD1};

    @Test
    public void dumpMessageToTopic() throws IOException {
        ProjectTopicName topicName =
                ProjectTopicName.newBuilder()
                        .setProject(PROJECT_ID)
                        .setTopic(TOPIC_ID)
                        .build();
        Publisher publisher = Publisher.newBuilder(topicName).build();
        for (String payload : PAYLOADS_TO_PLACE_ON_TOPIC) {
            PubsubMessage pubsubMessage =
                    PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(payload)).build();
            publisher.publish(pubsubMessage);
        }
        publisher.shutdown();
    }
}
