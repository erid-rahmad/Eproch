package com.bhp.opusb.service.trigger.process;

import java.math.BigDecimal;
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
public class GetAmountValue implements ProcessTrigger {

  private final ADTabRepository adTabRepository;

  public GetAmountValue(ADTabRepository adTabRepository) {
    this.adTabRepository = adTabRepository;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    long qty = castToLong(params.get("qty"));
    double priceActual = castToDouble(params.get("priceActual"));
    
    double taxAmount = castToDouble((priceActual*10)/100);
    double totalAmount = castToDouble(qty*priceActual);

    System.out.println("QTY : "+qty);
    System.out.println("PRICE ACTUAL : "+priceActual);

    System.out.println("TAX AMOUNT : "+taxAmount);
    System.out.println("TOTAL AMOUNT : "+totalAmount);
    
    return new ProcessResult()
    .add("taxAmount", taxAmount)
    .add("totalAmount", totalAmount);
    
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

  private double castToDouble(Object value) {
    if (value == null) {
      return 0;
    }

    try {
      return (double) value;
    } catch (ClassCastException e) {
      return (int) value;
    }
  }
  
}
