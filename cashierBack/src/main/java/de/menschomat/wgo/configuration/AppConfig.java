package de.menschomat.wgo.configuration;


import de.menschomat.wgo.database.jpa.model.Transaction;
import de.menschomat.wgo.database.jpa.repositories.ScheduleRepository;
import de.menschomat.wgo.database.jpa.repositories.TransactionRepository;
import de.menschomat.wgo.database.mongo.repositories.MongoScheduleRepository;
import de.menschomat.wgo.database.mongo.repositories.MongoTransactionRepository;
import de.menschomat.wgo.scheduleing.ScheduleTaskService;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Date;

@Configuration
public class AppConfig {
    private final
    ScheduleRepository scheduleRepository;
    private final
    ScheduleTaskService scheduleTaskService;
    private final
    TransactionRepository transactionRepository;

    public AppConfig(ScheduleRepository scheduleRepository, ScheduleTaskService scheduleTaskService, TransactionRepository transactionRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleTaskService = scheduleTaskService;
        this.transactionRepository = transactionRepository;
    }

    @PostConstruct
    public void init() {
        scheduleRepository.findAll().forEach(scheduledTask -> {
            scheduleTaskService.addTaskToScheduler(scheduledTask.getId(), new Runnable() {
                @Override
                public void run() {

                    Transaction toAdd = new Transaction();
                    toAdd.updateFromScheduledTask(scheduledTask);
                    transactionRepository.save(toAdd);
                }
            }, scheduledTask.getCronTab());
        });
    }
}
