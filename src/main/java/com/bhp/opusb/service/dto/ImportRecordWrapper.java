package com.bhp.opusb.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImportRecordWrapper {

  private final List<Map<String, Object>> records = new ArrayList<>();

  public boolean addRecord(Map<String, Object> record) {
    return records.add(record);
  }

  public List<Map<String, Object>> getRecords() {
    return records;
  }

  public int size() {
    return records.size();
  }
}
