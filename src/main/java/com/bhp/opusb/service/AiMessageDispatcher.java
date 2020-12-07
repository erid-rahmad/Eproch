package com.bhp.opusb.service;

import java.util.Map;

import com.bhp.opusb.config.factory.ProcessTriggerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AiMessageDispatcher {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(AiMessageDispatcher.class);
  private final ProcessTriggerFactory triggerFactory;

  public AiMessageDispatcher(ProcessTriggerFactory triggerFactory) {
    this.triggerFactory = triggerFactory;
  }

  @Async
  public void dispatch(String serviceName, Map<String, Object> payload) {
    LOGGER.debug("Dispatching message. serviceName: {}, payload {}", serviceName, payload);
    triggerFactory.get(serviceName).run(payload);
  }
}
