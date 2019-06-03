package de.menschomat.wgo.database.repositories;

import de.menschomat.wgo.database.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends MongoRepository<Tag, String> {

    public Tag findByTitle(String name);

    public List<Tag> findAllByColor(String color);

    public List<Tag> findAllByLinkedUserID(String linkedUserID);

    public List<Tag> findAllByTitleIn(List<String> list);

}
