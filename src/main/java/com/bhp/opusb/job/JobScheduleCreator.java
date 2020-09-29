package com.bhp.opusb.job;

import java.time.ZoneId;
import java.util.TimeZone;

import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Chamith
 */
@Component
public class JobScheduleCreator {

    /**
     * Create Quartz Job.
     *
     * @param name Job name.
     * @param group Job group.
     * @param isDurable Job needs to be persisted even after completion. if true, job will be persisted, not otherwise.
     * @param taskName SCDF task name to execute.
     * @return JobDetail object
     */
    public JobDetail createJob(String name, String group, boolean isDurable, boolean remote) {
        String suffix = remote ? "task_" : "trigger_";
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(remote ? "taskName" : "serviceName", name);

        JobBuilder jobBuilder;

        if (remote) {
            jobBuilder = JobBuilder.newJob(RemoteTaskOperationJob.class);
        } else {
            jobBuilder = JobBuilder.newJob(LocalProcessTriggerJob.class);
        }
        
		return jobBuilder
            .withIdentity(suffix + name, group)
            .usingJobData(jobDataMap)
            .storeDurably(isDurable)
            .build();
    }

    /**
     * Create cron trigger.
     *
     * @param name Trigger name.
     * @param group Group name.
     * @param cronExpression     Cron expression.
     * @return {@link Trigger}
     */
    public Trigger createCronTrigger(String name, String group, String cronExpression) {
        return TriggerBuilder.newTrigger()
            .withIdentity(name, group)
            .withSchedule(
                CronScheduleBuilder.cronSchedule(cronExpression)
                    .withMisfireHandlingInstructionFireAndProceed()
                    .inTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()))
            )
            .usingJobData("cron", cronExpression)
            .build();
    }

    /**
     * Create simple trigger.
     *
     * @param name Trigger name.
     * @param group Trigger start time.
     * @param interval Job repeat period mills
     * @param unit {@link IntervalUnit}.
     * @return {@link Trigger}
     */
    public Trigger createPeriodicTrigger(String name, String group, int interval, IntervalUnit unit) {
        ScheduleBuilder<?> scheduleBuilder;

        if (unit == IntervalUnit.DAY || unit == IntervalUnit.WEEK
            || unit == IntervalUnit.MONTH || unit == IntervalUnit.YEAR) {

            scheduleBuilder = CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
                .withInterval(interval, unit);
        } else {
            scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().repeatForever();

            if (unit == IntervalUnit.HOUR) {
                ((SimpleScheduleBuilder) scheduleBuilder).withIntervalInHours(interval);
            } else if (unit == IntervalUnit.MINUTE) {
                ((SimpleScheduleBuilder) scheduleBuilder).withIntervalInMinutes(interval);
            } else if (unit == IntervalUnit.SECOND) {
                ((SimpleScheduleBuilder) scheduleBuilder).withIntervalInSeconds(interval);
            } else if (unit == IntervalUnit.MILLISECOND) {
                ((SimpleScheduleBuilder) scheduleBuilder).withIntervalInMilliseconds(interval);
            }
        }

        return TriggerBuilder.newTrigger()
            .withIdentity(name, group)
            .withSchedule(scheduleBuilder)
            .build();
    }
}
