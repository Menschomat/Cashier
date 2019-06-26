package de.menschomat.wgo.database.jpa.model;


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
    public RestUser(DBUser input){
        this.id = input.getId();
        this.email = input.getEmail();
        this.name = input.getName();
        this.username = input.getUsername();
        this.surname = input.getSurname();
        this.dateOfBirth = input.getDateOfBirth();
        this.initialized = input.getInitialized();
        this.role = input.getRole();
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
