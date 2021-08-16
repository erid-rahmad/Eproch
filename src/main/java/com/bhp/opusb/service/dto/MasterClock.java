package com.bhp.opusb.service.dto;

import java.time.Instant;

public class MasterClock {
  private final Instant currentTime = Instant.now();

  public Instant getCurrentTime() {
    return currentTime;
  }
}
