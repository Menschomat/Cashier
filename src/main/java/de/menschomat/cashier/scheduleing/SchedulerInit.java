package de.menschomat.cashier.scheduleing;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import de.menschomat.cashier.configuration.InitAdminConfiguration;
import de.menschomat.cashier.database.jpa.model.ScheduledTask;
import de.menschomat.cashier.database.jpa.model.Transaction;
import de.menschomat.cashier.database.jpa.repositories.ScheduleRepository;
import de.menschomat.cashier.database.jpa.repositories.TagRepository;
import de.menschomat.cashier.database.jpa.repositories.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

@Configuration
@Transactional
public class SchedulerInit {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleTaskService scheduleTaskService;
    private final TransactionRepository transactionRepository;
    private final TagRepository tagRepository;
    private final CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.SPRING));
    Logger logger = LoggerFactory.getLogger(SchedulerInit.class);

    public SchedulerInit(ScheduleRepository scheduleRepository, ScheduleTaskService scheduleTaskService, TransactionRepository transactionRepository, TagRepository tagRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleTaskService = scheduleTaskService;
        this.transactionRepository = transactionRepository;
        this.tagRepository = tagRepository;
    }

    @PostConstruct
    public void init() {
        List<ScheduledTask> taskList = scheduleRepository.findAll();
        (new Thread(() -> {
            logger.info("STARTING SLEEP_DETECTOR");
            long pause = 10000L;
            long error = 100L;
            while (true) {
                long sleepTil = System.currentTimeMillis() + pause + error;
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException ignored) {
                }
                if (System.currentTimeMillis() > sleepTil) {
                    logger.info("DETECTED SLEEP! DOING TASK_SCHEDULER_CHECK");
                    List<ScheduledTask> taskList1 = scheduleRepository.findAll();
                    taskList1.forEach(this::buildHistory);
                    logger.info("FINISHED TASK_SCHEDULER_CHECK");
                }
            }
        })).start();
        taskList.forEach(this::buildHistory);
        taskList.forEach(scheduledTask -> scheduleTaskService.addTaskToScheduler(scheduledTask.getId(), () -> SchedulerUtils.schedule(tagRepository, transactionRepository, scheduleRepository, scheduledTask), scheduledTask.getCronTab()));
    }

    public void buildHistory(ScheduledTask task) {

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime lastTime;
        if (task.getLast() != null) {
            lastTime = ZonedDateTime.ofInstant(task.getLast().toInstant(), ZoneId.systemDefault()).withSecond(0).withNano(0);
        } else {
            lastTime = ZonedDateTime.ofInstant(task.getDate().toInstant(), ZoneId.systemDefault()).withSecond(0).withNano(0);
        }
        ZonedDateTime shouldRun = ExecutionTime.forCron(parser.parse(task.getCronTab())).nextExecution(lastTime).get().withSecond(0).withNano(0);
        long difference = now.compareTo(shouldRun);
        if (difference >= 0) {
            logger.info("DOING MISSED SCHEDULED-TASK " + task.getTitle() + " FOR " + shouldRun);
            Transaction toAdd = new Transaction();
            toAdd.updateFromScheduledTask(task);
            toAdd.setTags(tagRepository.findAllByScheduledTasks(task));
            toAdd.setDate(Date.from(shouldRun.toInstant()));
            transactionRepository.save(toAdd);
            task.setLast(toAdd.getDate());
            buildHistory(scheduleRepository.save(task));
        }
    }

}
