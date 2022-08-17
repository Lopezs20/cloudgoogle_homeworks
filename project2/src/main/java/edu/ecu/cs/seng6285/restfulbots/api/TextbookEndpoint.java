package edu.ecu.cs.seng6285.restfulbots.api;

import edu.ecu.cs.seng6285.restfulbots.datastore.TextbookService;
import edu.ecu.cs.seng6285.restfulbots.models.Textbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/textbooks")
public class TextbookEndpoint {
    @Autowired
    private TextbookService textbookService;

    // If textbook query parameters are a specified subject.
    @GetMapping
    public List<Textbook> getAllTextbooks(@RequestParam("subject") Optional<String> subject) {
        // If you aren't familiar with Java's Optional type, you can see here how it is
        // used -- in this case, so we know if we received a parameter as part of this call.
        if (subject.isPresent()) {
            return textbookService.getAllTextbooksForSubject(subject.get());
        }
        return textbookService.getAllTextbooks();
    }

    @GetMapping(value = "/init")
    public boolean initTextbooks() {
        // Create some sample textbooks
        List<Textbook> textbooks = new ArrayList<>();

        textbooks.add(new Textbook.Builder()
                .withTitle("Operating System Concepts")
                .withAuthors("Abraham Silberschatz, Greg Gagne, Peter B. Galvin")
                .withSubject("Computer Science")
                .withPublisher("Wiley")
                .withYear(2018)
                .build());

        textbooks.add(new Textbook.Builder()
                .withTitle("Programming Language Pragmatics")
                .withAuthors("Michael Scott")
                .withSubject("Computer Science")
                .withPublisher("Elsevier")
                .withYear(2015)
                .build());

        textbooks.add(new Textbook.Builder()
                .withTitle("Concepts of Programming Languages")
                .withAuthors("Robert W. Sebesta")
                .withSubject("Computer Science")
                .withPublisher("Pearson")
                .withYear(2016)
                .build());

        textbooks.add(new Textbook.Builder()
                .withTitle("Programming Language Pragmatics")
                .withAuthors("Michael Scott")
                .withSubject("Computer Science")
                .withPublisher("Elsevier")
                .withYear(2015)
                .build());

        textbooks.add(new Textbook.Builder()
                .withTitle("An Introduction to Statistical Learning: with Applications in R")
                .withAuthors("Gareth James, Daniela Witten, Trevor Hastie, and Robert Tibshirani")
                .withSubject("Mathematics")
                .withPublisher("Springer")
                .withYear(2017)
                .build());

        for (Textbook t : textbooks) {
            textbookService.createTextbook(t);
        }

        return true;
    }
}
