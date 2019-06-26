package de.menschomat.wgo.database.jpa.repositories;

import de.menschomat.wgo.database.mongo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    public Tag findByTitle(String name);

    public List<Tag> findAllByColor(String color);

    public List<Tag> findAllByLinkedUserID(String linkedUserID);

    public List<Tag> findAllByTitleIn(List<String> list);

    public Tag findByTitleAndLinkedUserID(String title, String linkedUserId);

}
