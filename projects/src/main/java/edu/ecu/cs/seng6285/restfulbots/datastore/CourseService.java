package edu.ecu.cs.seng6285.restfulbots.datastore;

import com.google.cloud.datastore.*;
import edu.ecu.cs.seng6285.restfulbots.models.Course;
import edu.ecu.cs.seng6285.restfulbots.pubsub.Publish;
import edu.ecu.cs.seng6285.restfulbots.pubsub.Topics;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CourseService {
    private Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    private static final String ENTITY_KIND = "Course";
    private final KeyFactory keyFactory = datastore.newKeyFactory().setKind(ENTITY_KIND);

    public Key createCourse(Course course) {
        Key key = datastore.allocateId(keyFactory.newKey());
        Entity courseEntity = Entity.newBuilder(key)
                .set(Course.COURSE_NAME, course.getCourseName())
                .set(Course.SUBJECT, course.getSubject())
                .build();
        datastore.put(courseEntity);

        new Publish
                .Builder()
                .forProject(Topics.PROJECT_ID)
                .toTopic(Topics.COURSE_CREATED)
                .sendId(key.getId())
                .build()
                .publish();

        return key;
    }

    public List<Course> getAllCourses() {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(ENTITY_KIND)
                .build();
        Iterator<Entity> entities = datastore.run(query);
        return buildCourses(entities);
    }

    public Course getCourse(long courseId) {
        Entity courseEntity = datastore.get(keyFactory.newKey(courseId));
        return entityToCourse(courseEntity);
    }

    public void deleteCourse(long courseId) {
        datastore.delete(keyFactory.newKey(courseId));

        new Publish
                .Builder()
                .forProject(Topics.PROJECT_ID)
                .toTopic(Topics.COURSE_DELETED)
                .sendId(courseId)
                .build()
                .publish();
    }

    public void updateCourse(Course course) {
        Entity courseEntity = Entity
                .newBuilder(datastore.get(keyFactory.newKey(course.getId())))
                .set(Course.COURSE_NAME, course.getCourseName())
                .set(Course.SUBJECT, course.getSubject())
                .build();
        datastore.update(courseEntity);

        new Publish
                .Builder()
                .forProject(Topics.PROJECT_ID)
                .toTopic(Topics.COURSE_UPDATED)
                .sendId(course.getId())
                .build()
                .publish();
    }

    private List<Course> buildCourses(Iterator<Entity> entities) {
        List<Course> courses = new ArrayList<>();
        entities.forEachRemaining(entity -> courses.add(entityToCourse(entity)));
        return courses;
    }

    private Course entityToCourse(Entity entity) {
        return new Course.Builder()
                .withId(entity.getKey().getId())
                .withCourseName(entity.getString(Course.COURSE_NAME))
                .withSubject(entity.getString(Course.SUBJECT))
                .build();
    }
}
