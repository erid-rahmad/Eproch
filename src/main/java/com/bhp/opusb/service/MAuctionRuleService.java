package com.bhp.opusb.service;

import com.bhp.opusb.domain.MAuctionRule;
import com.bhp.opusb.repository.MAuctionRuleRepository;
import com.bhp.opusb.service.dto.MAuctionRuleDTO;
import com.bhp.opusb.service.mapper.MAuctionRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MAuctionRule}.
 */
@Service
@Transactional
public class MAuctionRuleService {

    private final Logger log = LoggerFactory.getLogger(MAuctionRuleService.class);

    private final MAuctionRuleRepository mAuctionRuleRepository;

    private final MAuctionRuleMapper mAuctionRuleMapper;

    public MAuctionRuleService(MAuctionRuleRepository mAuctionRuleRepository, MAuctionRuleMapper mAuctionRuleMapper) {
        this.mAuctionRuleRepository = mAuctionRuleRepository;
        this.mAuctionRuleMapper = mAuctionRuleMapper;
    }

    /**
     * Save a mAuctionRule.
     *
     * @param mAuctionRuleDTO the entity to save.
     * @return the persisted entity.
     */
    public MAuctionRuleDTO save(MAuctionRuleDTO mAuctionRuleDTO) {
        log.debug("Request to save MAuctionRule : {}", mAuctionRuleDTO);
        MAuctionRule mAuctionRule = mAuctionRuleMapper.toEntity(mAuctionRuleDTO);
        mAuctionRule = mAuctionRuleRepository.save(mAuctionRule);
        return mAuctionRuleMapper.toDto(mAuctionRule);
    }

    /**
     * Get all the mAuctionRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MAuctionRules");
        return mAuctionRuleRepository.findAll(pageable)
            .map(mAuctionRuleMapper::toDto);
    }

    /**
     * Get one mAuctionRule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MAuctionRuleDTO> findOne(Long id) {
        log.debug("Request to get MAuctionRule : {}", id);
        return mAuctionRuleRepository.findById(id)
            .map(mAuctionRuleMapper::toDto);
    }

    /**
     * Delete the mAuctionRule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MAuctionRule : {}", id);
        mAuctionRuleRepository.deleteById(id);
    }
}
