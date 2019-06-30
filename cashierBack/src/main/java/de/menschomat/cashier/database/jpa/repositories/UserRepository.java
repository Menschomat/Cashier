package de.menschomat.cashier.database.jpa.repositories;

import de.menschomat.cashier.database.jpa.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<DBUser, String> {
    DBUser findByUsername(String username);
}
