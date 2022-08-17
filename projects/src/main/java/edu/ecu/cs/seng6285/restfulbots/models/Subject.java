package edu.ecu.cs.seng6285.restfulbots.models;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("subjects")
public class Subject {
    private long id;
    private String subjectName;

    public static final String ID = "id";
    public static final String SUBJECT_NAME = "subjectName";

    private Subject(Builder builder) {
        this.id = builder.id;
        this.subjectName = builder.subjectName;
    }

    public static class Builder {
        private long id;
        private String subjectName;

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withSubjectName(String subjectName) {
            this.subjectName = subjectName;
            return this;
        }

        public Subject build() {
            return new Subject(this);
        }
    }

    public Subject() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id='" + id + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}
