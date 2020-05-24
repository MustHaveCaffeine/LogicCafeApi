package io.lc.app.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Document(collection="submissions")
public class Submission {
    @Getter
    public ObjectId id;
    public ObjectId submitterId;
    public ObjectId snippetId;
    public ObjectId problemId;
}