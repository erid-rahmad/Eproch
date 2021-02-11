package com.bhp.opusb.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.bhp.opusb.config.integrator.MetadataExtractorIntegrator;
import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.ADTable;

import org.hibernate.boot.Metadata;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MetadataExtractorService {

  private final Logger log = LoggerFactory.getLogger(MetadataExtractorService.class);

  public List<ADTable> getTables() {
    Metadata metadata = MetadataExtractorIntegrator.INSTANCE.getMetadata();
    return metadata.getEntityBindings().stream()
        .map((persistentClass) -> {
          String entityName = persistentClass.getEntityName();
          ADTable table = new ADTable(persistentClass.getTable().getName());
          table.setADColumns(getColumns(entityName));
          return table;
        })
        .collect(Collectors.toList());
  }

  public List<ADColumn> getColumns(String entityName) {
    Metadata metadata = MetadataExtractorIntegrator.INSTANCE.getMetadata();
    PersistentClass persistentClass = metadata.getEntityBinding(entityName);
    Iterator<?> columnIterator = persistentClass.getTable().getColumnIterator();
    List<ADColumn> result = new ArrayList<>();

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

}