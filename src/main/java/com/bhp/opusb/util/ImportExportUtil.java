package com.bhp.opusb.util;

public class ImportExportUtil {

  private ImportExportUtil() {}

  public static CsvHeaderMeta parseCsvHeader(String baseTable, String csvHeader) {
    String[] headerParts = csvHeader.split(">");
    CsvHeaderMeta meta = new CsvHeaderMeta(csvHeader);

    // Base table.
    if (headerParts.length == 1) {
      parseAnnotationSeparatedField(baseTable, headerParts[0], meta);
    }
    
    // Child table.
    else {
      meta.setMasterTable(baseTable);
      parseAnnotationSeparatedField(headerParts[0], headerParts[1], meta);
    }
    
    return meta;
  }

  private static void parseAnnotationSeparatedField(String baseTable, String field, CsvHeaderMeta meta) {
    String[] fieldParts = field.split("@");
    meta.setBaseTable(baseTable);
    meta.setBaseColumn(fieldParts[0]);
    
    if (fieldParts.length == 2) {
      String[] parentParts = fieldParts[1].split("\\.");
      meta.setParentTable(parentParts[0]);
      meta.setParentColumn(parentParts[1]);
    }
  }
  
  public static class CsvHeaderMeta {
    private final String csvHeader;

    private String masterTable;
    private String baseTable;
    private String baseColumn;
    private String parentTable;
    private String parentColumn;
    private String childTable;
    private String childColumn;

    public CsvHeaderMeta(String csvHeader) {
      this.csvHeader = csvHeader;
    }

    public String getCsvHeader() {
      return csvHeader;
    }

    public String getMasterTable() {
      return masterTable;
    }

    public void setMasterTable(String masterTable) {
      this.masterTable = masterTable;
    }

    public String getBaseTable() {
      return baseTable;
    }

    public void setBaseTable(String baseTable) {
      this.baseTable = baseTable;
    }

    public String getBaseColumn() {
      return baseColumn;
    }

    public void setBaseColumn(String baseColumn) {
      this.baseColumn = baseColumn;
    }

    public String getParentTable() {
      return parentTable;
    }

    public void setParentTable(String parentTable) {
      this.parentTable = parentTable;
    }

    public String getParentColumn() {
      return parentColumn;
    }

    public void setParentColumn(String parentColumn) {
      this.parentColumn = parentColumn;
    }

    public String getChildTable() {
      return childTable;
    }

    public void setChildTable(String childTable) {
      this.childTable = childTable;
    }

    public String getChildColumn() {
      return childColumn;
    }

    public void setChildColumn(String childColumn) {
      this.childColumn = childColumn;
    }

    public boolean isChildTable() {
      return masterTable != null;
    }
  }
}
