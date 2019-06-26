package de.menschomat.wgo.database.mongo.repositories;

import de.menschomat.wgo.database.mongo.model.DBUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<DBUser, String> {

    public DBUser findByEmail(String id);

    public DBUser findByUsername(String username);

    public List<DBUser> findAllBySurname(String surname);

    public List<DBUser> findAllByName(String name);

}
