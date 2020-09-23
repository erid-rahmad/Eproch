package com.bhp.opusb.service.trigger.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.ADField;
import com.bhp.opusb.domain.ADTab;
import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.domain.enumeration.ADColumnType;
import com.bhp.opusb.repository.ADFieldRepository;
import com.bhp.opusb.repository.ADTabRepository;
import com.bhp.opusb.repository.ADTableRepository;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.trigger.ProcessTrigger;

import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.vavr.collection.Stream;

@Service
@Transactional
public class FieldGeneratorProcessTrigger implements ProcessTrigger {

  private static final Logger log = LoggerFactory.getLogger(FieldGeneratorProcessTrigger.class);
  private final ADTabRepository tabRepository;
  private final ADTableRepository tableRepository;
  private final ADFieldRepository fieldRepository;

  public FieldGeneratorProcessTrigger(ADTabRepository tabRepository, ADTableRepository tableRepository,
      ADFieldRepository fieldRepository) {
    this.tabRepository = tabRepository;
    this.tableRepository = tableRepository;
    this.fieldRepository = fieldRepository;
  }
  
  @Override
  public TriggerResult run(Map<String, Object> params) {
    long tabId = (int) params.get("adTabId");
    Optional<ADTab> tabRecord = tabRepository.findById(tabId);

    if (! tabRecord.isPresent()) {
      return new ProcessResult().add("error", "Tab with the given id doesn't exists");
    }

    ADTab tab = tabRecord.get();
    ADTable table = tableRepository.getOne(tab.getAdTable().getId());

    Set<ADColumn> columns = table.getADColumns();
    List<ADField> fields = Stream.ofAll(columns)
      .filter(column -> 
        ! Stream.ofAll(tab.getADFields())
          .exists(field -> field.getAdColumn().equals(column))
      )
      .map(column -> {
        boolean systemFields = isSystemFields(column.getSqlName());

        log.debug("Generating field from column {}", column.getName());
        return new ADField()
          .active(true)
          .adOrganization(tab.getAdOrganization())
          .name(resolveFieldName(column.getSqlName()))
          .adTab(tab)
          .adColumn(column)
          .columnNo(1)
          .columnSpan(8)
          .showLabel( ! column.getType().equals(ADColumnType.BOOLEAN))
          .showInDetail( ! systemFields)
          .showInGrid( ! systemFields)
          .writable(true);
      }).toJavaList();

    List<ADField> savedFields;
    if (fields.isEmpty()) {
      savedFields = new ArrayList<>();
    } else {
      savedFields = fieldRepository.saveAll(fields);
    }
    
    return new ProcessResult().add("Fields", savedFields);
  }

  private String resolveFieldName(String columnName) {
    String upperCased = columnName.replace("_", " ");
    return WordUtils.capitalizeFully(upperCased);
  }

  private static final Set<String> SYSTEM_FIELDS = new HashSet<>(
      Arrays.asList("id", "uid", "created_by", "created_date", "last_modified_by", "last_modified_date"));

  private boolean isSystemFields(String fieldName) {
    return SYSTEM_FIELDS.contains(fieldName);
  }

}
