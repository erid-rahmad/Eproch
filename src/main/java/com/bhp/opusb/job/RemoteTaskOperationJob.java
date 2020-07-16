package com.bhp.opusb.job;

import java.util.HashMap;
import java.util.Map;

import com.bhp.opusb.service.AdTriggerService;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class RemoteTaskOperationJob implements Job {

  @Autowired
  private AdTriggerService triggerService;

  @Override
  public void execute(JobExecutionContext executionContext) throws JobExecutionException {
    JobDataMap jobDataMap = executionContext.getMergedJobDataMap();
    String taskName = jobDataMap.getString("taskName");
    // List<?> adTaskProcesses = (List<?>) jobDataMap.get("adTaskProcesses");
    Map<String, Object> params = new HashMap<>();
    params.put("taskName", taskName);
    triggerService.executeProcess("taskExecutorProcessTrigger", params);
  }
  
}