package com.bhp.opusb.web.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DashboardService {

  private static final Logger log = LoggerFactory.getLogger(DashboardService.class);
  public static final String TOPIC_DASHBOARD = "/topic/dashboard";

  @SendTo(TOPIC_DASHBOARD)
  public String refreshDashboard(@Payload String payload) {
    log.debug("Refreshing dashboard due to event: {}", payload);
    return payload;
  }
  
}
