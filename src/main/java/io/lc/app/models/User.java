package io.lc.app.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import io.leangen.graphql.annotations.GraphQLQuery;

@Document(collection="users")
public class User {
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String username;
    private String emailAddress;

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
    @GraphQLQuery(name = "emailAddress") 
    public String getEmailAddress() {
        return this.emailAddress;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.firstName, this.lastName);
    }
}