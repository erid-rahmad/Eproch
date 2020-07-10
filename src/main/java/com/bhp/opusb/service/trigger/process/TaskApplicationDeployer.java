package com.bhp.opusb.service.trigger.process;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.bhp.opusb.domain.AdTask;
import com.bhp.opusb.domain.AdTaskApplication;
import com.bhp.opusb.domain.AdTaskProcess;
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
public class TaskApplicationDeployer implements ProcessTrigger {

  private static final Logger LOG = LoggerFactory.getLogger(TaskApplicationDeployer.class);

  private DataFlowOperations dataFlowTemplate;
  private AdTaskRepository taskRepository;

  @Autowired
  public TaskApplicationDeployer(DataFlowOperations dataFlowTemplate, AdTaskRepository taskRepository) {
    this.dataFlowTemplate = dataFlowTemplate;
    this.taskRepository = taskRepository;
  }

  @Override
  public void run(Map<String, Object> params) {
    Long taskId = Long.valueOf((Integer) params.get("taskId"));
    Optional<AdTask> task = taskRepository.findById(taskId);

    if (!task.isPresent()) {
      return;
    }

    Set<AdTaskProcess> processes = task.get().getAdTaskProcesses();

    LOG.debug("Found {} number of processe(s) in task {}", processes.size(), taskId);
    for (AdTaskProcess process : processes) {
      AdTaskApplication app = process.getAdTaskApplication();
      Boolean force = app.isOverrideExisting();
      ApplicationType type = ApplicationType.task;
      dataFlowTemplate.appRegistryOperations()
        .register(app.getValue(), type, app.getUri(), app.getMetadataUri(), force);
    }
  }
  
}