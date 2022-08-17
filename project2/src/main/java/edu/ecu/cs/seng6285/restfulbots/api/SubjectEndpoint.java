package edu.ecu.cs.seng6285.restfulbots.api;

import edu.ecu.cs.seng6285.restfulbots.datastore.SubjectService;
import edu.ecu.cs.seng6285.restfulbots.models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/subjects")
public class SubjectEndpoint {
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping(value = "/init")
    public boolean initSubjects() {
        // Create some sample subjects
        List<Subject> subjects = new ArrayList<>();

        subjects.add(new Subject.Builder().withSubjectName("Computer Science").build());
        subjects.add(new Subject.Builder().withSubjectName("Mathematics").build());
        subjects.add(new Subject.Builder().withSubjectName("English").build());
        subjects.add(new Subject.Builder().withSubjectName("History").build());

        for (Subject k : subjects){
            subjectService.createSubject(k);
        }
        return true;
    }
}
