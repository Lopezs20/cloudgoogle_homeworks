package edu.ecu.cs.seng6285.restfulbots.pubsub;

import java.util.ArrayList;
import java.util.List;

public class Topics {
    // TODO: Change this to your own Project ID!
    public static final String PROJECT_ID = "project-01-339316";

    public static final String COURSE_CREATED = "course-created";
    public static final String COURSE_UPDATED = "course-updated";
    public static final String COURSE_DELETED = "course-deleted";

    public static final String SUBJECT_CREATED = "subject-created";
    public static final String SUBJECT_UPDATED = "subject-updated";
    public static final String SUBJECT_DELETED = "subject-deleted";

    public static final String TEXTBOOK_CREATED = "textbook-created";
    public static final String TEXTBOOK_UPDATED = "textbook-updated";
    public static final String TEXTBOOK_DELETED = "textbook-deleted";

    public static List<String> getTopicNames() {
        List<String> topicNames = new ArrayList<>();
        topicNames.add(Topics.COURSE_CREATED);
        topicNames.add(Topics.COURSE_UPDATED);
        topicNames.add(Topics.COURSE_DELETED);
        topicNames.add(Topics.SUBJECT_CREATED);
        topicNames.add(Topics.SUBJECT_UPDATED);
        topicNames.add(Topics.SUBJECT_DELETED);
        topicNames.add(Topics.TEXTBOOK_CREATED);
        topicNames.add(Topics.TEXTBOOK_UPDATED);
        topicNames.add(Topics.TEXTBOOK_DELETED);
        return topicNames;
    }
}
