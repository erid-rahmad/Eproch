package com.bhp.opusb.service.trigger;

import java.util.Map;

import com.bhp.opusb.service.dto.TriggerResult;

public interface ProcessTrigger {
  
  TriggerResult run(Map<String, Object> params);
}