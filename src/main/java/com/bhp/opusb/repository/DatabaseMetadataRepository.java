package com.bhp.opusb.repository;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bhp.opusb.domain.ADClient;
import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.repository.util.ColumnTypeMapper;
import com.google.common.base.CaseFormat;

import org.hibernate.engine.spi.SessionImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseMetadataRepository {

  private static final Logger log = LoggerFactory.getLogger(DatabaseMetaData.class);

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private ADTableRepository adTableRepository;

  @Autowired
  private ADColumnRepository adColumnRepository;

  public List<ADTable> synchronizeTables() {
    SessionImplementor sessionImplementor = (SessionImplementor) entityManager.getDelegate();
    DatabaseMetaData metaData = null;
    List<ADTable> result = new ArrayList<>();

    try {
      metaData = sessionImplementor.connection().getMetaData();
      try (ResultSet rs = metaData.getTables(null, null, null, new String[] { "TABLE", "VIEW" })) {
        ADClient client = new ADClient();
        ADOrganization organization = new ADOrganization();
        client.setId(1L);
        organization.setId(1L);
        while (rs.next()) {
          String tableName = rs.getString("TABLE_NAME");
          ADTable table = new ADTable();
          table.name(tableName).setCreatedDate(null);
          table.setLastModifiedDate(null);

          log.debug("Find table {}", tableName);
          List<ADTable> record = adTableRepository.findByName(tableName);

          if (record.isEmpty()) {
            log.debug("table doesn't exists");
            String type = rs.getString("TABLE_TYPE");
            table.adClient(client).adOrganization(organization).view(type.equals("VIEW")).active(true);
            synchronizeColumns(table, metaData);
            adTableRepository.save(table);
            adColumnRepository.saveAll(table.getADColumns());
            result.add(table);
          } else {
            log.debug("table exists");
            synchronizeColumns(record.get(0), metaData);
          }

        }
      }
    } catch (SQLException e) {
      log.warn("Failed getting table metadata. {}", e.getLocalizedMessage());
    }
    return result;
  }

  public void synchronizeColumns(ADTable table, DatabaseMetaData metaData) {

    try (ResultSet columns = metaData.getColumns(null, null, table.getName(), null);
        ResultSet keys = metaData.getPrimaryKeys(null, null, table.getName());
        ResultSet importedKeys = metaData.getImportedKeys(null, null, table.getName())) {

      Set<String> primaryKeys = new HashSet<>();
      Map<String, String> parentTables = new HashMap<>();
      Map<String, String> parentColumns = new HashMap<>();
      Set<String> foreignColumns = new HashSet<>();

      // Determines the primary keys.
      while (keys.next()) {
        primaryKeys.add(keys.getString("COLUMN_NAME"));
      }

      // Determines the foreign keys.
      while (importedKeys.next()) {
        String pkTableName = importedKeys.getString("PKTABLE_NAME");
        String pkColumnName = importedKeys.getString("PKCOLUMN_NAME");
        String fkColumnName = importedKeys.getString("FKCOLUMN_NAME");
        log.debug("pkTable: {}", pkTableName);
        log.debug("pkColumn: {}", pkTableName);
        log.debug("fkColumn: {}", fkColumnName);

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
        int dataType = columns.getInt("DATA_TYPE");
        // String typeName = columns.getString("TYPE_NAME");
        long columnSize = columns.getInt("COLUMN_SIZE");
        // int decimalDigits = columns.getInt("DECIMAL_DIGITS");
        boolean nullable = columns.getString("IS_NULLABLE").equals("YES");
        boolean foreignKey = foreignColumns.contains(columnName);
        ADColumn column = new ADColumn().sqlName(columnName);

        // This is the existing table.
        if (table.getId() != null) {
          Optional<ADColumn> existingColumn = table.getADColumns().stream()
            .filter((col) -> col.getSqlName().equals(columnName))
            .findFirst();

          if (existingColumn.isPresent()) {
            log.debug("Update existing column {}", columnName);
            column = existingColumn.get();

            if (!foreignKey) {
              column.foreignKey(false).importedTable(null).importedColumn(null);
            }
          } else {
            log.debug("Add column {} into existing table {}", columnName, table.getName());
            initColumn(table, column);
            adColumnRepository.save(column);
          }
        } else {
          log.debug("Add column {} into table {}", columnName, table.getName());
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
        }
      }
    } catch (SQLException e) {
      log.warn("Failed getting column metadata. {}", e.getLocalizedMessage());
    }
  }

  private void initColumn(ADTable table, ADColumn column) {
    table.addADColumn(column);
    column.adClient(table.getAdClient()).adOrganization(table.getAdOrganization())
        .name(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_CAMEL, column.getSqlName()));
  }

}