package io.lc.app.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class User {
    private ObjectId id;
    private String firstName = "Default";
    private String lastName = "Name";
    private String username = "sainiajay";
    private String email = "saini.ajay172@gmail.com";

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @return the lastName
     */
    public String getFullName() {
        return this.toString();
    }

    /**
     * @return the email address
     */
    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.firstName, this.lastName);
    }
}