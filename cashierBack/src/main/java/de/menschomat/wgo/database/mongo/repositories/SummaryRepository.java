package de.menschomat.wgo.database.mongo.repositories;

import de.menschomat.wgo.database.mongo.model.Summary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SummaryRepository extends MongoRepository<Summary, String> {

    public Summary findByLinkedUserIDAndYearAndMonth(String linkedUserID, int year, int month);

    public List<Summary> findAllBylinkedUserID(String linkedUserID);

    public List<Summary> findAllByLinkedUserIDAndYear(String linkedUserID, int year);
}
