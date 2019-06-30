package de.menschomat.cashier.database.jpa.repositories;

import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.database.jpa.model.ScheduledTask;
import de.menschomat.cashier.database.jpa.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, String> {

    public Tag findByUser(DBUser name);

    public List<Tag> findByColor(String color);

    public List<Tag> findAllByUser(DBUser user);

    public List<Tag> findAllByTitleIn(List<String> list);

    public List<Tag> findAllByScheduledTasks(ScheduledTask task);

    public Tag findByTitleAndUser(String title, DBUser user);

}
