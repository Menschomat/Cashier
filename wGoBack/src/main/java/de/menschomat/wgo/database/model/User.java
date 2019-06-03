package de.menschomat.wgo.database.model;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class User {
    @Id
    public String id;
    public String email;
    public String name;
    public String surname;
    public Date dateOfBirth;
    public String passwordHash;
    public Boolean initialized = false;

    public User(){

    };
    public User(String email, String name, String surname, Date dateOfBirth, String passwordHash, Boolean initialized) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.passwordHash = passwordHash;
        this.initialized = initialized;
    }

}
