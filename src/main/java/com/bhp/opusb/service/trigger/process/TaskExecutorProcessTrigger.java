package com.bhp.opusb.service.trigger.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhp.opusb.service.trigger.ProcessTrigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.dataflow.rest.client.DataFlowOperations;
import org.springframework.stereotype.Service;

@Service
public class TaskExecutorProcessTrigger implements ProcessTrigger {

  private static final Logger log = LoggerFactory.getLogger(TaskExecutorProcessTrigger.class);

  private DataFlowOperations dataFlowTemplate;

  public TaskExecutorProcessTrigger(DataFlowOperations dataFlowTemplate) {
    this.dataFlowTemplate = dataFlowTemplate;
  }

  @Override
  public void run(Map<String, Object> params) {
    String taskName = (String) params.get("taskName");
    Map<String, String> properties = new HashMap<>();
    List<String> arguments = new ArrayList<>();
    String alternateComposedTaskRunnerApp = null;
    log.debug("Executing task {}", taskName);
    long executionId = dataFlowTemplate.taskOperations()
      .launch(taskName, properties, arguments, alternateComposedTaskRunnerApp);
    
    log.info("Task {} executed with ID: {}", taskName, executionId);
  }
  
}