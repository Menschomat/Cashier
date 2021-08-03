package de.menschomat.cashier.database.jpa.repositories;


import de.menschomat.cashier.database.jpa.model.ScheduledTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduledTask, String> {
}
