package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Map;

public class ImportParameterDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String tableName;
  private String delimiter = ",";
  private String lineSeparator = "\n";
  private long maxRows = -1;
  private Map<String, ImportCsvColumn> fieldsMap;

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getDelimiter() {
    return delimiter;
  }

  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
  }

  public String getLineSeparator() {
    return lineSeparator;
  }

  public void setLineSeparator(String lineSeparator) {
    this.lineSeparator = lineSeparator;
  }
  
  public long getMaxRows() {
    return maxRows;
  }

  public void setMaxRows(long maxRows) {
    this.maxRows = maxRows;
  }

  public Map<String, ImportCsvColumn> getFieldsMap() {
    return fieldsMap;
  }

  public void setFieldsMap(Map<String, ImportCsvColumn> fieldsMap) {
    this.fieldsMap = fieldsMap;
  }

  @Override
  public String toString() {
    return "ImportParameterDTO [delimiter=" + delimiter +
      ", lineSeparator=" + lineSeparator +
      ", maxRows=" + maxRows +
      ", tableName=" + tableName +
    "]";
  }

}
