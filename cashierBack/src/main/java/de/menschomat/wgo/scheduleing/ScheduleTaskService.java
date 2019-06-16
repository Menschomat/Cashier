package de.menschomat.wgo.scheduleing;

import org.bson.types.ObjectId;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

@Service
public class ScheduleTaskService {
    // Task Scheduler

    private ThreadPoolTaskScheduler scheduler;
    
    // A map for keeping scheduled tasks
    private Map<String , ScheduledFuture<?>> jobsMap = new HashMap<>();
    
    public ScheduleTaskService() {
        this.scheduler = new ThreadPoolTaskScheduler();
        this.scheduler.initialize();
    }
    
    
    // Schedule Task to be executed every night at 00 or 12 am
    public void addTaskToScheduler(String id, Runnable task, String cronString) {
        ScheduledFuture<?> scheduledTask = scheduler.schedule(task, new CronTrigger(cronString, TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        jobsMap.put(id, scheduledTask);
    }
    
    // Remove scheduled task 
    public void removeTaskFromScheduler(String id) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(id);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.remove(id);
        }
    }
    
    // A context refresh event listener
    @EventListener({ ContextRefreshedEvent.class })
    void contextRefreshedEvent() {
        // Get all tasks from DB and reschedule them in case of context restarted
    }
}