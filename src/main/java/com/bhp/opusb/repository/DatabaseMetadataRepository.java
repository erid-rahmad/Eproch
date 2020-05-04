package com.bhp.opusb.repository;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bhp.opusb.domain.ADClient;
import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.repository.util.ColumnTypeMapper;

import org.hibernate.engine.spi.SessionImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

          if (!adTableRepository.exists(Example.of(table))) {
            String type = rs.getString("TABLE_TYPE");
            table.adClient(client).adOrganization(organization).view(type.equals("VIEW")).active(true);
            synchronizeColumns(table, metaData, client, organization);
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

  public void synchronizeColumns(ADTable table, DatabaseMetaData metaData, ADClient client,
      ADOrganization organization) {

    try (ResultSet columns = metaData.getColumns(null, null, table.getName(), null);
        ResultSet keys = metaData.getPrimaryKeys(null, null, table.getName())) {

      Set<String> primaryKeys = new HashSet<>();
      while (keys.next()) {
        primaryKeys.add(keys.getString("COLUMN_NAME"));
      }

      while (columns.next()) {
        int dataType = columns.getInt("DATA_TYPE");
        String typeName = columns.getString("TYPE_NAME");
        int columnSize = columns.getInt("COLUMN_SIZE");
        int decimalDigits = columns.getInt("DECIMAL_DIGITS");
        boolean nullable = columns.getString("IS_NULLABLE").equals("YES");
        ADColumn column = new ADColumn();
        column.adClient(client)
          .adOrganization(organization)
          .name(columns.getString("COLUMN_NAME"))
          .type(ColumnTypeMapper.getColumnType(dataType))
          .key(primaryKeys.contains(column.getName()))
          .mandatory(!nullable)
          .active(true);

        table.addADColumn(column);
      }
    } catch (SQLException e) {
      log.warn("Failed getting column metadata. {}", e.getLocalizedMessage());
    }
  }

}