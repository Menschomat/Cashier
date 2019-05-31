package de.menschomat.wgo.database.model;

import org.springframework.data.annotation.Id;

public class Tag {
    @Id
    public String id;
    public String title = "";
    public String color = "gray";

    public Tag() {
    }

    public Tag(String title, String color) {
        this.title = title;
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format(
                "Tag[name=%s, color='%s']",
                title, color);
    }
}
