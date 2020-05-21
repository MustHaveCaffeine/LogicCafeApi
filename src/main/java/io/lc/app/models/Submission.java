package io.lc.app.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="submissions")
public class Submission {
    public ObjectId id;
    public ObjectId submitterId;
    public ObjectId snippetId;
    public ObjectId problemId;

    public ObjectId getId() {
        return this.id;
    }
}