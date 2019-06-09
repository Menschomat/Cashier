package de.menschomat.wgo.database.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class DBUser {
    @Id
    public String id;
    public String username;
    public String email;
    public String name;
    public String surname;
    public Date dateOfBirth;
    public String password;
    public Boolean initialized = false;
    public String role;

    public DBUser(){

    };
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
