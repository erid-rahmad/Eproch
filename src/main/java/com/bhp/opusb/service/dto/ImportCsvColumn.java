package com.bhp.opusb.service.dto;

import java.io.Serializable;

import com.bhp.opusb.domain.enumeration.ADColumnType;

public class ImportCsvColumn implements Serializable {
  
  private static final long serialVersionUID = 1L;
  private String sourceName;
  private String targetName;
  private ADColumnType type;

  public ImportCsvColumn() {
  }

  public ImportCsvColumn(String targetName, ADColumnType type) {
    this.targetName = targetName;
    this.type = type;
  }

  public String getSourceName() {
    return sourceName;
  }

  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }

  public String getTargetName() {
    return targetName;
  }

  public void setTargetName(String targetName) {
    this.targetName = targetName;
  }

  public ADColumnType getType() {
    return type;
  }

  public void setType(ADColumnType type) {
    this.type = type;
  }
}
