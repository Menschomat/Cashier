package de.menschomat.wgo.database.jpa.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "scheduled_task")
public class ScheduledTask {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(updatable = false, nullable = false, unique = true)
    private String id;

    @Size(min = 3, max = 100)
    private String title;

    private Float amount;

    private boolean ingestion;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "tags")
    private List<Tag> tags;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private DBUser user;


    @NotBlank
    @Size(min = 3, max = 20)
    private String cronTab;

    public ScheduledTask() {

    }

    public ScheduledTask(DBUser user, String cronTab, Float amount, Boolean ingestion, List<Tag> tags, String title) {
        this.user = user;
        this.cronTab = cronTab;
        this.amount = amount;
        this.ingestion = ingestion;
        this.tags = tags;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public boolean isIngestion() {
        return ingestion;
    }

    public void setIngestion(boolean ingestion) {
        this.ingestion = ingestion;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledTask that = (ScheduledTask) o;
        return ingestion == that.ingestion &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(tags, that.tags) &&
                Objects.equals(user, that.user) &&
                Objects.equals(cronTab, that.cronTab);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, amount, ingestion, tags, user, cronTab);
    }
}
