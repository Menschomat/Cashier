package de.menschomat.wgo.database.jpa.repositories;


import de.menschomat.wgo.database.jpa.model.DBUser;
import de.menschomat.wgo.database.jpa.model.ScheduledTask;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduledTask, String> {
    public List<ScheduledTask> findByUser(DBUser user);
    public void deleteAllById(List<String> ids);
}
