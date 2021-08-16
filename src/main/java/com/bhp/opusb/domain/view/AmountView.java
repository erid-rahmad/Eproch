package com.bhp.opusb.domain.view;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public interface AmountView {
  
  BigDecimal getMinPrice();

  BigDecimal getMaxPrice();
}
