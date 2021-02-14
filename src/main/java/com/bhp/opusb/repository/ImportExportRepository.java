package com.bhp.opusb.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.security.SecurityUtils;
import com.bhp.opusb.util.SqlBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import io.vavr.control.Either;

@Repository
public class ImportExportRepository {

  private static final Logger log = LoggerFactory.getLogger(ImportExportRepository.class);

  private final JdbcTemplate jdbcTemplate;
  private final ApplicationProperties properties;

  private static final String SQL_SELECT_SEQUENCE = "SELECT nextval('sequence_generator')";

  public ImportExportRepository(JdbcTemplate jdbcTemplate, ApplicationProperties properties) {
    this.jdbcTemplate = jdbcTemplate;
    this.properties = properties;
  }

  public void importData(String tableName, List<Map<String, Object>> records) {
    importData(tableName, records, false);
  }

  /**
   * Don't attempt to lookup for the existing record.
   * @param tableName
   * @param records
   * @param insertOnly
   */
  public void importData(String tableName, List<Map<String, Object>> records, boolean insertOnly) {
    boolean parametersOnly = false;
    String sql = null;
    List<List<Object>> data = new ArrayList<>(insertOnly ? records.size() : 10);

    for (Map<String, Object> record : records) {
      Either<SqlBuilder, List<Object>> either = buildSql(tableName, record, insertOnly, parametersOnly);

      if (either.isLeft()) {
        SqlBuilder sqlBuilder = either.getLeft();
        sql = sqlBuilder.getSql();
        data.add(sqlBuilder.getParameters());
        parametersOnly = true;
      } else if (either.isRight()) {
        List<Object> item = either.get();

        if (item != null) {
          data.add(either.get());
        }
      }
    }

    if (sql != null) {
      save(sql, data);
    }
  }

  public long generateSequence() {
    return jdbcTemplate.queryForObject(SQL_SELECT_SEQUENCE, Long.class);
  }

  public long findOne(String tableName, Map<String, Object> values) {
    long id = 0;
    Map<String, Object> keyFields = new HashMap<>();

    for (Map.Entry<String, Object> entry : values.entrySet()) {
      if ((tableName.equals("c_product") && entry.getKey().equals("name"))
          || (!tableName.equals("c_product") && isUniqueField(entry.getKey()))) {
        keyFields.put(entry.getKey(), entry.getValue());
        break;
      }
    }

    if (keyFields.isEmpty()) {
      log.warn("There is no index key for looking up the record");
      return id;
    }

    try {
      SqlBuilder builder = SqlBuilder
        .selectFrom(tableName)
        .where(keyFields)
        .build();

      id = jdbcTemplate.queryForObject(builder.getSql(), builder.getParameters().toArray(), Long.class);
    } catch (DataAccessException e) {
      log.info("Record not found.");
    }

    return id;
  }

  /**
   * Find a record or create a new one if not found.
   * @param tableName
   * @param values
   * @return
   */
  public long findOrCreate(String tableName, Map<String, Object> values) {
    SqlBuilder builder;
    long id = findOne(tableName, values);

    if (id == 0) {
      String login = SecurityUtils.getCurrentUserLogin().orElseGet(() -> "system");
      
      // Create new record with the new generated ID.
      addGeneratedId(tableName, values);
      id = (long) values.get("id");

      if ( ! tableName.startsWith("jhi_")) {
        if ( ! tableName.equals("ad_organization") && ! values.containsKey("ad_organization_id")) {
          values.put("ad_organization_id", properties.getDefaultOrganizationId());
        }

        values.put("active", true);
        values.put("uid", UUID.randomUUID());
        values.put("created_by", login);
      } else if (tableName.equals("jhi_user")) {
        values.put("created_by", login);
        values.put("created_date", LocalDateTime.now());
      }

      builder = SqlBuilder
        .insertInto(tableName)
        .values(values)
        .build();

      log.debug("Create a new {} record: {}", tableName, values);
      jdbcTemplate.update(builder.getSql(), builder.getParameters().toArray());
    }

    return id;
  }

