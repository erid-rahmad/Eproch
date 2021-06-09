package com.bhp.opusb.web.websocket;

import com.bhp.opusb.service.dto.MAuctionDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class AuctionService {
  
  private static final Logger log = LoggerFactory.getLogger(AuctionService.class);

  private final SimpMessageSendingOperations messagingTemplate;

  public AuctionService(SimpMessageSendingOperations messagingTemplate) {
      this.messagingTemplate = messagingTemplate;
  }

  public void publish(MAuctionDTO payload) {
    log.debug("Send an auction update: {}", payload);
    messagingTemplate.convertAndSend("/topic/auction/" + payload.getId(), payload);
  }
}
