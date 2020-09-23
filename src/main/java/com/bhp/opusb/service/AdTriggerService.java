package com.bhp.opusb.service;

import com.bhp.opusb.config.factory.ProcessTriggerFactory;
import com.bhp.opusb.domain.AdTrigger;
import com.bhp.opusb.repository.AdTriggerRepository;
import com.bhp.opusb.service.dto.AdTriggerDTO;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.mapper.AdTriggerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AdTrigger}.
 */
@Service
@Transactional
public class AdTriggerService {

    private final Logger log = LoggerFactory.getLogger(AdTriggerService.class);

    private final AdTriggerRepository adTriggerRepository;

    private final AdTriggerMapper adTriggerMapper;

    private ProcessTriggerFactory processTriggerFactory;

    @Autowired
    public AdTriggerService(AdTriggerRepository adTriggerRepository, AdTriggerMapper adTriggerMapper, ProcessTriggerFactory processTriggerFactory) {
        this.adTriggerRepository = adTriggerRepository;
        this.adTriggerMapper = adTriggerMapper;
        this.processTriggerFactory = processTriggerFactory;
    }

    /**
     * Save a adTrigger.
     *
     * @param adTriggerDTO the entity to save.
     * @return the persisted entity.
     */
    public AdTriggerDTO save(AdTriggerDTO adTriggerDTO) {
        log.debug("Request to save AdTrigger : {}", adTriggerDTO);
        AdTrigger adTrigger = adTriggerMapper.toEntity(adTriggerDTO);
        adTrigger = adTriggerRepository.save(adTrigger);
        return adTriggerMapper.toDto(adTrigger);
    }

    /**
     * Get all the adTriggers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTriggerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdTriggers");
        return adTriggerRepository.findAll(pageable)
            .map(adTriggerMapper::toDto);
    }

    /**
     * Get one adTrigger by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdTriggerDTO> findOne(Long id) {
        log.debug("Request to get AdTrigger : {}", id);
        return adTriggerRepository.findById(id)
            .map(adTriggerMapper::toDto);
    }

    /**
     * Delete the adTrigger by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdTrigger : {}", id);
        adTriggerRepository.deleteById(id);
    }

    /**
     * Execute a process with the given Spring bean name.
     * @param serviceName
     * @param params
     */
    public TriggerResult executeProcess(String serviceName, Map<String, Object> params) {
        if (log.isDebugEnabled()) {
            params.entrySet().stream().forEach((entry) -> {
                log.debug("> {}: {}", entry.getKey(), entry.getValue());
            });
        }
        return this.processTriggerFactory.get(serviceName).run(params);
    }

}
