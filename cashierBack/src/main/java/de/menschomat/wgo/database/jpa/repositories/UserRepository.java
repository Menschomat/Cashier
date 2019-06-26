package de.menschomat.wgo.database.jpa.repositories;

import de.menschomat.wgo.database.mongo.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<DBUser, Long> {

    public DBUser findByEmail(String id);

    public DBUser findByUsername(String username);

    public List<DBUser> findAllBySurname(String surname);

    public List<DBUser> findAllByName(String name);

}
