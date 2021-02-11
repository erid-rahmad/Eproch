package com.bhp.opusb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlBuilder {

  private final StatementType mode;
  private final String tableName;
  private List<Object> parameters = new ArrayList<>();
  private StringBuilder selectStatement;
  private StringBuilder fromClause;
  private StringBuilder whereClause;
  private StringBuilder insertColumns;
  private StringBuilder insertValues;
  private String sql;

  public SqlBuilder(String tableName, StatementType type) {
    this.tableName = tableName;
    this.mode = type;

    if (type.equals(StatementType.INSERT)) {
      insertColumns = new StringBuilder("INSERT INTO " + tableName + " ( ");
      insertValues = new StringBuilder("VALUES ( ");
    } else if (type.equals(StatementType.SELECT)) {
      selectStatement = new StringBuilder("SELECT ");
    }
  }

  public static SqlBuilder insertInto(String tableName) {
    return new SqlBuilder(tableName, StatementType.INSERT);
  }

  public static SqlBuilder selectFrom(String tableName, String ...fields) {
    return new SqlBuilder(tableName, StatementType.SELECT)
      .select(fields);
  }

  public SqlBuilder select(String ...fields) {
    StringBuilder selectClause = new StringBuilder();

    if (fields.length == 0) {
      selectClause.append(" id ");
    } else {
      int index = 0;

      for (String field : fields) {
        if (index++ > 0) {
          selectClause.append(", ");
        }

        selectClause.append(field);
      }
    }

    selectStatement.append(selectClause).append(" FROM " + tableName);
    return this;
  }

  public SqlBuilder where(Map<String, Object> fields) {
    int index = 0;
    whereClause = new StringBuilder(" WHERE ");
    
    for (Map.Entry<String, Object> entry : fields.entrySet()) {
      String fieldName = entry.getKey();
      Object value = entry.getValue();

      if (index++ > 0) {
        whereClause.append(" AND ");
      }

      whereClause.append(fieldName + " = ?");
      parameters.add(value);
    }
    
    return this;
  }

  public SqlBuilder values(Map<String, Object> values) {
    int index = 0;
    
    for (Map.Entry<String, Object> entry : values.entrySet()) {
      String fieldName = entry.getKey();
      Object value = entry.getValue();

      if (index++ > 0) {
        insertColumns.append(", ");
        insertValues.append((", "));
      }

      insertColumns.append(fieldName);
      insertValues.append("?");
      parameters.add(value);
    }

    insertColumns.append(" ) ");
    insertValues.append(" ) ");

    return this;
  }

  public String getSql() {
    return sql;
  }

  public Object[] getParameters() {
    return parameters.toArray();
  }

  public SqlBuilder build() {
    if (mode.equals(StatementType.INSERT)) {
      sql = insertColumns.append(insertValues).toString();
    } else if (mode.equals(StatementType.SELECT)) {
      sql = selectStatement.append(whereClause).toString();
    }
    return this;
  }

  private enum StatementType {
    INSERT, SELECT, UPDATE
  }
}
