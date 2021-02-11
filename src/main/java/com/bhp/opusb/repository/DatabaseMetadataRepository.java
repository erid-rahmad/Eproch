package com.bhp.opusb.repository;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.ADReference;
import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.repository.util.ColumnTypeMapper;
import com.google.common.base.CaseFormat;

import org.hibernate.engine.spi.SessionImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseMetadataRepository {

  private static final Logger log = LoggerFactory.getLogger(DatabaseMetadataRepository.class);

  @PersistenceContext
  private EntityManager entityManager;

  private final ADTableRepository adTableRepository;
  private final ADColumnRepository adColumnRepository;

  public DatabaseMetadataRepository(ADTableRepository adTableRepository, ADColumnRepository adColumnRepository) {
    this.adTableRepository = adTableRepository;
    this.adColumnRepository = adColumnRepository;
  }

  public List<ADTable> synchronizeTables(String name) {
    final SessionImplementor sessionImplementor = (SessionImplementor) entityManager.getDelegate();
    final List<ADTable> result = new ArrayList<>();
    final ADOrganization organization = new ADOrganization();
    final ADReference reference = new ADReference();

    organization.setId(1L);
    reference.setId(7L);

    try {
      DatabaseMetaData metaData = sessionImplementor.connection().getMetaData();
      String searchCriteria = null;

      if (name != null) {
        final boolean uppercasedIdentifiers = metaData.storesUpperCaseIdentifiers();
        searchCriteria = uppercasedIdentifiers ? name.toUpperCase() : name;
      }

      try (ResultSet rs = metaData.getTables(null, null, searchCriteria, new String[] { "TABLE", "VIEW" })) {
        while (rs.next()) {
          String tableName = rs.getString("TABLE_NAME");
          ADTable table = new ADTable();
          table.name(tableName).setCreatedDate(null);
          table.setLastModifiedDate(null);

          log.debug("Find table {}", tableName);
          Optional<ADTable> record = adTableRepository.findFirstByName(tableName);

          if (record.isPresent()) {
            log.debug("table exists");
            synchronizeColumns(record.get(), reference, metaData);
          } else {
            log.debug("table doesn't exists");
            String type = rs.getString("TABLE_TYPE");
            table.adOrganization(organization).view(type.equals("VIEW")).active(true);
            synchronizeColumns(table, reference, metaData);
            adTableRepository.save(table);
            adColumnRepository.saveAll(table.getADColumns());
            result.add(table);
          }

        }
      }
    } catch (SQLException e) {
      log.warn("Failed getting table metadata. {}", e.getLocalizedMessage());
    }
    return result;
  }

  public void synchronizeColumns(ADTable table, ADReference reference, DatabaseMetaData metaData) {

    try (ResultSet columns = metaData.getColumns(null, null, table.getName(), null);
        ResultSet keys = metaData.getPrimaryKeys(null, null, table.getName());
        ResultSet importedKeys = metaData.getImportedKeys(null, null, table.getName())) {

      final Set<String> primaryKeys = new HashSet<>();
      final Map<String, String> parentTables = new HashMap<>();
      final Map<String, String> parentColumns = new HashMap<>();
      final Set<String> foreignColumns = new HashSet<>();

      // Determines the primary keys.
      while (keys.next()) {
        primaryKeys.add(keys.getString("COLUMN_NAME"));
      }

      // Determines the foreign keys.
      while (importedKeys.next()) {
        String pkTableName = importedKeys.getString("PKTABLE_NAME");
        String pkColumnName = importedKeys.getString("PKCOLUMN_NAME");
        String fkColumnName = importedKeys.getString("FKCOLUMN_NAME");

        if (pkTableName != null) {
          parentTables.put(fkColumnName, pkTableName);
        }
        if (pkColumnName != null) {
          parentColumns.put(fkColumnName, pkColumnName);
        }
        if (fkColumnName != null) {
          foreignColumns.add(fkColumnName);
        }
      }

      while (columns.next()) {
        String columnName = columns.getString("COLUMN_NAME");
        String camelCasedName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName);
        int dataType = columns.getInt("DATA_TYPE");
        long columnSize = columns.getInt("COLUMN_SIZE");
        boolean nullable = columns.getInt("NULLABLE") > DatabaseMetaData.attributeNoNulls;
        boolean foreignKey = foreignColumns.contains(columnName);
        ADColumn column = new ADColumn().name(camelCasedName).sqlName(columnName);

        // This is the existing table.
        if (table.getId() != null) {
          Optional<ADColumn> existingColumn = table.getADColumns().stream()
            .filter(col ->
              (col.getName() != null && col.getName().equals(camelCasedName))
                || (col.getSqlName() != null && col.getSqlName().equals(columnName))
            )
            .findFirst();

          if (existingColumn.isPresent()) {
            log.debug("Update existing column {} - {}", columnName, camelCasedName);
            column = existingColumn.get();

            if (! column.getName().equals(camelCasedName)) {
              column.setName(camelCasedName);
            }

            if (! column.getSqlName().equals(columnName)) {
              column.setSqlName(columnName);
            }

            if (! foreignKey) {
              column.foreignKey(false).importedTable(null).importedColumn(null);
            }
          } else {
            log.debug("Add column {} - {} into the existing table {}", columnName, camelCasedName, table.getName());
            initColumn(table, column);
            adColumnRepository.save(column);
          }
        } else {
          log.debug("Add column {} - {} into table {}", columnName, camelCasedName, table.getName());
          initColumn(table, column);
        }
        
        column
          .fieldLength(columnSize)
          .type(ColumnTypeMapper.getColumnType(dataType))
          .key(primaryKeys.contains(column.getName()))
          .mandatory(!nullable)
          .active(true);

        if (foreignKey) {
          log.debug("Adding foreign key metadata.");
          column.foreignKey(true)
            .importedTable(parentTables.get(columnName))
            .importedColumn(parentColumns.get(columnName));

          if (column.getAdReference() == null) {
            column.setAdReference(reference);
          }
        }
      }
    } catch (SQLException e) {
      log.warn("Failed getting column metadata. {}", e.getLocalizedMessage());
    }
  }

  private void initColumn(ADTable table, ADColumn column) {
    table.addADColumn(column);
    column.adOrganization(table.getAdOrganization())
        .name(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, column.getSqlName()))
        .updatable( ! isSystemFields(column.getSqlName()) );
  }

  private static final Set<String> SYSTEM_FIELDS = new HashSet<>(
      Arrays.asList("id", "uid", "created_by", "created_date", "last_modified_by", "last_modified_date"));

  private boolean isSystemFields(String fieldName) {
    return SYSTEM_FIELDS.contains(fieldName);
  }

}