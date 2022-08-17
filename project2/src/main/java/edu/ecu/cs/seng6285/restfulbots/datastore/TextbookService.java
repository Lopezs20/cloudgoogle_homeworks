package edu.ecu.cs.seng6285.restfulbots.datastore;

import com.google.cloud.datastore.*;
import edu.ecu.cs.seng6285.restfulbots.models.Textbook;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TextbookService {
    private Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    private static final String ENTITY_KIND = "Textbook";
    private final KeyFactory keyFactory = datastore.newKeyFactory().setKind(ENTITY_KIND);

    public Key createTextbook(Textbook textbook) {
        Key key = datastore.allocateId(keyFactory.newKey());
        Entity textbookEntity = Entity.newBuilder(key)
                .set(Textbook.TITLE, textbook.getTitle())
                .set(Textbook.AUTHORS, textbook.getAuthors())
                .set(Textbook.SUBJECT, textbook.getSubject())
                .set(Textbook.PUBLISHER, textbook.getPublisher())
                .set(Textbook.YEAR, textbook.getYear())
                .build();
        datastore.put(textbookEntity);
        return key;
    }

    public List<Textbook> getAllTextbooks() {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(ENTITY_KIND)
                .build();
        Iterator<Entity> entities = datastore.run(query);
        return buildTextbooks(entities);
    }

    public List<Textbook> getAllTextbooksForSubject(String subject) {
        // HINT: Look at the lab we did on Datastore, we need to filter our results. You need
        // to do this filtering in the query, you CANNOT just grab everything and then filter
        // it here using Java code.

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(ENTITY_KIND)
                .setFilter(StructuredQuery.PropertyFilter.eq(Textbook.SUBJECT, subject))
                .build();
        Iterator<Entity> entities = datastore.run(query);
        return buildTextbooks(entities);
    }

    private List<Textbook> buildTextbooks(Iterator<Entity> entities) {
        List<Textbook> textbooks = new ArrayList<>();
        entities.forEachRemaining(entity -> textbooks.add(entityToTextbook(entity)));
        return textbooks;
    }

    private Textbook entityToTextbook(Entity entity) {
        return new Textbook.Builder()
                .withTitle(entity.getString(Textbook.TITLE))
                .withAuthors(entity.getString(Textbook.AUTHORS))
                .withSubject(entity.getString(Textbook.SUBJECT))
                .withPublisher(entity.getString(Textbook.PUBLISHER))
                .withYear(entity.getLong(Textbook.YEAR))
                .build();
    }

}
