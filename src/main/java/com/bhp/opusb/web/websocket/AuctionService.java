package com.bhp.opusb.web.websocket;

import java.util.HashMap;
import java.util.Map;

import com.bhp.opusb.service.dto.MAuctionDTO;
import com.bhp.opusb.service.dto.MAuctionEventLogDTO;
import com.bhp.opusb.service.dto.MAuctionItemDTO;
import com.bhp.opusb.service.dto.MAuctionSubmissionItemDTO;

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

  public void publish(MAuctionEventLogDTO eventLogDTO, MAuctionDTO payload) {
    publish(eventLogDTO, payload, null, null);
  }

  public void publish(MAuctionEventLogDTO eventLogDTO, MAuctionDTO payload, MAuctionItemDTO item) {
    publish(eventLogDTO, payload, item, null);
  }

  public void publish(MAuctionEventLogDTO eventLogDTO, MAuctionDTO auction, MAuctionItemDTO item, MAuctionSubmissionItemDTO submittedItem) {
    log.debug("Send an auction update: {}", auction);
    Map<String, Object> payload = new HashMap<>(4);
    payload.put("log", eventLogDTO);
    payload.put("auction", auction);

    if (item != null) {
      payload.put("lot", item);
    }

    if (submittedItem != null) {
      payload.put("bid", submittedItem);
    }
    
    messagingTemplate.convertAndSend("/topic/auction/" + auction.getId(), payload);
  }
}
