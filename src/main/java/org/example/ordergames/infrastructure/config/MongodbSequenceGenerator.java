package org.example.ordergames.infrastructure.config;

import org.example.ordergames.infrastructure.model.DatabaseSequence;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class MongodbSequenceGenerator {

    private final MongoTemplate mongoTemplate;

    public MongodbSequenceGenerator(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public long generateSequence(final String seqName) {
        final DatabaseSequence counter = mongoTemplate.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
