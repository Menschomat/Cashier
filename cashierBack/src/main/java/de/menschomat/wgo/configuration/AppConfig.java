package de.menschomat.wgo.configuration;


import de.menschomat.wgo.database.jpa.model.Tag;
import de.menschomat.wgo.database.jpa.model.Transaction;
import de.menschomat.wgo.database.jpa.repositories.ScheduleRepository;
import de.menschomat.wgo.database.jpa.repositories.TagRepository;
import de.menschomat.wgo.database.jpa.repositories.TransactionRepository;
import de.menschomat.wgo.scheduleing.ScheduleTaskService;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@Transactional
public class AppConfig {
    private final
    ScheduleRepository scheduleRepository;
    private final
    ScheduleTaskService scheduleTaskService;
    private final
    TransactionRepository transactionRepository;
    private final TagRepository tagRepository;

    public AppConfig(ScheduleRepository scheduleRepository, ScheduleTaskService scheduleTaskService, TransactionRepository transactionRepository, TagRepository tagRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleTaskService = scheduleTaskService;
        this.transactionRepository = transactionRepository;
        this.tagRepository = tagRepository;
    }

    @PostConstruct
    public void init() {
        scheduleRepository.findAll().forEach(scheduledTask -> {

            scheduleTaskService.addTaskToScheduler(scheduledTask.getId(), new Runnable() {
                @Override
                public void run() {
                    Transaction toAdd = new Transaction();
                    toAdd.updateFromScheduledTask(scheduledTask);
                    toAdd.setTags(tagRepository.findAllByScheduledTasks(scheduledTask));
                    transactionRepository.save(toAdd);
                }
            }, scheduledTask.getCronTab());
        });
    }
}
