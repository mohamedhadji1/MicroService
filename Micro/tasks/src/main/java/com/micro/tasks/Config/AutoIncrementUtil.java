package com.micro.tasks.Config;
import com.micro.tasks.Entities.SequenceCounter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class AutoIncrementUtil {

    private final MongoTemplate mongoTemplate;

    public AutoIncrementUtil(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public int getNextSequence(String sequenceName) {
        Query query = new Query(Criteria.where("_id").is(sequenceName));
        Update update = new Update().inc("seq", 1);
        SequenceCounter counter = mongoTemplate.findAndModify(query, update, SequenceCounter.class);
        if (counter == null) {
            counter = new SequenceCounter(sequenceName, 1);
            mongoTemplate.save(counter);
            return 1;
        }
        return counter.getSeq();
    }
}