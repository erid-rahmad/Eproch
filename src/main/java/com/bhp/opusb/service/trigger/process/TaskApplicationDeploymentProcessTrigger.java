package com.bhp.opusb.service.trigger.process;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bhp.opusb.domain.AdTask;
import com.bhp.opusb.domain.AdTaskApplication;
import com.bhp.opusb.domain.AdTaskProcess;
import com.bhp.opusb.repository.AdTaskApplicationRepository;
import com.bhp.opusb.repository.AdTaskRepository;
import com.bhp.opusb.service.trigger.ProcessTrigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.dataflow.core.ApplicationType;
import org.springframework.cloud.dataflow.rest.client.DataFlowOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskApplicationDeploymentProcessTrigger implements ProcessTrigger {

  private static final Logger LOG = LoggerFactory.getLogger(TaskApplicationDeploymentProcessTrigger.class);

  private DataFlowOperations dataFlowTemplate;
  private AdTaskRepository taskRepository;
  private AdTaskApplicationRepository applicationRepository;

  @Autowired
  public TaskApplicationDeploymentProcessTrigger(DataFlowOperations dataFlowTemplate, AdTaskRepository taskRepository,
    AdTaskApplicationRepository applicationRepository) {
  
    this.dataFlowTemplate = dataFlowTemplate;
    this.taskRepository = taskRepository;
    this.applicationRepository = applicationRepository;
  }

  @Override
  public void run(Map<String, Object> params) {
    Long taskId = Long.valueOf((Integer) params.get("adTaskId"));
    Optional<AdTask> task = taskRepository.findById(taskId);

    if (!task.isPresent()) {
      return;
    }

    AdTask record = task.get();
    List<AdTaskProcess> processes = record.getAdTaskProcesses();
    StringBuilder taskDefinition = new StringBuilder();

    LOG.debug("Found {} number of processe(s) in task {}", processes.size(), taskId);
    for (AdTaskProcess process : processes) {
      AdTaskApplication app = process.getAdTaskApplication();
      if (app.isDeployed() && !app.isOverrideExisting())
        continue;

      Boolean force = app.isOverrideExisting();
      ApplicationType type = ApplicationType.task;
      dataFlowTemplate.appRegistryOperations()
        .register(app.getValue(), type, app.getUri(), app.getMetadataUri(), force);
      
      if (app.isDeployed())
        continue;

      app.setDeployed(true);
      applicationRepository.save(app);
    }

    dataFlowTemplate.taskOperations()
      .create(record.getValue(), taskDefinition.toString(), null);
  }
  
}