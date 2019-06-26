package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.mongo.model.ScheduledTask;
import de.menschomat.wgo.database.mongo.repositories.ScheduleRepository;
import de.menschomat.wgo.database.mongo.repositories.TransactionRepository;
import de.menschomat.wgo.rest.model.ScheduleInformation;
import de.menschomat.wgo.scheduleing.ScheduleTaskService;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleHandler {
    private final
    TransactionRepository transactionRepository;
    private final
    ScheduleRepository scheduleRepository;

    private final
    ScheduleTaskService scheduleTaskService;

    public ScheduleHandler(TransactionRepository transactionRepository, ScheduleRepository scheduleRepository, ScheduleTaskService scheduleTaskService) {
        this.transactionRepository = transactionRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleTaskService = scheduleTaskService;
    }

    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<ScheduledTask> getScheduleTask(Authentication authentication) {
        return scheduleRepository.findAllByUserID(authentication.getName());
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<ScheduledTask> addScheduleTask(Authentication authentication, @RequestBody ScheduleInformation scheduleInformation) {

        scheduleInformation.toSchedule.linkedUserID = authentication.getName();
        ScheduledTask toAdd = new ScheduledTask(UUID.randomUUID().toString(), scheduleInformation.toSchedule, authentication.getName(), scheduleInformation.cronTab);
        scheduleRepository.save(toAdd);
        scheduleTaskService.addTaskToScheduler(toAdd.id, new Runnable() {
            @Override
            public void run() {
                scheduleInformation.toSchedule.id = new ObjectId();
                scheduleInformation.toSchedule.date = new Date(System.currentTimeMillis());
                transactionRepository.insert(scheduleInformation.toSchedule);
            }
        }, scheduleInformation.cronTab);
        return getScheduleTask(authentication);
    }

    @DeleteMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<ScheduledTask> delScheduleTask(Authentication authentication, @RequestParam String id) {
        scheduleTaskService.removeTaskFromScheduler(id);
        scheduleRepository.deleteById(id);
        return getScheduleTask(authentication);
    }
    @DeleteMapping(value = "/multiple", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<ScheduledTask> delScheduleTask(Authentication authentication, @RequestParam List<String> ids) {
        ids.forEach(id -> {
            scheduleTaskService.removeTaskFromScheduler(id);
            scheduleRepository.deleteById(id);
        });

        return getScheduleTask(authentication);
    }
}
