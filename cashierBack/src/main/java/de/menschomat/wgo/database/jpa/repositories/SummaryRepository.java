package de.menschomat.wgo.database.jpa.repositories;

import de.menschomat.wgo.database.mongo.model.Summary;
import de.menschomat.wgo.database.mongo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SummaryRepository extends JpaRepository<Summary, Long> {

    public Summary findByLinkedUserIDAndYearAndMonth(String linkedUserID, int year, int month);

    public List<Summary> findAllBylinkedUserID(String linkedUserID);

    public List<Summary> findAllByLinkedUserIDAndYear(String linkedUserID, int year);
}
