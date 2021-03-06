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
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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
  private final CacheManager cacheManager;

  public FieldGeneratorProcessTrigger(ADTabRepository tabRepository, ADTableRepository tableRepository,
      ADFieldRepository fieldRepository, CacheManager cacheManager) {
    this.tabRepository = tabRepository;
    this.tableRepository = tableRepository;
    this.fieldRepository = fieldRepository;
    this.cacheManager = cacheManager;
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
      // Exclude system fields, virtual columns, and any existing fields.
      .filter(column -> !isSystemFields(column.getSqlName())
          && !Stream.ofAll(tab.getADFields())
            .exists(field -> field.getVirtualColumnName() == null && field.getAdColumn().equals(column)))
      .map(column -> {
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
          .showInDetail(true)
          .showInGrid(true)
          .writable(true);
      }).toJavaList();

    List<ADField> savedFields;
    if (fields.isEmpty()) {
      savedFields = new ArrayList<>();
    } else {
      savedFields = fieldRepository.saveAll(fields);
      Optional.ofNullable(cacheManager.getCache("com.bhp.opusb.domain.ADTab.aDFields"))
        .ifPresent(Cache::clear);
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
