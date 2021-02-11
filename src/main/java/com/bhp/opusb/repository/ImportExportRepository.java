package com.bhp.opusb.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.util.SqlBuilder;
import com.bhp.opusb.web.rest.errors.UserNotAuthorizedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

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
    for (Map<String,Object> record : records) {
      buildSql(tableName, record);
    }
  }

  public long generateSequence() {
    return jdbcTemplate.queryForObject(SQL_SELECT_SEQUENCE, Long.class);
  }

  /**
   * Find a record or create a new one if not found.
   * @param tableName
   * @param values
   * @return
   */
  public long findOrCreate(String tableName, Map<String, Object> values) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    SqlBuilder builder;
    long id = 0;
    Map<String, Object> keyFields = new HashMap<>();

    for (Map.Entry<String,Object> entry : values.entrySet()) {
      if (isUniqueField(entry.getKey())) {
        keyFields.put(entry.getKey(), entry.getValue());
        break;
      }
    }

    try {
      builder = SqlBuilder
        .selectFrom(tableName)
        .where(keyFields)
        .build();

      log.debug("Find a {} record with the following criteria: {}", tableName, values);
      id = jdbcTemplate.queryForObject(builder.getSql(), builder.getParameters(), Long.class);
    } catch (DataAccessException e) {

      // Create new record with the new generated ID.
      id = generateSequence();

      if ( ! tableName.equals("ad_user")) {
        values.put("id", id);
      }

      if ( ! tableName.startsWith("jhi_")) {
        if ( ! tableName.equals("ad_organization") && ! values.containsKey("ad_organization_id")) {
          values.put("ad_organization_id", properties.getDefaultOrganizationId());
        }

        values.put("active", true);
        values.put("uid", UUID.randomUUID());
      } else if (tableName.equals("jhi_user")) {
        values.put("created_by", authentication.getName());
        values.put("created_date", LocalDateTime.now());
      }

      builder = SqlBuilder
        .insertInto(tableName)
        .values(values)
        .build();

      log.debug("Create a new {} record: {}", tableName, values);
      jdbcTemplate.update(builder.getSql(), builder.getParameters());
    }

    return id;
  }

  /**
   * Insert or update the batch or records.
   * @param sql
   * @param records
   */
  public void save(String sql, List<Map<Integer, Object>> records) {
    jdbcTemplate.batchUpdate(sql, records, 10, (ps, args) -> {
      for (Map.Entry<Integer, Object> entry : args.entrySet()) {
        ps.setObject(entry.getKey(), entry.getValue());
      }
    });
  }

  private void buildSql(String tableName, Map<String, Object> record) {
    final Map<String, Map<String, Object>> linkedTableMap = new HashMap<>();
    final Map<String, Object> tableFields = new HashMap<>();

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
        final Map<String, Object> linkedTableRecord = linkedTableMap.computeIfAbsent(linkedKeys[1], mapKey -> new HashMap<>());

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

    findOrCreate(tableName, tableFields);
  }

  private boolean hasUniqueFields(Map<String, Object> fields) {
    return fields.entrySet().stream().anyMatch(entry -> isUniqueField(entry.getKey()));
  }

  private boolean isUniqueField(String fieldName) {
    return fieldName.equals("code") || fieldName.equals("name") || fieldName.equals("value")
        || fieldName.equals("login") || fieldName.equals("email");
  }
}
