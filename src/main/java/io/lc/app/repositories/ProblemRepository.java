package io.lc.app.repositories;

import org.bson.types.ObjectId;
import io.lc.app.models.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProblemRepository extends MongoRepository<Problem, ObjectId> {
    public Problem findBySlug(String slug);
}