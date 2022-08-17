package edu.ecu.cs.seng6285.restfulbots.datastore;

import com.google.cloud.datastore.*;
import edu.ecu.cs.seng6285.restfulbots.models.Subject;
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
        return key;
    }

    public List<Subject> getAllSubjects() {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(ENTITY_KIND)
                .build();
        Iterator<Entity> entities = datastore.run(query);
        return buildSubjects(entities);
    }

    private List<Subject> buildSubjects(Iterator<Entity> entities) {
        List<Subject> subjects = new ArrayList<Subject>();
        entities.forEachRemaining(entity -> subjects.add(entityToCourse(entity)));
        return subjects;
    }

    private Subject entityToCourse(Entity entity) {
        return new Subject.Builder()
                .withSubjectName(entity.getString(Subject.SUBJECT_NAME))
                .build();
    }

}
