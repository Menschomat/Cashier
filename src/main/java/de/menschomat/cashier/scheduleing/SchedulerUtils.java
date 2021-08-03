package de.menschomat.cashier.scheduleing;

import de.menschomat.cashier.database.jpa.model.ScheduledTask;
import de.menschomat.cashier.database.jpa.model.Transaction;
import de.menschomat.cashier.database.jpa.repositories.ScheduleRepository;
import de.menschomat.cashier.database.jpa.repositories.TagRepository;
import de.menschomat.cashier.database.jpa.repositories.TransactionRepository;

public abstract class SchedulerUtils {
    public static void schedule(TagRepository tagRepository, TransactionRepository transactionRepository, ScheduleRepository scheduleRepository, ScheduledTask toAdd) {
        Transaction newTrans = new Transaction();
        newTrans.updateFromScheduledTask(toAdd);
        newTrans.setTags(tagRepository.findAllByScheduledTasks(toAdd));
        transactionRepository.save(newTrans);
        toAdd.setLast(newTrans.getDate());
        scheduleRepository.save(toAdd);
    }
}
