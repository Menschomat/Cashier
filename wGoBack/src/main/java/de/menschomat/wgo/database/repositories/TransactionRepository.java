package de.menschomat.wgo.database.repositories;

import de.menschomat.wgo.database.model.Tag;
import de.menschomat.wgo.database.model.Transaction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    public Transaction findByTitle(String title);

    public List<Transaction> findAllByTagIdsContains(String id);

    public List<Transaction> findAllByDateBetween(Date from, Date to);

    public List<Transaction> findAllByLinkedUserID(String linkedUserID);

}
