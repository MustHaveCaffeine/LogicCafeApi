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
    private String description;

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
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return the slug
     */
    public String getSlug() {
        return this.slug;
    }
    public void getSlug(String slug) {
        this.slug = slug;
    }

    /**
     * @return the tags
     */
    public List<String> getTags() {
        return this.tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * @return the level
     */
    public Level getLevel() {
        return this.level;
    }
    public void setLevel(String level) {
        this.level = Level.valueOf(level);
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Problem[%s]", this.slug);
    }
}