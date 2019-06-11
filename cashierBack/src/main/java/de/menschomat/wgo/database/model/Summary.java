
package de.menschomat.wgo.database.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Summary {
    @Id
    public String id;
    public String linkedUserID;
    public int month;
    public int year;
    public Float balance;
    public List<String> tagIDs;
}