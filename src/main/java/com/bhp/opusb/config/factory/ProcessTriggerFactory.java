package com.bhp.opusb.config.factory;

import com.bhp.opusb.service.trigger.ProcessTrigger;

public interface ProcessTriggerFactory {
  ProcessTrigger get(String name);
}