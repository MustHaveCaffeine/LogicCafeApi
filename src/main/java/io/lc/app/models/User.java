package io.lc.app.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection="users")
public class User {
    @Getter
    private ObjectId id;
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;

    @Override
    public String toString() {
        return String.format("%s %s", this.firstName, this.lastName);
    }
}