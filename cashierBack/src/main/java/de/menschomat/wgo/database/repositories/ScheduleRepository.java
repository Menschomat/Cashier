package de.menschomat.wgo.database.repositories;

import de.menschomat.wgo.database.model.ScheduledTask;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScheduleRepository extends MongoRepository<ScheduledTask, String> {
    public List<ScheduledTask> findAllByUserID(String userID);
}
