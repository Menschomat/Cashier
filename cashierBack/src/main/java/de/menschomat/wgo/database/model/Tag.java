package de.menschomat.wgo.database.model;

import org.springframework.data.annotation.Id;

public class Tag {
    @Id
    public String id;
    public String title = "";
    public String color = "gray";
    public String linkedUserID;

    public Tag() {
    }

    public Tag(String title, String color, String linkedUserID) {
        this.title = title;
        this.color = color;
        this.linkedUserID = linkedUserID;
    }

    @Override
    public String toString() {
        return String.format(
                "Tag[name=%s, color='%s']",
                title, color);
    }
}
