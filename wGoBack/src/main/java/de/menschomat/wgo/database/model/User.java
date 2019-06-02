package de.menschomat.wgo.database.model;

import org.springframework.data.annotation.Id;

public class User {
    @Id
    public String id;
    public String email;
    public String name;
    public String surname;
    public String dateOfBirth;
    public String passwordHash;
}
