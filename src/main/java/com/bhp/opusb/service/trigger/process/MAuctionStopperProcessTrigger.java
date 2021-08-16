package com.bhp.opusb.service.trigger.process;

import java.time.Instant;
import java.util.Map;

import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.repository.MAuctionItemRepository;
import com.bhp.opusb.service.MAuctionEventLogService;
import com.bhp.opusb.service.dto.MAuctionEventLogDTO;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.mapper.MAuctionItemMapper;
import com.bhp.opusb.service.mapper.MAuctionMapper;
import com.bhp.opusb.service.trigger.ProcessTrigger;
import com.bhp.opusb.util.DocumentUtil;
import com.bhp.opusb.util.NumberUtil;
import com.bhp.opusb.web.websocket.AuctionService;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("mAuctionStopperProcessTrigger")
@Transactional
public class MAuctionStopperProcessTrigger implements ProcessTrigger {

  private final Logger log = LoggerFactory.getLogger(MAuctionStopperProcessTrigger.class);

  /**
   * Websocket service.
   */
  private final AuctionService auctionService;

  private final MAuctionEventLogService mAuctionEventLogService;

  private final MAuctionItemRepository mAuctionItemRepository;

  private final Scheduler scheduler;

  private final MAuctionMapper mAuctionMapper;

  private final MAuctionItemMapper mAuctionItemMapper;

  public MAuctionStopperProcessTrigger(AuctionService auctionService, MAuctionEventLogService mAuctionEventLogService,
      MAuctionItemRepository mAuctionItemRepository, Scheduler scheduler, MAuctionMapper mAuctionMapper,
      MAuctionItemMapper mAuctionItemMapper) {
    this.auctionService = auctionService;
    this.mAuctionEventLogService = mAuctionEventLogService;
    this.mAuctionItemRepository = mAuctionItemRepository;
    this.scheduler = scheduler;
    this.mAuctionMapper = mAuctionMapper;
    this.mAuctionItemMapper = mAuctionItemMapper;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    Long lotId = NumberUtil.getLongValue(params.get("lotId"));
    
    mAuctionItemRepository.findById(lotId)
      .ifPresent(item -> {
        MAuction auction = item.getAuction();
        String groupName = auction.getDocumentNo();
        String triggerName = item.getUid().toString();
        TriggerKey triggerKey = new TriggerKey(triggerName, groupName);

        auction.lastStartDate(null)
          .estEndDate(null)
          .remainingTime(null);

        try {
          scheduler.unscheduleJob(triggerKey);
          item.setAuctionStatus(DocumentUtil.STATUS_FINISH);
          MAuctionEventLogDTO eventLogDTO = mAuctionEventLogService.addLog(DocumentUtil.STATUS_FINISH, auction.getId(), item.getId(), Instant.now());
          auctionService.publish(eventLogDTO, mAuctionMapper.toDto(auction), mAuctionItemMapper.toDto(item));
        } catch (SchedulerException e) {
          log.error("Failed to stop the auction. {}", e.getLocalizedMessage());
        }
      });
    
    return new ProcessResult();
  }
  
}
