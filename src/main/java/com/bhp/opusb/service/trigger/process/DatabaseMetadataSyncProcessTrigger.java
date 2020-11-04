package com.bhp.opusb.service.trigger.process;

import java.util.List;
import java.util.Map;

import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.repository.DatabaseMetadataRepository;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.mapper.ADTableMapper;
import com.bhp.opusb.service.trigger.ProcessTrigger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.vavr.collection.Stream;

@Service
@Transactional
public class DatabaseMetadataSyncProcessTrigger implements ProcessTrigger {

  private final ADTableMapper adTableMapper;
  private final DatabaseMetadataRepository databaseMetadataRepository;
  
  public DatabaseMetadataSyncProcessTrigger(ADTableMapper adTableMapper,
      DatabaseMetadataRepository databaseMetadataRepository) {
    this.adTableMapper = adTableMapper;
    this.databaseMetadataRepository = databaseMetadataRepository;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    String tableName = (String) params.get("tableName");
    List<ADTable> tables = databaseMetadataRepository.synchronizeTables(tableName);
    return new ProcessResult().add("tables", Stream.ofAll(tables).map(adTableMapper::toDto).asJava());
  }
}
