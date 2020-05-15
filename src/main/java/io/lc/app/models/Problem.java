package io.lc.app.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="problems")
public class Problem {
    public enum Level {
        EASY, MEDIUM, HARD
    }

    private ObjectId id;
    private String title;
    private String slug;
    private Level level;
    private List<String> tags;

    /**
     * @return the Id
     */
    public ObjectId getId() {
        return this.id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * @return the slug
     */
    public String getSlug() {
        return this.slug;
    }

    /**
     * @return the tags
     */
    public List<String> getTags() {
        return this.tags;
    }

    /**
     * @return the level
     */
    public Level getLevel() {
        return this.level;
    }

    @Override
    public String toString() {
        return String.format("Problem[%s]", this.slug);
    }
}