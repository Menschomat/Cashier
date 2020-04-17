package de.menschomat.cashier.rest;


import de.menschomat.cashier.database.jpa.model.*;
import de.menschomat.cashier.database.jpa.repositories.ScheduleRepository;
import de.menschomat.cashier.database.jpa.repositories.TagRepository;
import de.menschomat.cashier.database.jpa.repositories.TransactionRepository;
import de.menschomat.cashier.database.jpa.repositories.UserRepository;
import de.menschomat.cashier.scheduleing.ScheduleTaskService;
import de.menschomat.cashier.scheduleing.SchedulerUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/schedule")
@Transactional
public class ScheduleHandler {
    private final
    TransactionRepository transactionRepository;
    private final
    ScheduleRepository scheduleRepository;
    private final
    UserRepository userRepository;
    private final TagRepository tagRepository;

    private final
    ScheduleTaskService scheduleTaskService;

    public ScheduleHandler(TransactionRepository transactionRepository, ScheduleRepository scheduleRepository, UserRepository userRepository, TagRepository tagRepository, ScheduleTaskService scheduleTaskService) {
        this.transactionRepository = transactionRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.scheduleTaskService = scheduleTaskService;
        this.tagRepository = tagRepository;
    }

    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<ScheduledTask> getScheduleTask(Authentication authentication) {
        Optional<DBUser> dbUserOptional = userRepository.findById(authentication.getName());
        if (dbUserOptional.isPresent()) {
            return dbUserOptional.get().getScheduledTasks();
        } else
            throw new UsernameNotFoundException("USER NOT FOUND");
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<ScheduledTask> addScheduleTask(Authentication authentication, @RequestBody ScheduledTask scheduledTask) {
        final Optional<DBUser> user_o = userRepository.findById(authentication.getName());
        if (user_o.isPresent()) {
            scheduledTask.setDate(new Date());
            scheduledTask.setUser(user_o.get());
            scheduledTask.setTags(scheduledTask.getTags().stream().peek(tag -> tag.setUser(user_o.get())).collect(Collectors.toList()));
            tagRepository.saveAll(scheduledTask.getTags());
            final ScheduledTask toAdd = scheduleRepository.saveAndFlush(scheduledTask);
            scheduleTaskService.addTaskToScheduler(toAdd.getId(), () -> SchedulerUtils.schedule(tagRepository, transactionRepository, scheduleRepository, toAdd), scheduledTask.getCronTab());
            return getScheduleTask(authentication);
        } else {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
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
