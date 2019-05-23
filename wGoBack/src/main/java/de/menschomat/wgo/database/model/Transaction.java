package de.menschomat.wgo.database.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Transaction {

    @Id
    public String id;

    public String title;
    public Float amount;

    public String date;

    public List<String> tagIds;

    public Transaction() {
    }

    public Transaction(String title, String date, Float amount, List<String> tagIds) {
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.tagIds = tagIds;
    }

    @Override
    public String toString() {
        return String.format(
                "Transaction[id=%s, title='%s', amount='%s', date='%s']",
                id, title, amount, date);
    }

}
