package de.menschomat.wgo.database.jpa.repositories;

import de.menschomat.wgo.database.jpa.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<DBUser, String> {

    public DBUser findByEmail(String id);

    public DBUser findByUsername(String username);

    public List<DBUser> findAllBySurname(String surname);

    public List<DBUser> findAllByName(String name);

}
