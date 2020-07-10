package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdButton;
import com.bhp.opusb.repository.AdButtonRepository;
import com.bhp.opusb.service.dto.AdButtonDTO;
import com.bhp.opusb.service.mapper.AdButtonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdButton}.
 */
@Service
@Transactional
public class AdButtonService {

    private final Logger log = LoggerFactory.getLogger(AdButtonService.class);

    private final AdButtonRepository adButtonRepository;

    private final AdButtonMapper adButtonMapper;

    public AdButtonService(AdButtonRepository adButtonRepository, AdButtonMapper adButtonMapper) {
        this.adButtonRepository = adButtonRepository;
        this.adButtonMapper = adButtonMapper;
    }

    /**
     * Save a adButton.
     *
     * @param adButtonDTO the entity to save.
     * @return the persisted entity.
     */
    public AdButtonDTO save(AdButtonDTO adButtonDTO) {
        log.debug("Request to save AdButton : {}", adButtonDTO);
        AdButton adButton = adButtonMapper.toEntity(adButtonDTO);
        adButton = adButtonRepository.save(adButton);
        return adButtonMapper.toDto(adButton);
    }

    /**
     * Get all the adButtons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdButtonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdButtons");
        return adButtonRepository.findAll(pageable)
            .map(adButtonMapper::toDto);
    }

    /**
     * Get one adButton by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdButtonDTO> findOne(Long id) {
        log.debug("Request to get AdButton : {}", id);
        return adButtonRepository.findById(id)
            .map(adButtonMapper::toDto);
    }

    /**
     * Delete the adButton by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdButton : {}", id);
        adButtonRepository.deleteById(id);
    }
}
