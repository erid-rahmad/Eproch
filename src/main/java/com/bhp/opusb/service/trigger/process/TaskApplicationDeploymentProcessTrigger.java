//package com.bhp.opusb.service.trigger.process;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import com.bhp.opusb.domain.AdTask;
//import com.bhp.opusb.domain.AdTaskApplication;
//import com.bhp.opusb.domain.AdTaskProcess;
//import com.bhp.opusb.repository.AdTaskApplicationRepository;
//import com.bhp.opusb.repository.AdTaskRepository;
//import com.bhp.opusb.service.dto.ProcessResult;
//import com.bhp.opusb.service.dto.TriggerResult;
//import com.bhp.opusb.service.trigger.ProcessTrigger;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.dataflow.core.ApplicationType;
//import org.springframework.cloud.dataflow.rest.client.DataFlowOperations;
//import org.springframework.cloud.dataflow.rest.resource.TaskDefinitionResource;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//public class TaskApplicationDeploymentProcessTrigger implements ProcessTrigger {
//
//  private static final Logger LOG = LoggerFactory.getLogger(TaskApplicationDeploymentProcessTrigger.class);
//
//  private final DataFlowOperations dataFlowTemplate;
//  private final AdTaskRepository taskRepository;
//  private final AdTaskApplicationRepository applicationRepository;
//
//  @Autowired
//  public TaskApplicationDeploymentProcessTrigger(DataFlowOperations dataFlowTemplate, AdTaskRepository taskRepository,
//    AdTaskApplicationRepository applicationRepository) {
//
//    this.dataFlowTemplate = dataFlowTemplate;
//    this.taskRepository = taskRepository;
//    this.applicationRepository = applicationRepository;
//  }
//
//  @Override
//  public TriggerResult run(Map<String, Object> params) {
//    Long taskId = Long.valueOf((Integer) params.get("adTaskId"));
//    Optional<AdTask> task = taskRepository.findById(taskId);
//
//    if ( ! task.isPresent()) {
//      return new ProcessResult().add("error", "Task #" + taskId + " doesn't exist");
//    }
//
//    AdTask record = task.get();
//    List<AdTaskProcess> processes = record.getAdTaskProcesses();
//    StringBuilder taskDefinition = new StringBuilder();
//
//    LOG.debug("Found {} number of processe(s) in task {}", processes.size(), taskId);
//    for (AdTaskProcess process : processes) {
//      AdTaskApplication app = process.getAdTaskApplication();
//      boolean deployed = app.isDeployed();
//      boolean override = app.isOverrideExisting();
//
//      if (deployed && ! override) {
//        continue;
//      }
//
//      ApplicationType type = ApplicationType.task;
//      dataFlowTemplate.appRegistryOperations()
//        .register(app.getValue(), type, app.getUri(), app.getMetadataUri(), override);
//
//      if (! deployed) {
//        app.setDeployed(true);
//        applicationRepository.save(app);
//      }
//
//      if (taskDefinition.length() > 0) {
//        taskDefinition.append(" && ");
//      }
//      taskDefinition.append(app.getValue());
//    }
//
//    TaskDefinitionResource resource = dataFlowTemplate.taskOperations()
//      .create(record.getValue(), taskDefinition.toString(), null);
//
//    return new ProcessResult().add("status", resource.getStatus());
//  }
//
//}
