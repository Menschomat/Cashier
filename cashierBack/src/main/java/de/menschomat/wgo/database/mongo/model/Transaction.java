package de.menschomat.wgo.database.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class Transaction {

    @Id
    public ObjectId id;
    public String title;
    public Float amount;
    public boolean ingestion;
    public Date date;
    public List<String> tagIds;
    public String linkedUserID;

    public Transaction() {
    }

    public Transaction(String title, Date date, Float amount, boolean ingestion, List<String> tagIds, String linkedUserID) {
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.ingestion = ingestion;
        this.tagIds = tagIds;
        this.linkedUserID = linkedUserID;
    }
}
