package com.bhp.opusb.service.trigger;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.bhp.opusb.service.dto.TriggerResult;

public interface ProcessTrigger {
  public static final String ORACLE_DATE_FORMAT = "dd-MMM-yy";
  public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ORACLE_DATE_FORMAT);
  
  TriggerResult run(Map<String, Object> params);
}