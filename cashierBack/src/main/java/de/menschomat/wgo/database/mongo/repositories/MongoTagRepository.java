package de.menschomat.wgo.database.mongo.repositories;

import de.menschomat.wgo.database.mongo.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoTagRepository extends MongoRepository<Tag, String> {

    public Tag findByTitle(String name);

    public List<Tag> findAllByColor(String color);

    public List<Tag> findAllByLinkedUserID(String linkedUserID);

    public List<Tag> findAllByTitleIn(List<String> list);

    public Tag findByTitleAndLinkedUserID(String title, String linkedUserId);

}
