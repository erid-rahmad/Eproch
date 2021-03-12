package com.bhp.opusb.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public class ExportParameterDTO {
  
  @NotNull
  private long windowId;

  private boolean currentRowOnly;
  private long recordId;
  
  private boolean includeLines;
  private List<Long> includedSubTabs = new ArrayList<>();
  private Map<String, Object> parameterMapping = new HashMap<>();

  public long getWindowId() {
    return windowId;
  }

  public void setWindowId(long windowId) {
    this.windowId = windowId;
  }

  public boolean isCurrentRowOnly() {
    return currentRowOnly;
  }

  public void setCurrentRowOnly(boolean currentRowOnly) {
    this.currentRowOnly = currentRowOnly;
  }

  public long getRecordId() {
    return recordId;
  }

  public void setRecordId(long recordId) {
    this.recordId = recordId;
  }

  public boolean isIncludeLines() {
    return includeLines;
  }

  public void setIncludeLines(boolean includeLines) {
    this.includeLines = includeLines;
  }

  public List<Long> getIncludedSubTabs() {
    return includedSubTabs;
  }

  public void setIncludedSubTabs(List<Long> includedSubTabs) {
    this.includedSubTabs = includedSubTabs;
  }

  public Map<String, Object> getParameterMapping() {
    return parameterMapping;
  }

  public void setParameterMapping(Map<String, Object> parameterMapping) {
    this.parameterMapping = parameterMapping;
  }
}
