package com.bhp.opusb.service.trigger.process;

import java.util.Map;
import java.util.Optional;

import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.ADTab;
import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.repository.ADTabRepository;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.trigger.ProcessTrigger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.vavr.collection.Stream;
import io.vavr.control.Option;

@Service
@Transactional(readOnly = true)
public class TestCallout implements ProcessTrigger {

  private final ADTabRepository adTabRepository;

  public TestCallout(ADTabRepository adTabRepository) {
    this.adTabRepository = adTabRepository;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    long tabId = castToLong(params.get("tabId"));
    Optional<ADTab> tab = adTabRepository.findById(tabId);
    if (tab.isPresent()) {
      ADTable table = tab.get().getAdTable();
      Option<ADColumn> column = Stream.ofAll(table.getADColumns())
        .find(c -> c.getName().equals("id"));

      if (column.isDefined()) {
        return new ProcessResult()
          .add("parentColumnId", column.get().getId());
      }
    }
    return new ProcessResult();
  }

  private long castToLong(Object value) {
    if (value == null) {
      return 0;
    }

    try {
      return (long) value;
    } catch (ClassCastException e) {
      return (int) value;
    }
  }
  
}
