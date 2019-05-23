package de.menschomat.wgo.database.model;

public class Tag {
    public String name;
    public String color = "gray";

    public Tag(String name) {
        this.name = name;
    }

    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format(
                "Tag[name=%s, color='%s']",
                name, color);
    }
}
