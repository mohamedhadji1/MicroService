package com.micro.tasks.Services;

import com.micro.tasks.Entities.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {
    @Autowired
    private MongoOperations mongoOperations;

    public int generateSequence(String seqName) {
        Query query = new Query(Criteria.where("_id").is(seqName));
        Update update = new Update().inc("seq", 1);
        Sequence counter = mongoOperations.findAndModify(query, update, Sequence.class);
        if (counter == null) {
            counter = new Sequence();
            counter.setId(seqName);
            counter.setSeq(1);
            mongoOperations.save(counter);
            return 1;
        }
        return counter.getSeq();
    }
}