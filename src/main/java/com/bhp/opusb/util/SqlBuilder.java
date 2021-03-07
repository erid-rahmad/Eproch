package com.bhp.opusb.util;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import com.bhp.opusb.util.ImportExportUtil.CsvHeaderMeta;
import com.google.common.base.CaseFormat;

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

  private static final Map<String, String> OPERATOR_MAPPING;
  static {
    Map<String, String> operatorMapping = new HashMap<>(5);
    operatorMapping.put("equals", "=");
    operatorMapping.put("greaterThan", ">");
    operatorMapping.put("greaterOrEqualThan", ">=");
    operatorMapping.put("lessThan", "<");
    operatorMapping.put("lessOrEqualThan", "<=");
    OPERATOR_MAPPING = Collections.unmodifiableMap(operatorMapping);
  }

  public static final String JOIN_INNER = "INNER JOIN";
  public static final String JOIN_LEFT = "LEFT JOIN";

  public SqlBuilder(String tableName, StatementType type) {
    this.tableName = tableName;
    this.mode = type;

    if (type.equals(StatementType.INSERT)) {
      insertColumns = new StringBuilder("INSERT INTO " + tableName + " ( ");
      insertValues = new StringBuilder("VALUES ( ");
    } else if (type.equals(StatementType.SELECT)) {
      selectStatement = new StringBuilder("SELECT ");
      fromClause = new StringBuilder(" FROM ").append(tableName);
    }
  }

  public static List<Object> buildParameters(Map<String, Object> values) {
    final List<Object> parameters = new ArrayList<>();
    for (Map.Entry<String, Object> entry : values.entrySet()) {
      parameters.add(entry.getValue());
    }
    return parameters;
  }

  public static SqlBuilder insertInto(String tableName) {
    return new SqlBuilder(tableName, StatementType.INSERT);
  }

  public static SqlBuilder selectFrom(String tableName, String ...fields) {
    return new SqlBuilder(tableName, StatementType.SELECT)
      .select(fields);
  }

  public static SqlBuilder selectFrom(String tableName, List<CsvHeaderMeta> fields) {
    return new SqlBuilder(tableName, StatementType.SELECT)
      .select(fields);
  }

  public SqlBuilder select(List<CsvHeaderMeta> fields) {
    Set<String> linkedTables = new HashSet<>();
    
    IntStream.range(0, fields.size())
      .filter(index -> fields.get(index).getMasterTable() == null)
      .forEach(index -> {
        final CsvHeaderMeta field = fields.get(index);
        final String baseTable = field.getBaseTable();

        if (selectStatement.length() > 7) {
          selectStatement.append(", ");
        }

        // Join to parent table, if any.
        final String parentTable = field.getParentTable();

        if (parentTable == null) {
          selectStatement.append(field.getBaseTable())
            .append(".").append(field.getBaseColumn());
        } else {
          String baseField = parentTable + "." + field.getParentColumn();
          String key = baseTable + "." + parentTable;

          selectStatement.append(baseField);

          if (! linkedTables.contains(key)) {
            linkedTables.add(key);
            fromClause.append(" LEFT JOIN ").append(parentTable)
              .append(" ON (").append(parentTable + ".id = ")
              .append(field.getBaseTable() + "." + field.getBaseColumn()).append(")");
          }
        }
      });
      
    return this;
  }

  public SqlBuilder select(String ...fields) {
    StringBuilder selectClause = new StringBuilder();

    if (fields.length == 0) {
      selectClause.append(tableName + ".id");
    } else {
      int index = 0;

      for (String field : fields) {
        if (index++ > 0) {
          selectClause.append(", ");
        }

        selectClause.append(tableName + "." + field);
      }
    }


    selectStatement.append(selectClause);
    return this;
  }

  public SqlBuilder join(String parentTableName, String tableName, String joinType, String foreignField,
      String... fields) {

    for (String field : fields) {
      selectStatement.append(", " + parentTableName + "." + field);
    }

    fromClause.append(" " + joinType).append(" " + parentTableName + " ON (")
      .append(tableName + "." + foreignField + " = ").append(parentTableName + ".id").append(")");

    return this;
  }

  public SqlBuilder where(Map<String, Object> fields) {
    int index = 0;

    if (fields.isEmpty()) {
      return this;
    }

    whereClause = new StringBuilder(" WHERE ");
    
    for (Map.Entry<String, Object> entry : fields.entrySet()) {
      final String parameter = buildParameter(entry.getKey());
      final Object paramValue = entry.getValue();

      if (parameter.isEmpty()) {
        continue;
      }

      Object value;
 
      // TODO Check date field from its metadata.
      if (parameter.contains("date")) {
        value = Date.valueOf(LocalDate.parse((String) paramValue));
      } else if (parameter.contains("_id")) {
        value = Long.parseLong((String) paramValue);
      } else if (paramValue.equals("true") || paramValue.equals("false")) {
        value = Boolean.valueOf((String) paramValue);
      } else {
        value = paramValue;
      }

      if (index++ > 0) {
        whereClause.append(" AND ");
      }

      whereClause.append(parameter);
      parameters.add(value);
    }
    
    return this;
  }

  private String buildParameter(String field) {
    final StringBuilder criteriaBuilder = new StringBuilder();
    String[] fieldParts = field.split("\\.");
    String left = fieldParts[0];  // The left side of the dot.
    String right = fieldParts[1]; // The right side of the dot

    if (left.equals(this.tableName) && ! OPERATOR_MAPPING.containsKey(right)) {
      criteriaBuilder.append(field).append(" = ?");
    } else if (! left.equals(this.tableName) && OPERATOR_MAPPING.containsKey(right)) {
      String operator = OPERATOR_MAPPING.get(right);
      String columnName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, left);
      criteriaBuilder.append(this.tableName).append(".").append(columnName)
        .append(" ").append(operator).append(" ?");
    }

    return criteriaBuilder.toString();
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

  public List<Object> getParameters() {
    return parameters;
  }

  public SqlBuilder build() {
    if (mode.equals(StatementType.INSERT)) {
      sql = insertColumns.append(insertValues).toString();
    } else if (mode.equals(StatementType.SELECT)) {
      selectStatement.append(fromClause);

      if (whereClause != null) {
        selectStatement.append(whereClause);
      }

      sql = selectStatement.toString();
    }

    return this;
  }

  private enum StatementType {
    INSERT, SELECT, UPDATE
  }
}
