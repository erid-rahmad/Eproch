package com.bhp.opusb.service;

import java.util.Objects;
import java.util.Optional;

import com.bhp.opusb.domain.AdTask;
import com.bhp.opusb.domain.AdTaskScheduler;
import com.bhp.opusb.domain.AdTaskSchedulerGroup;
import com.bhp.opusb.domain.AdTrigger;
import com.bhp.opusb.domain.enumeration.AdSchedulerTrigger;
import com.bhp.opusb.job.JobScheduleCreator;
import com.bhp.opusb.repository.AdTaskRepository;
import com.bhp.opusb.repository.AdTaskSchedulerGroupRepository;
import com.bhp.opusb.repository.AdTaskSchedulerRepository;
import com.bhp.opusb.repository.AdTriggerRepository;
import com.bhp.opusb.service.dto.AdTaskSchedulerDTO;
import com.bhp.opusb.service.mapper.AdTaskSchedulerMapper;

import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdTaskScheduler}.
 */
@Service
@Transactional
public class AdTaskSchedulerService {

    private final Logger log = LoggerFactory.getLogger(AdTaskSchedulerService.class);

    private final AdTaskSchedulerRepository adTaskSchedulerRepository;

    private final AdTaskSchedulerGroupRepository adTaskSchedulerGroupRepository;

    private final AdTaskRepository adTaskRepository;
    private final AdTriggerRepository adTriggerRepository;

    private final AdTaskSchedulerMapper adTaskSchedulerMapper;

    private final Scheduler scheduler;

    private final JobScheduleCreator scheduleCreator;

    @Autowired
    public AdTaskSchedulerService(AdTaskSchedulerRepository adTaskSchedulerRepository,
            AdTaskSchedulerGroupRepository adTaskSchedulerGroupRepository,
            AdTaskRepository adTaskRepository,
            AdTriggerRepository adTriggerRepository,
            AdTaskSchedulerMapper adTaskSchedulerMapper, Scheduler scheduler,
            JobScheduleCreator scheduleCreator) {

        this.adTaskSchedulerRepository = adTaskSchedulerRepository;
        this.adTaskSchedulerGroupRepository = adTaskSchedulerGroupRepository;
        this.adTaskRepository = adTaskRepository;
        this.adTriggerRepository = adTriggerRepository;
        this.adTaskSchedulerMapper = adTaskSchedulerMapper;
        this.scheduler = scheduler;
        this.scheduleCreator = scheduleCreator;
    }

    /**
     * Save a adTaskScheduler.
     *
     * @param adTaskSchedulerDTO the entity to save.
     * @return the persisted entity.
     */
    public AdTaskSchedulerDTO save(AdTaskSchedulerDTO adTaskSchedulerDTO) {
        log.debug("Request to save AdTaskScheduler : {}", adTaskSchedulerDTO);
        final boolean newRecord = adTaskSchedulerDTO.getId() == null;
        AdTaskScheduler oldState = adTaskSchedulerRepository.findOneById(adTaskSchedulerDTO.getId());
        AdTaskScheduler adTaskScheduler = adTaskSchedulerMapper.toEntity(adTaskSchedulerDTO);
        adTaskScheduler = adTaskSchedulerRepository.save(adTaskScheduler);

        // Whether to invoke SCDF task or local trigger.
        boolean remote = adTaskScheduler.getAdTask() != null;

        AdTaskSchedulerGroup group = adTaskScheduler.getGroup();
        String groupName = null;
        String jobName;

        if (remote) {
            jobName = getJobName(adTaskScheduler.getAdTask().getId(), remote);
        } else {
            jobName = getJobName(adTaskScheduler.getAdTrigger().getId(), remote);
        }

        if (group != null) {
            groupName = getGroupName(group.getId());
        }

        try {
            if (newRecord) {
                // TODO Should be able to pass the trigger parameters for local process, 
                JobDetail jobDetail = scheduleCreator.createJob(jobName, groupName, false, remote);
                Trigger trigger = createTrigger(adTaskScheduler, groupName);
                scheduler.scheduleJob(jobDetail, trigger);
                log.info("Job with key - {} scheduled sucessfully", jobDetail.getKey());
            } else {
                if (needPause(adTaskScheduler.isActive(), oldState.isActive())) {
                    pauseTrigger(adTaskSchedulerDTO);
                    log.info("Job {}.{} paused", groupName, jobName);
                } else {
                    if (needResume(adTaskScheduler.isActive(), oldState.isActive())) {
                        resumeTrigger(adTaskSchedulerDTO);
                        log.info("Job {}.{} resumed", groupName, jobName);
                    }
                
                    if (needReschedule(adTaskScheduler, oldState)) {
                        Trigger trigger = createTrigger(adTaskScheduler, groupName);
                        scheduler.rescheduleJob(new TriggerKey(adTaskScheduler.getValue(), groupName), trigger);
                        log.info("Job {}.{} rescheduled", groupName, jobName);
                    }
                }
            }
        } catch (SchedulerException e) {
            log.error("Could not schedule job with key - {}.{} due to error - {}", groupName, jobName, e.getLocalizedMessage());
        }
        
        return adTaskSchedulerMapper.toDto(adTaskScheduler);
    }

