package com.bhp.opusb.web.websocket;

import com.bhp.opusb.web.websocket.dto.NotificationDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationService {
  
  private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

  private final SimpMessageSendingOperations messagingTemplate;

  public NotificationService(SimpMessageSendingOperations messagingTemplate) {
      this.messagingTemplate = messagingTemplate;
  }

  @SubscribeMapping("/queue/notification/{userName}")
  public NotificationDTO subscribeNotification(@DestinationVariable String userName, @Payload NotificationDTO payload) {
    log.debug("subscribeNotification. userName: {}, payload: {}", userName, payload);
    return payload;
  }

  public void notify(NotificationDTO payload) {
    log.debug("Send a notification: {}", payload);
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    final String userName = authentication.getName();
    messagingTemplate.convertAndSend("/queue/notification/" + userName, payload);
  }
}
