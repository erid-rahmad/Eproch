package com.bhp.opusb.service.trigger.process;

import java.util.Map;

import com.bhp.opusb.repository.MAuctionRepository;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.mapper.MAuctionMapper;
import com.bhp.opusb.service.trigger.ProcessTrigger;
import com.bhp.opusb.util.NumberUtil;
import com.bhp.opusb.web.websocket.AuctionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("mAuctionProcessTrigger")
@Transactional
public class MAuctionProcessTrigger implements ProcessTrigger {

  private final Logger log = LoggerFactory.getLogger(MAuctionProcessTrigger.class);

  /**
   * Websocket service.
   */
  private final AuctionService auctionService;

  private final MAuctionRepository mAuctionRepository;

  private final MAuctionMapper mAuctionMapper;

  MAuctionProcessTrigger(AuctionService auctionService, MAuctionRepository mAuctionRepository,
      MAuctionMapper mAuctionMapper) {
    this.auctionService = auctionService;
    this.mAuctionRepository = mAuctionRepository;
    this.mAuctionMapper = mAuctionMapper;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    Long auctionId = NumberUtil.getLongValue(params.get("auctionId"));
    String action = (String) params.get("action");
    log.debug("Request to apply action {} on Auction {}", action, auctionId);

    if (action != null) {
      mAuctionRepository.findById(auctionId)
        .ifPresent(auction -> {
          auction.documentAction(action).documentStatus(action);
          auctionService.publish(mAuctionMapper.toDto(auction));
        });
    }

    return new ProcessResult();
  }
  
}
