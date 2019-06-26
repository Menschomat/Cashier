package de.menschomat.wgo.database.mongo.repositories;

import de.menschomat.wgo.database.mongo.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    public Transaction findByTitle(String title);

    public List<Transaction> findAllByTagIdsContains(String id);

    public List<Transaction> findAllByDateBetween(Date from, Date to);

    public List<Transaction> findAllByLinkedUserID(String linkedUserID);

    public List<Transaction> findAllByIngestion(boolean ingestion);

    public Page<Transaction> findByLinkedUserID(String linkedUserID, Pageable pageable);

    public Page<Transaction> findByDateBetweenAndLinkedUserID(Date from, Date to, String linkedUserID, Pageable pageable);

    public List<Transaction> findByDateBetweenAndLinkedUserID(Date from, Date to, String linkedUserID);

    public List<Transaction> findAllByLinkedUserIDOrderByDate(String linkedUserID, Pageable pageable);

    public  void  deleteAllById(List<String> ids);

}
