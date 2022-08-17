package edu.ecu.cs.seng6285.restfulbots.models;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "courses")
public class Course {
    private long id;
    private String courseName;
    private String subject;

    public static final String ID = "id";
    public static final String COURSE_NAME = "courseName";
    public static final String SUBJECT = "subject";

    public Course(Builder builder) {
        this.id = builder.id;
        this.courseName = builder.courseName;
        this.subject = builder.subject;
    }

    public static class Builder {
        private long id;
        private String courseName;
        private String subject;

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", courseName='" + courseName + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
