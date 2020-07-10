package com.bhp.opusb.service.trigger;

import java.util.Map;

public interface ProcessTrigger {
  
  void run(Map<String, Object> params);
}