    public boolean pauseTrigger(AdTaskSchedulerDTO dto) {
        String triggerName = dto.getValue();
        String groupName = getGroupName(dto.getGroupId());

        try {
            scheduler.pauseTrigger(new TriggerKey(triggerName, groupName));
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to pause job - {}.{} due to error - {}", groupName, triggerName, e.getLocalizedMessage());
            return false;
        }
    }

    public boolean resumeTrigger(AdTaskSchedulerDTO dto) {
        String triggerName = dto.getValue();
        String groupName = getGroupName(dto.getGroupId());

        try {
            scheduler.resumeTrigger(new TriggerKey(triggerName, groupName));
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to resume job - {}.{} due to error - {}", groupName, triggerName, e.getLocalizedMessage());
            return false;
        }
    }

    /**
     * Get all the adTaskSchedulers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTaskSchedulerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdTaskSchedulers");
        return adTaskSchedulerRepository.findAll(pageable).map(adTaskSchedulerMapper::toDto);
    }

    /**
     * Get one adTaskScheduler by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdTaskSchedulerDTO> findOne(Long id) {
        log.debug("Request to get AdTaskScheduler : {}", id);
        return adTaskSchedulerRepository.findById(id).map(adTaskSchedulerMapper::toDto);
    }

    /**
     * Delete the adTaskScheduler by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdTaskScheduler : {}", id);
        Optional<AdTaskScheduler> taskScheduler = adTaskSchedulerRepository.findById(id);

        if (! taskScheduler.isPresent())
            throw new IllegalArgumentException("There is no scheduler with ID " + id);

        AdTaskScheduler record = taskScheduler.get();

        // Whether to invoke task or trigger.
        boolean invokeTask = record.getAdTask() != null;

        AdTaskSchedulerGroup group = record.getGroup();
        String triggerName = getJobName(invokeTask ? record.getAdTask().getId() : record.getAdTrigger().getId(), invokeTask);
        String groupName = getGroupName(group == null ? null : group.getId());

        try {
            scheduler.unscheduleJob(new TriggerKey(triggerName, groupName));
            adTaskSchedulerRepository.deleteById(id);
        } catch (SchedulerException e) {
            log.error("Could not delete scheduler#{} with key - {}.{} due to error - {}", id, groupName, triggerName, e.getLocalizedMessage());
        }
    }

    private boolean needPause(boolean newState, boolean oldState) {
        return isActiveStateChanged(newState, oldState) && oldState;
    }

    private boolean needResume(boolean newState, boolean oldState) {
        return isActiveStateChanged(newState, oldState) && newState;
    }

    private boolean needReschedule(AdTaskScheduler newData, AdTaskScheduler oldData) {
        if (newData.getTrigger() != oldData.getTrigger())
            return true;

        boolean useCron = newData.getTrigger() == AdSchedulerTrigger.CRON;
        boolean useInterval = newData.getTrigger() == AdSchedulerTrigger.PERIODIC;
        boolean cronChanged = useCron && ! Objects.equals(newData.getCronExpression(), oldData.getCronExpression());
        boolean intervalChanged = useInterval && (! Objects.equals(newData.getPeriodicCount(), oldData.getPeriodicCount())
            || ! Objects.equals(newData.getPeriodicUnit(), oldData.getPeriodicUnit()));

        return cronChanged || intervalChanged;
    }

    private boolean isActiveStateChanged(boolean newState, boolean oldState) {
        return newState != oldState;
    }

    private String getJobName(Long id, boolean invokeTask) {
        if (invokeTask) {
            Optional<AdTask> record = adTaskRepository.findById(id);
            return record.isPresent() ? record.get().getValue() : null;
        } else {
            Optional<AdTrigger> record = adTriggerRepository.findById(id);
            return record.isPresent() ? record.get().getValue() : null;
        }
    }

    private String getGroupName(Long groupId) {
        if (groupId == null)
            return null;

        Optional<AdTaskSchedulerGroup> groupRecord = adTaskSchedulerGroupRepository.findById(groupId);
        return groupRecord.isPresent() ? groupRecord.get().getValue() : null;
    }

    private Trigger createTrigger(AdTaskScheduler adTaskScheduler, String groupName) {
        Trigger trigger;
        String triggerName = adTaskScheduler.getValue();

        log.debug("Trigger impl: {}", adTaskScheduler.getTrigger());
        if (adTaskScheduler.getTrigger() == AdSchedulerTrigger.CRON) {
            log.debug("Cron exp: {}", adTaskScheduler.getCronExpression());
            String cronExpression = adTaskScheduler.getCronExpression();
            trigger = scheduleCreator.createCronTrigger(triggerName, groupName, cronExpression);
        } else {
            int interval = adTaskScheduler.getPeriodicCount();
            String unit = adTaskScheduler.getPeriodicUnit();
            log.debug("Interval: {}, unit: {}", interval, unit);
            trigger = scheduleCreator.createPeriodicTrigger(triggerName, groupName, interval, IntervalUnit.valueOf(unit));
        }

        return trigger;
    }
}
