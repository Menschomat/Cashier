package de.menschomat.wgo.database.jpa.repositories;

import de.menschomat.wgo.database.mongo.model.ScheduledTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduledTask, Long> {
    public List<ScheduledTask> findAllByUserID(String userID);
    public void deleteAllById(List<String> ids);
}
