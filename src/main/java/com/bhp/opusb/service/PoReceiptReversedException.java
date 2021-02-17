package com.bhp.opusb.service;

import com.bhp.opusb.service.dto.MVerificationLineDTO;

public class PoReceiptReversedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public PoReceiptReversedException(MVerificationLineDTO line) {
    super("Some line(s) has been already reversed. PO #" + line.getPoNo() + ", item: " + line.getProductName());
  }

}
