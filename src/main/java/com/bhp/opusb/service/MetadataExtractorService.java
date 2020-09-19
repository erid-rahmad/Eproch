package com.bhp.opusb.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.bhp.opusb.config.integrator.MetadataExtractorIntegrator;
import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.repository.DatabaseMetadataRepository;

import org.hibernate.boot.Metadata;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MetadataExtractorService {

  private final Logger log = LoggerFactory.getLogger(MetadataExtractorService.class);

  @Autowired
  private DatabaseMetadataRepository metadataRepository;

  public List<ADTable> getTables() {
    Metadata metadata = MetadataExtractorIntegrator.INSTANCE.getMetadata();
    return metadata.getEntityBindings().stream()
        .map((persistentClass) -> {
          String entityName = persistentClass.getEntityName();
          ADTable table = new ADTable(persistentClass.getTable().getName());
          Set<ADColumn> columns = getColumns(entityName);
          table.setADColumns(columns);
          return table;
        })
        .collect(Collectors.toList());
  }

  public Set<ADColumn> getColumns(String entityName) {
    Metadata metadata = MetadataExtractorIntegrator.INSTANCE.getMetadata();
    PersistentClass persistentClass = metadata.getEntityBinding(entityName);
    Iterator<?> columnIterator = persistentClass.getTable().getColumnIterator();
    Set<ADColumn> result = new HashSet<>();

    log.info("Getting columns for entityName: {}", entityName);
    while(columnIterator.hasNext()) {
      Column column = (Column) columnIterator.next();
      String name = column.getName();
      printColumnInfo(column);
      ADColumn adColumn = new ADColumn(name);
      result.add(adColumn);
    }
    return result;
  }

  private void printColumnInfo(Column column) {
    log.info("> Column info[name: {}, length: {}, precision: {}, scale: {}, class: {}]", column.getName(), column.getLength(),
        column.getPrecision(), column.getScale(), column.getClass());
  }

  /**
   * Not activated users should be automatically deleted after 3 days.
   * <p>
   * This is scheduled to get fired everyday, at 01:00 (am).
   */
  @Scheduled(fixedRate = 3600000)
  @Transactional
  public void synchronizeTablesAndColumns() {
    metadataRepository.synchronizeTables();
  }

}