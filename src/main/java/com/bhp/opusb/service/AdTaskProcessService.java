package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdTaskProcess;
import com.bhp.opusb.repository.AdTaskProcessRepository;
import com.bhp.opusb.service.dto.AdTaskProcessDTO;
import com.bhp.opusb.service.mapper.AdTaskProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdTaskProcess}.
 */
@Service
@Transactional
public class AdTaskProcessService {

    private final Logger log = LoggerFactory.getLogger(AdTaskProcessService.class);

    private final AdTaskProcessRepository adTaskProcessRepository;

    private final AdTaskProcessMapper adTaskProcessMapper;

    public AdTaskProcessService(AdTaskProcessRepository adTaskProcessRepository, AdTaskProcessMapper adTaskProcessMapper) {
        this.adTaskProcessRepository = adTaskProcessRepository;
        this.adTaskProcessMapper = adTaskProcessMapper;
    }

    /**
     * Save a adTaskProcess.
     *
     * @param adTaskProcessDTO the entity to save.
     * @return the persisted entity.
     */
    public AdTaskProcessDTO save(AdTaskProcessDTO adTaskProcessDTO) {
        log.debug("Request to save AdTaskProcess : {}", adTaskProcessDTO);
        AdTaskProcess adTaskProcess = adTaskProcessMapper.toEntity(adTaskProcessDTO);
        adTaskProcess = adTaskProcessRepository.save(adTaskProcess);
        return adTaskProcessMapper.toDto(adTaskProcess);
    }

    /**
     * Get all the adTaskProcesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTaskProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdTaskProcesses");
        return adTaskProcessRepository.findAll(pageable)
            .map(adTaskProcessMapper::toDto);
    }

    /**
     * Get one adTaskProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdTaskProcessDTO> findOne(Long id) {
        log.debug("Request to get AdTaskProcess : {}", id);
        return adTaskProcessRepository.findById(id)
            .map(adTaskProcessMapper::toDto);
    }

    /**
     * Delete the adTaskProcess by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdTaskProcess : {}", id);
        adTaskProcessRepository.deleteById(id);
    }
}
