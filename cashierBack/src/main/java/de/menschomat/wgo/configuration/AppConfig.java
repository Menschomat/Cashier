package de.menschomat.wgo.configuration;

import de.menschomat.wgo.database.repositories.ScheduleRepository;
import de.menschomat.wgo.database.repositories.TransactionRepository;
import de.menschomat.wgo.scheduleing.ScheduleTaskService;
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
            scheduleTaskService.addTaskToScheduler(scheduledTask.id, new Runnable() {
                @Override
                public void run() {
                    scheduledTask.transaction.date = new Date(System.currentTimeMillis());
                    transactionRepository.save(scheduledTask.transaction);
                }
            }, scheduledTask.cronTab);
        });
    }
}
