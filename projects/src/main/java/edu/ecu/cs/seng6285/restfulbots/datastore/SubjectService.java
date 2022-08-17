package edu.ecu.cs.seng6285.restfulbots.datastore;

import com.google.cloud.datastore.*;
import edu.ecu.cs.seng6285.restfulbots.models.Subject;
import edu.ecu.cs.seng6285.restfulbots.pubsub.Publish;
import edu.ecu.cs.seng6285.restfulbots.pubsub.Topics;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SubjectService {
    private Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    private static final String ENTITY_KIND = "Subject";
    private final KeyFactory keyFactory = datastore.newKeyFactory().setKind(ENTITY_KIND);

    public Key createSubject(Subject subject) {
        Key key = datastore.allocateId(keyFactory.newKey());
        Entity subjectEntity = Entity.newBuilder(key)
                .set(Subject.SUBJECT_NAME, subject.getSubjectName())
                .build();
        datastore.put(subjectEntity);

        new Publish
                .Builder()
                .forProject(Topics.PROJECT_ID)
                .toTopic(Topics.SUBJECT_CREATED)
                .sendId(key.getId())
                .build()
                .publish();

        return key;
    }

    public List<Subject> getAllSubjects() {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(ENTITY_KIND)
                .build();
        Iterator<Entity> entities = datastore.run(query);
        return buildSubjects(entities);
    }

    public Subject getSubject(long subjectId) {
        Entity subjectEntity = datastore.get(keyFactory.newKey(subjectId));
        return entityToSubject(subjectEntity);
    }

    public void deleteSubject(long subjectId) {
        datastore.delete(keyFactory.newKey(subjectId));

        new Publish
                .Builder()
                .forProject(Topics.PROJECT_ID)
                .toTopic(Topics.SUBJECT_CREATED)
                .sendId(subjectId)
                .build()
                .publish();
    }

    public void updateSubject(Subject subject) {
        Entity subjectEntity = Entity
                .newBuilder(datastore.get(keyFactory.newKey(subject.getId())))
                .set(Subject.SUBJECT_NAME, subject.getSubjectName())
                .build();
            datastore.update(subjectEntity);

            new Publish
            .Builder()
            .forProject(Topics.PROJECT_ID)
            .toTopic(Topics.SUBJECT_CREATED)
            .sendId(subject.getId())
            .build()
            .publish();
    }

    private List<Subject> buildSubjects(Iterator<Entity> entities) {
        List<Subject> subjects = new ArrayList<>();
        entities.forEachRemaining(entity -> subjects.add(entityToSubject(entity)));
        return subjects;
    }

    private Subject entityToSubject(Entity entity) {
        return new Subject.Builder()
                .withId(entity.getKey().getId())
                .withSubjectName(entity.getString(Subject.SUBJECT_NAME))
                .build();
    }

}
