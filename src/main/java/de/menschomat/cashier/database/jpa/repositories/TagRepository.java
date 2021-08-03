package de.menschomat.cashier.database.jpa.repositories;

import de.menschomat.cashier.database.jpa.model.ScheduledTask;
import de.menschomat.cashier.database.jpa.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, String> {
    List<Tag> findAllByScheduledTasks(ScheduledTask task);
}
