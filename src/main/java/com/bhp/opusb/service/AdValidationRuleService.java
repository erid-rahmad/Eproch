package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdValidationRule;
import com.bhp.opusb.repository.AdValidationRuleRepository;
import com.bhp.opusb.service.dto.AdValidationRuleDTO;
import com.bhp.opusb.service.mapper.AdValidationRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdValidationRule}.
 */
@Service
@Transactional
public class AdValidationRuleService {

    private final Logger log = LoggerFactory.getLogger(AdValidationRuleService.class);

    private final AdValidationRuleRepository adValidationRuleRepository;

    private final AdValidationRuleMapper adValidationRuleMapper;

    public AdValidationRuleService(AdValidationRuleRepository adValidationRuleRepository, AdValidationRuleMapper adValidationRuleMapper) {
        this.adValidationRuleRepository = adValidationRuleRepository;
        this.adValidationRuleMapper = adValidationRuleMapper;
    }

    /**
     * Save a adValidationRule.
     *
     * @param adValidationRuleDTO the entity to save.
     * @return the persisted entity.
     */
    public AdValidationRuleDTO save(AdValidationRuleDTO adValidationRuleDTO) {
        log.debug("Request to save AdValidationRule : {}", adValidationRuleDTO);
        AdValidationRule adValidationRule = adValidationRuleMapper.toEntity(adValidationRuleDTO);
        adValidationRule = adValidationRuleRepository.save(adValidationRule);
        return adValidationRuleMapper.toDto(adValidationRule);
    }

    /**
     * Get all the adValidationRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdValidationRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdValidationRules");
        return adValidationRuleRepository.findAll(pageable)
            .map(adValidationRuleMapper::toDto);
    }

    /**
     * Get one adValidationRule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdValidationRuleDTO> findOne(Long id) {
        log.debug("Request to get AdValidationRule : {}", id);
        return adValidationRuleRepository.findById(id)
            .map(adValidationRuleMapper::toDto);
    }

    /**
     * Delete the adValidationRule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdValidationRule : {}", id);
        adValidationRuleRepository.deleteById(id);
    }
}
