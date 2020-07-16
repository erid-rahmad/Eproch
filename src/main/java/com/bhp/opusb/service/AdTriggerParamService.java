package com.bhp.opusb.service;

import java.util.Optional;

import com.bhp.opusb.domain.AdTriggerParam;
import com.bhp.opusb.repository.AdTriggerParamRepository;
import com.bhp.opusb.service.dto.AdTriggerParamDTO;
import com.bhp.opusb.service.mapper.AdTriggerParamMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdTriggerParam}.
 */
@Service
@Transactional
public class AdTriggerParamService {

    private final Logger log = LoggerFactory.getLogger(AdTriggerParamService.class);

    private final AdTriggerParamRepository adTriggerParamRepository;

    private final AdTriggerParamMapper adTriggerParamMapper;

    public AdTriggerParamService(AdTriggerParamRepository adTriggerParamRepository, AdTriggerParamMapper adTriggerParamMapper) {
        this.adTriggerParamRepository = adTriggerParamRepository;
        this.adTriggerParamMapper = adTriggerParamMapper;
    }

    /**
     * Save a adTriggerParam.
     *
     * @param adTriggerParamDTO the entity to save.
     * @return the persisted entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.ADTrigger.adTriggerParams", allEntries = true)
    public AdTriggerParamDTO save(AdTriggerParamDTO adTriggerParamDTO) {
        log.debug("Request to save AdTriggerParam : {}", adTriggerParamDTO);
        AdTriggerParam adTriggerParam = adTriggerParamMapper.toEntity(adTriggerParamDTO);
        adTriggerParam = adTriggerParamRepository.save(adTriggerParam);
        return adTriggerParamMapper.toDto(adTriggerParam);
    }

    /**
     * Get all the adTriggerParams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTriggerParamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdTriggerParams");
        return adTriggerParamRepository.findAll(pageable)
            .map(adTriggerParamMapper::toDto);
    }

    /**
     * Get one adTriggerParam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdTriggerParamDTO> findOne(Long id) {
        log.debug("Request to get AdTriggerParam : {}", id);
        return adTriggerParamRepository.findById(id)
            .map(adTriggerParamMapper::toDto);
    }

    /**
     * Delete the adTriggerParam by id.
     *
     * @param id the id of the entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.ADTrigger.adTriggerParams", allEntries = true)
    public void delete(Long id) {
        log.debug("Request to delete AdTriggerParam : {}", id);
        adTriggerParamRepository.deleteById(id);
    }
}
