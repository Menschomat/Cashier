package de.menschomat.wgo.database.mongo.repositories;

import de.menschomat.wgo.database.mongo.model.ScheduledTask;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoScheduleRepository extends MongoRepository<ScheduledTask, String> {
    public List<ScheduledTask> findAllByUserID(String userID);
    public void deleteAllById(List<String> ids);
}
