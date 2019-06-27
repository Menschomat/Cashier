package de.menschomat.wgo.rest;


import de.menschomat.wgo.database.jpa.model.*;
import de.menschomat.wgo.database.jpa.repositories.ScheduleRepository;
import de.menschomat.wgo.database.jpa.repositories.TagRepository;
import de.menschomat.wgo.database.jpa.repositories.TransactionRepository;
import de.menschomat.wgo.database.jpa.repositories.UserRepository;
import de.menschomat.wgo.scheduleing.ScheduleTaskService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
        List<ScheduledTask> out = userRepository.findById(authentication.getName()).get().getScheduledTasks();
        return out;
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<ScheduledTask> addScheduleTask(Authentication authentication, @RequestBody ScheduledTask scheduledTask) {
        final Optional<DBUser> user_o = userRepository.findById(authentication.getName());
        if (user_o.isPresent()) {
            scheduledTask.setUser(user_o.get());
            scheduledTask.setTags(scheduledTask.getTags().stream().map(tag -> {
                tag.setUser(user_o.get());
                return tag;
            }).collect(Collectors.toList()));
            // scheduledTask.getTags().forEach(tagRepository::saveAndFlush);
          tagRepository.saveAll(scheduledTask.getTags());

        //    tagRepository.flush();d
            final ScheduledTask toAdd = scheduleRepository.saveAndFlush(scheduledTask);
            scheduleTaskService.addTaskToScheduler(toAdd.getId(), new Runnable() {
                @Override
                public void run() {
                    Transaction newTrans = new Transaction();
                    newTrans.updateFromScheduledTask(toAdd);
                    List<Tag> tags =  scheduledTask.getTags().stream().map(tag -> tagRepository.findById(tag.getId()).get()).collect(Collectors.toList());
                    tags = tagRepository.findAllByScheduledTasks(scheduledTask);
                    newTrans.setTags(tags);
                    System.out.println(transactionRepository.save(newTrans).getId());
                }
            }, scheduledTask.getCronTab());
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
