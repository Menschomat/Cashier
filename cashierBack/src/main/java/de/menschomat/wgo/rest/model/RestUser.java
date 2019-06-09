package de.menschomat.wgo.rest.model;

import de.menschomat.wgo.database.model.DBUser;

import java.util.Date;

public class RestUser {
    public String id;
    public String username;
    public String email;
    public String name;
    public String surname;
    public Date dateOfBirth;
    public Boolean initialized = false;
    public String role;

    public RestUser() {

    }
    public  RestUser(DBUser input){
        this.id = input.id;
        this.email = input.email;
        this.name = input.name;
        this.username = input.username;
        this.surname = input.surname;
        this.dateOfBirth = input.dateOfBirth;
        this.initialized = input.initialized;
        this.role = input.role;
    }

    public RestUser(String id, String email, String name, String surname, String username, Date dateOfBirth, Boolean initialized, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.username = username;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.initialized = initialized;
        this.role = role;
    }

}
