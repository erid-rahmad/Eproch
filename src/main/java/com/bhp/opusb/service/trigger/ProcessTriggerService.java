package com.bhp.opusb.service.trigger;

import java.util.Map;

import com.bhp.opusb.config.factory.ProcessTriggerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcessTriggerService {
  
  private static final Logger LOG = LoggerFactory.getLogger(ProcessTriggerService.class);
  private ProcessTriggerFactory triggerFactory;

  public ProcessTriggerService(ProcessTriggerFactory triggerFactory) {
    this.triggerFactory = triggerFactory;
  }

  public void executeProcess(String name, Map<String, Object> params) {
    if (LOG.isDebugEnabled()) {
      params.entrySet().stream().forEach((entry) -> {
        LOG.debug("> {}: {}", entry.getKey(), entry.getValue());
      });
    }
    this.triggerFactory.get(name).run(params);
  }

}