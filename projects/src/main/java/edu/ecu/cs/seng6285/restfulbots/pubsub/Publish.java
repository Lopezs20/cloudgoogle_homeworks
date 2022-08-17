package edu.ecu.cs.seng6285.restfulbots.pubsub;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Publish {
    private static String logClass = "Publish";
    private Logger logger = LoggerFactory.getLogger(logClass);

    private String topicId;
    private String projectId;
    private long entityId;

    private Publish() {
        // Empty constructor to force use of builder...
    }

    private Publish(Builder builder) {
        this.topicId = builder.topicId;
        this.projectId = builder.projectId;
        this.entityId = builder.entityId;
    }

    public static class Builder {
        private String topicId;
        private String projectId;
        private long entityId;

        public Builder forProject(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder toTopic(String topicId) {
            this.topicId = topicId;
            return this;
        }

        public Builder sendId(long entityId) {
            this.entityId = entityId;
            return this;
        }

        public Publish build() {
            return new Publish(this);
        }
    }

    public void publish() {
        Publisher publisher = null;
        try {
            TopicName topicName = TopicName.of(this.projectId, this.topicId);
            publisher = Publisher.newBuilder(topicName).build();

            String idAsString = Long.toString(entityId);
            ByteString data = ByteString.copyFromUtf8(idAsString);
            PubsubMessage message = PubsubMessage.newBuilder().setData(data).build();

            ApiFuture<String> messageIdFuture = publisher.publish(message);
            String messageId = messageIdFuture.get();
            logger.info(String.format("Published message %s to topic %s", messageId, this.topicId));
        } catch (IOException | ExecutionException | InterruptedException e) {
            logger.info(String.format("An error occurred: %s", e.toString()));
        } finally {
            if (publisher != null) {
                publisher.shutdown();
                try {
                    publisher.awaitTermination(1, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    logger.info(String.format("An error occurred: %s", e.toString()));
                }
            }
        }
    }
}
