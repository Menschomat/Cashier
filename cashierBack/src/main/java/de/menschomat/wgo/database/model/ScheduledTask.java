package de.menschomat.wgo.database.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ScheduledTask {
    @Id
    public String id;
    @Indexed(name = "userid_index", direction = IndexDirection.DESCENDING, unique = true)
    public String userID;
    public String cronTab;

    public Transaction transaction;

    public ScheduledTask() {

    }

    public ScheduledTask(Transaction transaction, String userID, String cronTab) {
        this.transaction = transaction;
        this.userID = userID;
        this.cronTab = cronTab;
    }
    public ScheduledTask(String id, Transaction transaction, String userID, String cronTab) {
        this.id = id;
        this.transaction = transaction;
        this.userID = userID;
        this.cronTab = cronTab;
    }

}
