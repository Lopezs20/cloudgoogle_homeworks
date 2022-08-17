package edu.ecu.cs.seng6285.restfulbots.models;

import com.fasterxml.jackson.annotation.JsonRootName;

// Jackson gives the ability to go back and forth between OBJ and JSON text (serialized/deserialized).
@JsonRootName(value = "courses")
public class Course {
    private String courseName;
    private String subject;

    // constants for JSON
    public static final String COURSE_NAME = "courseName";
    public static final String SUBJECT = "subject";

    // build a course 
    public Course(Builder builder) {
        this.courseName = builder.courseName;
        this.subject = builder.subject;
    }

    public static class Builder {
        private String courseName;
        private String subject;

        public Builder withCourseName(String courseName) {
            this.courseName = courseName;
            return this;
        }

        public Builder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }

    public Course() {

    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
