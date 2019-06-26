package de.menschomat.wgo.database.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.menschomat.wgo.database.mongo.model.Transaction;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Document
public class ScheduledTask {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "user_id", updatable = false, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DBUser user;

    @NotBlank
    @Size(min = 3, max = 20)
    private String cronTab;

    private Transaction transaction;


    public ScheduledTask(Transaction transaction, DBUser user, String cronTab) {
        this.transaction = transaction;
        this.user = user;
        this.cronTab = cronTab;
    }

    public ScheduledTask(String id, Transaction transaction, DBUser user, String cronTab) {
        this.id = id;
        this.transaction = transaction;
        this.user = user;
        this.cronTab = cronTab;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DBUser getUser() {
        return user;
    }

    public void setUser(DBUser user) {
        this.user = user;
    }

    public String getCronTab() {
        return cronTab;
    }

    public void setCronTab(String cronTab) {
        this.cronTab = cronTab;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledTask that = (ScheduledTask) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(cronTab, that.cronTab) &&
                Objects.equals(transaction, that.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, cronTab, transaction);
    }
}
