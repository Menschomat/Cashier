package de.menschomat.wgo.database.jpa.repositories;

import de.menschomat.wgo.database.jpa.model.DBUser;
import de.menschomat.wgo.database.jpa.model.Tag;
import de.menschomat.wgo.database.jpa.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    public Transaction findByTitle(String title);

    public List<Transaction> findAllByTags(List<Tag> tags);

    public List<Transaction> findAllByDateBetween(Date from, Date to);

    public List<Transaction> findAllByUser(DBUser user);

    public List<Transaction> findAllByIngestion(boolean ingestion);

    public Page<Transaction> findByUser(DBUser user, Pageable pageable);

    public Page<Transaction> findByDateBetweenAndUser(Date from, Date to, DBUser user, Pageable pageable);

    public List<Transaction> findByDateBetweenAndUser(Date from, Date to, DBUser user);

    public List<Transaction> findAllByUserOrderByDate(DBUser user, Pageable pageable);

    public  void  deleteAllById(List<String> ids);

}
