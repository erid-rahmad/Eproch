package com.bhp.opusb.service.dto;

import java.util.HashMap;
import java.util.Map;

public class ProcessResult implements TriggerResult {

  private final Map<String, Object> result = new HashMap<>();

  @Override
  public Object get() {
    return result;
  }

  public ProcessResult add(String key, Object value) {
    result.put(key, value);
    return this;
  }
  
}