  public Either<SqlBuilder, List<Object>> buildInsertStatement(String tableName, Map<String, Object> values, boolean parametersOnly) {
    if (parametersOnly) {
      return Either.right(SqlBuilder.buildParameters(values));
    }

    return Either.left(SqlBuilder
      .insertInto(tableName)
      .values(values)
      .build()
    );
  }

  /**
   * Insert or update the batch or records.
   * @param sql
   * @param records
   */
  public void save(String sql, List<List<Object>> records) {
    log.debug("Batch save the records. total: {}", records.size());
    jdbcTemplate.batchUpdate(sql, records, 20, (ps, fields) -> {
      for (int i = 0; i < fields.size(); ++i) {
        ps.setObject(i + 1, fields.get(i));
      }
    });
  }

  /**
   * Build either SQL or just the parameters.
   * @param tableName
   * @param record
   * @param insertOnly
   * @param parametersOnly
   * @return
   */
  private Either<SqlBuilder, List<Object>> buildSql(String tableName, Map<String, Object> record, boolean insertOnly, boolean parametersOnly) {
    final Map<String, Map<String, Object>> linkedTableMap = new LinkedHashMap<>();
    final Map<String, Object> tableFields = new LinkedHashMap<>();

    for (Map.Entry<String, Object> entry : record.entrySet()) {
      final String key = entry.getKey();
      final Object value = entry.getValue();

      if (key.contains(".")) {
        if (value == null) {
          continue;
        }

        final int separatorIndex = key.indexOf(".");
        final String linkedTableKey = key.substring(0, separatorIndex);
        final String[] linkedKeys = linkedTableKey.split("@");
        final String linkedTableField = key.substring(separatorIndex + 1);
        final String temporaryKey = "#" + linkedKeys[1];
        final Map<String, Object> linkedTableRecord = linkedTableMap.computeIfAbsent(linkedKeys[1], mapKey -> new LinkedHashMap<>());

        linkedTableRecord.put(linkedTableField, value);
        tableFields.computeIfAbsent(temporaryKey, tmp -> linkedKeys[0]);
      } else {
        tableFields.put(key, value);
      }
    }

    linkedTableMap.forEach((table, data) -> {
      if (hasUniqueFields(data)) {
        long id = findOrCreate(table, data);
        String linkedFieldName = (String) tableFields.get("#" + table);
        tableFields.put(linkedFieldName, id);
      }
      
      tableFields.remove("#" + table);
    });

    if (insertOnly) {
      // No need to check for the existing record.
      addGeneratedId(tableName, tableFields);
      return buildInsertStatement(tableName, tableFields, parametersOnly);
    } else {
      // Ignore or update the existing record.
      long id = findOne(tableName, tableFields);

      if (id == 0) {
        addGeneratedId(tableName, tableFields);
        return buildInsertStatement(tableName, tableFields, parametersOnly);
      }

      // TODO Provide the option to either ignore or update the existing record.
      // Currently, ignore any existing record.
      return Either.right(null);
    }
  }

  private void addGeneratedId(String tableName, Map<String, Object> fieldsMap) {
    if ( ! tableName.equals("ad_user")) {
      fieldsMap.put("id", generateSequence());
    }
  }

  /**
   * Checks whether the map of fields contains unique fields.
   */
  private boolean hasUniqueFields(Map<String, Object> fields) {
    return fields.entrySet().stream().anyMatch(entry -> isUniqueField(entry.getKey()));
  }

  /**
   * Check whether the given fieldName is considered unique.
   * @param fieldName
   * @return
   */
  private boolean isUniqueField(String fieldName) {
    return fieldName.equals("id") || fieldName.equals("code") || fieldName.equals("name") || fieldName.equals("value")
        || fieldName.equals("login") || fieldName.equals("email");
  }
}
