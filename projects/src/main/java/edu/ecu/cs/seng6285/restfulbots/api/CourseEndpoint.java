package edu.ecu.cs.seng6285.restfulbots.api;

import com.google.cloud.datastore.Key;
import edu.ecu.cs.seng6285.restfulbots.datastore.CourseService;
import edu.ecu.cs.seng6285.restfulbots.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/courses")
public class CourseEndpoint {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(value = "{courseId}")
    public Course getCourse(@PathVariable long courseId) {
        return courseService.getCourse(courseId);
    }

    @DeleteMapping(value = "{courseId}")
    public void deleteCourse(@PathVariable long courseId) {
        courseService.deleteCourse(courseId);
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        Key key = courseService.createCourse(course);
        course.setId(key.getId());
        return course;
    }
    
    @PatchMapping(value = "{courseId}")
    public Course updateCourse(@RequestBody Course course, @PathVariable long courseId) {
        course.setId(courseId);
        courseService.updateCourse(course);
        return course;
    }

    @GetMapping(value = "/init")
    public boolean initCourses() {
        // Create some sample courses
        List<Course> courses = new ArrayList<>();

        courses.add(new Course.Builder()
                .withCourseName("Operating System")
                .withSubject("Computer Science")
                .build()
        );
        courses.add(new Course.Builder()
                .withCourseName("Programming Languages")
                .withSubject("Computer Science")
                .build()
        );
        courses.add(new Course.Builder()
                .withCourseName("Statistics")
                .withSubject("Mathematics")
                .build()
        );
        courses.add(new Course.Builder()
                .withCourseName("English Composition")
                .withSubject("English")
                .build()
        );
        courses.add(new Course.Builder()
                .withCourseName("Poems and Sonnets")
                .withSubject("English")
                .build()
        );

        for (Course c : courses) {
            Key key = courseService.createCourse(c);
            c.setId(key.getId());
        }

        return true;
    }
}
