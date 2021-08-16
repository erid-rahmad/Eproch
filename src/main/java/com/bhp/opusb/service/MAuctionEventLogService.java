package com.bhp.opusb.service;

import com.bhp.opusb.domain.MAuctionEventLog;
import com.bhp.opusb.repository.MAuctionEventLogRepository;
import com.bhp.opusb.security.SecurityUtils;
import com.bhp.opusb.service.dto.MAuctionEventLogDTO;
import com.bhp.opusb.service.mapper.MAuctionEventLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MAuctionEventLog}.
 */
@Service
@Transactional
public class MAuctionEventLogService {

    private final Logger log = LoggerFactory.getLogger(MAuctionEventLogService.class);

    private final MAuctionEventLogRepository mAuctionEventLogRepository;

    private final MAuctionEventLogMapper mAuctionEventLogMapper;

    public MAuctionEventLogService(MAuctionEventLogRepository mAuctionEventLogRepository, MAuctionEventLogMapper mAuctionEventLogMapper) {
        this.mAuctionEventLogRepository = mAuctionEventLogRepository;
        this.mAuctionEventLogMapper = mAuctionEventLogMapper;
    }

    /**
     * Save a mAuctionEventLog.
     *
     * @param mAuctionEventLogDTO the entity to save.
     * @return the persisted entity.
     */
    public MAuctionEventLogDTO save(MAuctionEventLogDTO mAuctionEventLogDTO) {
        log.debug("Request to save MAuctionEventLog : {}", mAuctionEventLogDTO);
        MAuctionEventLog mAuctionEventLog = mAuctionEventLogMapper.toEntity(mAuctionEventLogDTO);
        mAuctionEventLog = mAuctionEventLogRepository.save(mAuctionEventLog);
        return mAuctionEventLogMapper.toDto(mAuctionEventLog);
    }

    public MAuctionEventLogDTO addLog(String action, Long auctionId, Long itemId, Instant dateTrx) {
        return addLog(action, auctionId, itemId, dateTrx, null, null);
    }

    public MAuctionEventLogDTO addLog(String action, Long auctionId, Long itemId, Instant dateTrx, Long vendorId, BigDecimal price) {
        String username = SecurityUtils.getCurrentUserLogin().orElse("system");
        MAuctionEventLogDTO eventLogDTO = new MAuctionEventLogDTO();
        eventLogDTO.setAction(action);
        eventLogDTO.setAuctionId(auctionId);
        eventLogDTO.setAuctionItemId(itemId);
        eventLogDTO.setDateTrx(dateTrx == null ? Instant.now() : dateTrx);
        eventLogDTO.setUsername(username);
        
        if (vendorId != null) {
            eventLogDTO.setVendorId(vendorId);
        }

        if (price != null) {
            eventLogDTO.setPrice(price);
        }

        return save(eventLogDTO);
    }

    /**
     * Get all the mAuctionEventLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionEventLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MAuctionEventLogs");
        return mAuctionEventLogRepository.findAll(pageable)
            .map(mAuctionEventLogMapper::toDto);
    }

    /**
     * Get one mAuctionEventLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MAuctionEventLogDTO> findOne(Long id) {
        log.debug("Request to get MAuctionEventLog : {}", id);
        return mAuctionEventLogRepository.findById(id)
            .map(mAuctionEventLogMapper::toDto);
    }

    /**
     * Delete the mAuctionEventLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MAuctionEventLog : {}", id);
        mAuctionEventLogRepository.deleteById(id);
    }
}
