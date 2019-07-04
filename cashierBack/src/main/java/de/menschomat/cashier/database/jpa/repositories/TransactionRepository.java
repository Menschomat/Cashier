package de.menschomat.cashier.database.jpa.repositories;

import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.database.jpa.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Page<Transaction> findByUser(DBUser user, Pageable pageable);

    Page<Transaction> findByDateBetweenAndUser(Date from, Date to, DBUser user, Pageable pageable);

    List<Transaction> findByDateBetweenAndUser(Date from, Date to, DBUser user);

    Transaction findByTitle(String title);

    void deleteByIdAndUser(String id, DBUser user);

}
