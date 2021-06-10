package com.bhp.opusb.service;

import com.bhp.opusb.domain.MAuctionSubmissionLog;
import com.bhp.opusb.repository.MAuctionSubmissionLogRepository;
import com.bhp.opusb.service.dto.MAuctionSubmissionLogDTO;
import com.bhp.opusb.service.mapper.MAuctionSubmissionLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MAuctionSubmissionLog}.
 */
@Service
@Transactional
public class MAuctionSubmissionLogService {

    private final Logger log = LoggerFactory.getLogger(MAuctionSubmissionLogService.class);

    private final MAuctionSubmissionLogRepository mAuctionSubmissionLogRepository;

    private final MAuctionSubmissionLogMapper mAuctionSubmissionLogMapper;

    public MAuctionSubmissionLogService(MAuctionSubmissionLogRepository mAuctionSubmissionLogRepository, MAuctionSubmissionLogMapper mAuctionSubmissionLogMapper) {
        this.mAuctionSubmissionLogRepository = mAuctionSubmissionLogRepository;
        this.mAuctionSubmissionLogMapper = mAuctionSubmissionLogMapper;
    }

    /**
     * Save a mAuctionSubmissionLog.
     *
     * @param mAuctionSubmissionLogDTO the entity to save.
     * @return the persisted entity.
     */
    public MAuctionSubmissionLogDTO save(MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO) {
        log.debug("Request to save MAuctionSubmissionLog : {}", mAuctionSubmissionLogDTO);
        MAuctionSubmissionLog mAuctionSubmissionLog = mAuctionSubmissionLogMapper.toEntity(mAuctionSubmissionLogDTO);
        mAuctionSubmissionLog = mAuctionSubmissionLogRepository.save(mAuctionSubmissionLog);
        return mAuctionSubmissionLogMapper.toDto(mAuctionSubmissionLog);
    }

    /**
     * Get all the mAuctionSubmissionLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionSubmissionLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MAuctionSubmissionLogs");
        return mAuctionSubmissionLogRepository.findAll(pageable)
            .map(mAuctionSubmissionLogMapper::toDto);
    }

    /**
     * Get one mAuctionSubmissionLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MAuctionSubmissionLogDTO> findOne(Long id) {
        log.debug("Request to get MAuctionSubmissionLog : {}", id);
        return mAuctionSubmissionLogRepository.findById(id)
            .map(mAuctionSubmissionLogMapper::toDto);
    }

    /**
     * Delete the mAuctionSubmissionLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MAuctionSubmissionLog : {}", id);
        mAuctionSubmissionLogRepository.deleteById(id);
    }
}
