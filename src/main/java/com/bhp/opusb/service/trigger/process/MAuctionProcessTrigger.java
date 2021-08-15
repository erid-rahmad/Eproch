package com.bhp.opusb.service.trigger.process;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.domain.MAuctionItem;
import com.bhp.opusb.domain.MAuctionRule;
import com.bhp.opusb.job.JobScheduleCreator;
import com.bhp.opusb.repository.CVendorRepository;
import com.bhp.opusb.repository.MAuctionItemRepository;
import com.bhp.opusb.repository.MAuctionRepository;
import com.bhp.opusb.security.SecurityUtils;
import com.bhp.opusb.service.MAuctionEventLogService;
import com.bhp.opusb.service.MAuctionSubmissionItemService;
import com.bhp.opusb.service.dto.MAuctionEventLogDTO;
import com.bhp.opusb.service.dto.MAuctionItemDTO;
import com.bhp.opusb.service.dto.MAuctionSubmissionItemDTO;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.mapper.MAuctionItemMapper;
import com.bhp.opusb.service.mapper.MAuctionMapper;
import com.bhp.opusb.service.trigger.ProcessTrigger;
import com.bhp.opusb.util.DocumentUtil;
import com.bhp.opusb.util.NumberUtil;
import com.bhp.opusb.web.websocket.AuctionService;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
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

  private final MAuctionSubmissionItemService mAuctionSubmissionItemService;

  private final MAuctionEventLogService mAuctionEventLogService;

  private final MAuctionRepository mAuctionRepository;

  private final MAuctionItemRepository mAuctionItemRepository;

  private final CVendorRepository cVendorRepository;

  private final MAuctionMapper mAuctionMapper;
  private final MAuctionItemMapper mAuctionItemMapper;

  private final Scheduler scheduler;

  private final JobScheduleCreator scheduleCreator;
  
  MAuctionProcessTrigger(AuctionService auctionService, MAuctionSubmissionItemService mAuctionSubmissionItemService,
      MAuctionEventLogService mAuctionEventLogService, MAuctionRepository mAuctionRepository,
      MAuctionItemRepository mAuctionItemRepository, CVendorRepository cVendorRepository, MAuctionMapper mAuctionMapper,
      MAuctionItemMapper mAuctionItemMapper, Scheduler scheduler, JobScheduleCreator scheduleCreator) {
    this.auctionService = auctionService;
    this.mAuctionSubmissionItemService = mAuctionSubmissionItemService;
    this.mAuctionEventLogService = mAuctionEventLogService;
    this.mAuctionRepository = mAuctionRepository;
    this.mAuctionItemRepository = mAuctionItemRepository;
    this.cVendorRepository = cVendorRepository;
    this.mAuctionMapper = mAuctionMapper;
    this.mAuctionItemMapper = mAuctionItemMapper;
    this.scheduler = scheduler;
    this.scheduleCreator = scheduleCreator;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    final ProcessResult result = new ProcessResult();
    Long auctionId = NumberUtil.getLongValue(params.get("auctionId"));
    String action = (String) params.get("action");
    Object extraTime = params.get("extraTime");

    log.debug("Request to apply action {} on Auction {}", action, auctionId);

    if (action != null) {
      if (action.equals("BID")) {
        Long lotId = NumberUtil.getLongValue(params.get("itemId"));
        BigDecimal price = new BigDecimal((String) params.get("price"));

        bidAuctionItem(auctionId, lotId, price)
          .ifPresent(lot -> result.add("lot", lot));
      } else {
        mAuctionRepository.findById(auctionId)
          .ifPresent(auction -> {
            if (action.equals(DocumentUtil.ACTION_PAUSE)) {
              pauseAuction(auction);
            } else if (action.equals(DocumentUtil.ACTION_START)) {
              startAuction(auction);
            } else if (action.equals(DocumentUtil.ACTION_STOP)) {
              stopAuction(auction);
            } else if (action.equals("XTM") && extraTime != null) {
              addExtraTime(auction, (Integer) extraTime);
            }

            result.add("auction", mAuctionMapper.toDto(auction));
          });
      }
    }

    return result;
  }

  /**
   * Submit the proposed price of an item.
   * @param auctionId The ID of the auction where the item is belong to.
   * @param itemId The auction item referenced by MAuctionSubmissionItem.
   * @param price The proposed price.
   * @return
   */
  private Optional<MAuctionSubmissionItemDTO> bidAuctionItem(Long auctionId, Long itemId, BigDecimal price) {
    Instant dateTrx = Instant.now();
    Optional<MAuction> auctionRecord = mAuctionRepository.findById(auctionId);
    Optional<CVendor> vendorRecord = cVendorRepository.findById(SecurityUtils.getVendorId());

    if (auctionRecord.isPresent() && vendorRecord.isPresent()) {
      CVendor vendor = vendorRecord.get();
      Optional<MAuctionSubmissionItemDTO> result = mAuctionSubmissionItemService.submitBid(auctionId, vendor.getId(), itemId, price);
      result.ifPresent(submittedItem -> {
        submittedItem.setVendorName(vendor.getName());

        Optional<MAuctionItem> itemRecord = mAuctionItemRepository.findById(itemId);
        MAuctionItemDTO itemDTO = mAuctionItemMapper.toDto(itemRecord.orElseGet(MAuctionItem::new));
        MAuctionEventLogDTO eventLogDTO = mAuctionEventLogService.addLog("BID", auctionId, itemId, dateTrx, vendor.getId(), price);

        eventLogDTO.setVendorName(vendor.getName());
        auctionService.publish(eventLogDTO, mAuctionMapper.toDto(auctionRecord.get()), itemDTO, submittedItem);
      });

      return result;
    }

    return Optional.empty();
  }

  /**
   * Pause the ongoing auction event.
   * @param auction The ongoing auction.
   */
  private void addExtraTime(MAuction auction, Integer extraTime) {
    mAuctionItemRepository.findFirstByAuctionAndAuctionStatus(auction, DocumentUtil.ACTION_START)
      .ifPresent(lot -> {
        Instant dateTrx = Instant.now();
        Duration remainingTime = Duration.between(auction.getEstEndDate(), dateTrx).plusMinutes(extraTime);

        auction.estEndDate(dateTrx.plus(remainingTime))
          .remainingTime(remainingTime);

        MAuctionEventLogDTO eventLogDTO = mAuctionEventLogService.addLog("XTM", auction.getId(), lot.getId(), dateTrx);

        auctionService.publish(eventLogDTO, mAuctionMapper.toDto(auction), mAuctionItemMapper.toDto(lot));
      });
  }

  /**
   * Pause the ongoing auction event.
   * @param auction The ongoing auction.
   */
  private void pauseAuction(MAuction auction) {
    mAuctionItemRepository.findFirstByAuctionAndAuctionStatus(auction, DocumentUtil.ACTION_START)
      .ifPresent(lot -> {
        Instant dateTrx = Instant.now();
        Duration remainingTime = Duration.between(dateTrx, auction.getEstEndDate());

        try {
          toggleTrigger(auction, lot, true, null);
          auction.remainingTime(remainingTime);
          lot.setAuctionStatus(DocumentUtil.ACTION_PAUSE);
          MAuctionEventLogDTO eventLogDTO = mAuctionEventLogService.addLog(DocumentUtil.ACTION_PAUSE, auction.getId(), lot.getId(), dateTrx);
          auctionService.publish(eventLogDTO, mAuctionMapper.toDto(auction), mAuctionItemMapper.toDto(lot));
        } catch (SchedulerException e) {
          log.error("Failed to pause the auction. {}", e.getLocalizedMessage());
        }
      });
  }

  /**
   * This will start an MAuction with the following criteria:
   * 1. New auction and has not been started yet.
   * 2. Auction that has been paused.
   * @param auction The auction to be started.
   */
  private void startAuction(MAuction auction) {
    MAuctionRule rule = auction.getRule();
    Instant dateTrx = Instant.now();
    final String status = DocumentUtil.ACTION_START;

    if (auction.getLastStartDate() == null) {
      // Auction is started for the first time.
      mAuctionItemRepository.findFirstByAuctionAndAuctionStatusNullOrderById(auction)
        .ifPresent(lot -> {
          Instant endDate = dateTrx.plus(rule.getFirstLotRunTime(), MINUTES);
          Duration remainingTime = Duration.between(dateTrx, endDate);

          log.debug("Starting event. StartTime: {}, EndTime: {}, Duration: {}", dateTrx, endDate, remainingTime);

          try {
            initScheduler(auction, lot, endDate);
            auction.documentAction(status)
              .documentStatus(status)
              .lastStartDate(dateTrx)
              .estEndDate(endDate)
              .remainingTime(remainingTime);

            lot.setAuctionStatus(status);
            MAuctionEventLogDTO eventLogDTO = mAuctionEventLogService.addLog(status, auction.getId(), lot.getId(), dateTrx);
            auctionService.publish(eventLogDTO, mAuctionMapper.toDto(auction), mAuctionItemMapper.toDto(lot));
          } catch (SchedulerException e) {
            log.error("Failed to start the auction. {}", e.getLocalizedMessage());
          }
        });
    } else {
      mAuctionItemRepository.findFirstByAuctionAndAuctionStatus(auction, DocumentUtil.ACTION_PAUSE)
        .ifPresent(lot -> {
          Instant endDate = dateTrx.plus(auction.getRemainingTime().toMinutes(), MINUTES);
          Duration remainingTime = Duration.between(dateTrx, endDate);

          log.debug("Resuming event. StartTime: {}, EndTime: {}, Duration: {}", dateTrx, endDate, remainingTime);
          try {
            toggleTrigger(auction, lot, false, endDate);
            auction.lastStartDate(dateTrx)
              .estEndDate(endDate);

            lot.setAuctionStatus(DocumentUtil.ACTION_START);
            MAuctionEventLogDTO eventLogDTO = mAuctionEventLogService.addLog(status, auction.getId(), lot.getId(), dateTrx);
            auctionService.publish(eventLogDTO, mAuctionMapper.toDto(auction), mAuctionItemMapper.toDto(lot));
          } catch (SchedulerException e) {
            log.error("Failed to resume the auction. {}", e.getLocalizedMessage());
          }
        });
    }
  }

  /**
   * Terminates the whole auction event.
   * @param auction The ongoing auction.
   */
  private void stopAuction(MAuction auction) {
    final String status = DocumentUtil.STATUS_STOP;
    Instant dateTrx = Instant.now();
    Duration remainingTime = Duration.between(auction.getEstEndDate(), dateTrx);

    auction.documentAction(status)
      .documentStatus(status)
      .remainingTime(remainingTime);

    mAuctionItemRepository.findFirstByAuctionAndAuctionStatus(auction, DocumentUtil.ACTION_START)
      .ifPresent(lot -> {
        MAuctionEventLogDTO eventLogDTO = mAuctionEventLogService.addLog(status, auction.getId(), lot.getId(), dateTrx);
        auctionService.publish(eventLogDTO, mAuctionMapper.toDto(auction), mAuctionItemMapper.toDto(lot));
        mAuctionItemRepository.updateStatus(auction, status);
      });

  }

  private void initScheduler(MAuction auction, MAuctionItem auctionItem, Instant endTime) throws SchedulerException {
    String groupName = auction.getDocumentNo();
    String jobName = "mAuctionStopperProcessTrigger";
    String triggerName = auctionItem.getUid().toString();

    Map<String, Object> jobParams = new HashMap<>(1);
    jobParams.put("lotId", auctionItem.getId());

    JobDetail jobDetail = scheduleCreator.createJob(jobName, groupName, false, false, jobParams);
    Trigger trigger = createTrigger(triggerName, groupName, endTime);
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();
  }

  private void toggleTrigger(MAuction auction, MAuctionItem auctionItem, boolean pause, Instant endTime) throws SchedulerException {
    String groupName = auction.getDocumentNo();
    String triggerName = auctionItem.getUid().toString();
    TriggerKey triggerKey = new TriggerKey(triggerName, groupName);

    if (pause) {
      scheduler.pauseTrigger(triggerKey);
    } else if (endTime.isAfter(auction.getEstEndDate())) {
      // Reschedule the job if end date has been recalculated.
      Trigger trigger = createTrigger(triggerName, groupName, endTime);
      scheduler.rescheduleJob(triggerKey, trigger);
    } else {
      scheduler.resumeTrigger(triggerKey);
    }
  }

  private Trigger createTrigger(String triggerName, String groupName, Instant endTime) {
      return scheduleCreator.createNonRepeatTrigger(triggerName, groupName, Date.from(endTime));
  }
}
