package de.menschomat.cashier.database.jpa.model;


import java.util.Date;

public class RestUser {
    public String id;
    public String username;
    public String email;
    public String name;
    public String surname;
    public Date dateOfBirth;
    public Boolean initialized;
    public String role;

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

}
