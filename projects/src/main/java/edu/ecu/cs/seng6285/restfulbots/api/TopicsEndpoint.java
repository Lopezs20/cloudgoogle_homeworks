package edu.ecu.cs.seng6285.restfulbots.api;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.*;
import edu.ecu.cs.seng6285.restfulbots.pubsub.Topics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/topics")
public class TopicsEndpoint {

    @GetMapping(value = "/setup")
    private void initTopics() throws IOException {
        try (TopicAdminClient topicAdminClient = TopicAdminClient.create();
             SubscriptionAdminClient subAdminClient = SubscriptionAdminClient.create()) {

            List<TopicName> topics = new ArrayList<>();
            for (String topicName : Topics.getTopicNames()) {
                topics.add(TopicName.of(Topics.PROJECT_ID, topicName));
            }

            for (TopicName tn : topics) {
                topicAdminClient.createTopic(tn);
                subAdminClient.createSubscription(
                        ProjectSubscriptionName.of(tn.getProject(), tn.getTopic() + "-sub"),
                        tn,
                        PushConfig.getDefaultInstance(),
                        10);
            }
        }
    }
}
