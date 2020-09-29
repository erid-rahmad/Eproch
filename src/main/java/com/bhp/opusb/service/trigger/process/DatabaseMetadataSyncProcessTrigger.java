package com.bhp.opusb.service.trigger.process;

import java.util.List;
import java.util.Map;

import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.repository.DatabaseMetadataRepository;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.trigger.ProcessTrigger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DatabaseMetadataSyncProcessTrigger implements ProcessTrigger {

  private final DatabaseMetadataRepository databaseMetadataRepository;
  
  public DatabaseMetadataSyncProcessTrigger(DatabaseMetadataRepository databaseMetadataRepository) {
    this.databaseMetadataRepository = databaseMetadataRepository;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    List<ADTable> tables = databaseMetadataRepository.synchronizeTables();
    return new ProcessResult().add("tables", tables);
  }
}
