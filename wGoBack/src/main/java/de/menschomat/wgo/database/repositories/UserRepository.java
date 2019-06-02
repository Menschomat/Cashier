package de.menschomat.wgo.database.repositories;

import de.menschomat.wgo.database.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByEmail(String id);

    public List<User> findAllBySurname(String surname);

    public List<User> findAllByName(String name);

}
