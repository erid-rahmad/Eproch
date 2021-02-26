//package com.bhp.opusb.service.trigger.process;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.bhp.opusb.service.dto.ProcessResult;
//import com.bhp.opusb.service.dto.TriggerResult;
//import com.bhp.opusb.service.trigger.ProcessTrigger;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.dataflow.rest.client.DataFlowOperations;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TaskExecutorProcessTrigger implements ProcessTrigger {
//
//  private static final Logger log = LoggerFactory.getLogger(TaskExecutorProcessTrigger.class);
//
//  private final DataFlowOperations dataFlowTemplate;
//
//  public TaskExecutorProcessTrigger(DataFlowOperations dataFlowTemplate) {
//    this.dataFlowTemplate = dataFlowTemplate;
//  }
//
//  @Override
//  public TriggerResult run(Map<String, Object> params) {
//    String taskName = (String) params.get("taskName");
//    Map<String, String> properties = new HashMap<>();
//    List<String> arguments = new ArrayList<>();
//    String alternateComposedTaskRunnerApp = null;
//    log.debug("Executing SCDF task {}", taskName);
//    long executionId = dataFlowTemplate.taskOperations()
//      .launch(taskName, properties, arguments, alternateComposedTaskRunnerApp);
//
//    log.info("SCDF task {} executed with ID: {}", taskName, executionId);
//    return new ProcessResult().add("execution_id", executionId);
//  }
//
//}
