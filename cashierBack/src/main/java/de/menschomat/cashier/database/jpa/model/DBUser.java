package de.menschomat.cashier.database.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.menschomat.cashier.rest.model.RestUser;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cashier_user")
public class DBUser implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36, updatable = false, nullable = false, unique = true)
    private String id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(unique = true)
    private String username;

    @Size(max = 100)
    private String email;

    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String surname;

    private Date dateOfBirth;

    private String password;

    private Boolean initialized;

    private String role;

    @OneToMany(mappedBy = "user",

            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Tag> tags;


    @OneToMany(mappedBy = "user",

            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "user",

            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<ScheduledTask> scheduledTasks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getInitialized() {
        return initialized;
    }

    public void setInitialized(Boolean initialized) {
        this.initialized = initialized;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBUser dbUser = (DBUser) o;
        return id.equals(dbUser.id) &&
                Objects.equals(username, dbUser.username) &&
                Objects.equals(email, dbUser.email) &&
                Objects.equals(name, dbUser.name) &&
                Objects.equals(surname, dbUser.surname) &&
                Objects.equals(dateOfBirth, dbUser.dateOfBirth) &&
                Objects.equals(password, dbUser.password) &&
                Objects.equals(initialized, dbUser.initialized) &&
                Objects.equals(role, dbUser.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, name, surname, dateOfBirth, password, initialized, role);
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<ScheduledTask> getScheduledTasks() {
        return scheduledTasks;
    }

    public void setScheduledTasks(List<ScheduledTask> scheduledTasks) {
        this.scheduledTasks = scheduledTasks;
    }
    public void updateFromRestUser(RestUser rUser) {
        this.username = rUser.username;
        this.dateOfBirth = rUser.dateOfBirth;
        this.email = rUser.email;
        this.name = rUser.name;
        this.surname = rUser.surname;
        this.initialized = rUser.initialized;
    }
}