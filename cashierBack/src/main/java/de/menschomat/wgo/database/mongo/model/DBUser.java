package de.menschomat.wgo.database.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class DBUser {
    @Id
    public String id;
    @Indexed(name = "username_index", direction = IndexDirection.DESCENDING, unique = true)
    public String username;
    public String email;
    public String name;
    public String surname;
    public Date dateOfBirth;
    public String password;
    public Boolean initialized = false;
    public String role;

    public DBUser() {

    }

    public void updateFromRestUser(RestUser rUser) {
        this.username = rUser.username;
        this.dateOfBirth = rUser.dateOfBirth;
        this.email = rUser.email;
        this.name = rUser.name;
        this.surname = rUser.surname;
        this.initialized = rUser.initialized;
    }

    public DBUser(String email, String name, String surname, String username, Date dateOfBirth, String password, Boolean initialized, String role) {
        this.email = email;
        this.name = name;
        this.username = username;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.initialized = initialized;
        this.role = role;
    }

}
