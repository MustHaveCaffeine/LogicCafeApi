package io.lc.app.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import io.lc.app.models.Submission;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubmissionRepository extends MongoRepository<Submission, ObjectId> {
    List<Submission> findByProblemId(ObjectId problemId);
}