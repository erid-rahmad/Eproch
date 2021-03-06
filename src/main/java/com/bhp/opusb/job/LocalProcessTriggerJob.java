package com.bhp.opusb.job;

import java.util.HashMap;

import com.bhp.opusb.service.AdTriggerService;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class LocalProcessTriggerJob implements Job {

  @Autowired
  private AdTriggerService triggerService;

  @Override
  public void execute(JobExecutionContext executionContext) throws JobExecutionException {
    JobDataMap jobDataMap = executionContext.getMergedJobDataMap();
    String serviceName = jobDataMap.getString("serviceName");

    // TODO Should be able to pass the trigger parameters.
    triggerService.executeProcess(serviceName, new HashMap<>(1));
  }
  
}