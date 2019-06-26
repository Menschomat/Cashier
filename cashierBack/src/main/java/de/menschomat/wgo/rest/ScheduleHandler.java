package de.menschomat.wgo.rest;


import de.menschomat.wgo.database.jpa.model.*;
import de.menschomat.wgo.database.jpa.repositories.ScheduleRepository;
import de.menschomat.wgo.database.jpa.repositories.TransactionRepository;
import de.menschomat.wgo.database.jpa.repositories.UserRepository;
import de.menschomat.wgo.scheduleing.ScheduleTaskService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleHandler {
    private final
    TransactionRepository transactionRepository;
    private final
    ScheduleRepository scheduleRepository;
    private final
    UserRepository userRepository;

    private final
    ScheduleTaskService scheduleTaskService;

    public ScheduleHandler(TransactionRepository transactionRepository, ScheduleRepository scheduleRepository, UserRepository userRepository, ScheduleTaskService scheduleTaskService) {
        this.transactionRepository = transactionRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.scheduleTaskService = scheduleTaskService;
    }

    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<ScheduledTask> getScheduleTask(Authentication authentication) {
        return userRepository.findById(authentication.getName()).get().getScheduledTasks();
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<ScheduledTask> addScheduleTask(Authentication authentication, @RequestBody ScheduleInformation scheduleInformation) {
        DBUser user = userRepository.findById(authentication.getName()).get();
        scheduleInformation.toSchedule.setUser(user);
        scheduleInformation.toSchedule.setUser(user);
        final ScheduledTask newSchedule = new ScheduledTask(
                user,
                scheduleInformation.cronTab,
                scheduleInformation.toSchedule.getAmount(),
                scheduleInformation.toSchedule.isIngestion(),
                scheduleInformation.toSchedule.getTags(),
                scheduleInformation.toSchedule.getTitle());
        final ScheduledTask toAdd = scheduleRepository.save(newSchedule);
        scheduleTaskService.addTaskToScheduler(toAdd.getId(), new Runnable() {
            @Override
            public void run() {
                Transaction newTrans = new Transaction();
                newTrans.updateFromScheduledTask(toAdd);
                transactionRepository.save(newTrans);
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
