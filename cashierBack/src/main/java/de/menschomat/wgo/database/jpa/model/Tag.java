package de.menschomat.wgo.database.jpa.model;

import de.menschomat.wgo.database.jpa.helpers.AuditModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tag")
public class Tag extends AuditModel {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(updatable = false, nullable = false)
    private String id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String title = "";

    @NotBlank
    @Size(min = 3, max = 100)
    private String color = "gray";

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "user_id")
    private DBUser user;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "transactions")
    private List<Transaction> transactions;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "scheduled_tasks")
    private List<ScheduledTask> scheduledTasks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public DBUser getUser() {
        return user;
    }

    public void setUser(DBUser user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) &&
                Objects.equals(title, tag.title) &&
                Objects.equals(color, tag.color) &&
                Objects.equals(user, tag.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, color, user);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
