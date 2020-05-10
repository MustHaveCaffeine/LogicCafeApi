package io.lc.app.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import io.lc.app.models.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    public User findByUsername(String username);
}