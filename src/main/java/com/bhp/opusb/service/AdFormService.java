package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdForm;
import com.bhp.opusb.repository.AdFormRepository;
import com.bhp.opusb.service.dto.AdFormDTO;
import com.bhp.opusb.service.mapper.AdFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdForm}.
 */
@Service
@Transactional
public class AdFormService {

    private final Logger log = LoggerFactory.getLogger(AdFormService.class);

    private final AdFormRepository adFormRepository;

    private final AdFormMapper adFormMapper;

    public AdFormService(AdFormRepository adFormRepository, AdFormMapper adFormMapper) {
        this.adFormRepository = adFormRepository;
        this.adFormMapper = adFormMapper;
    }

    /**
     * Save a adForm.
     *
     * @param adFormDTO the entity to save.
     * @return the persisted entity.
     */
    public AdFormDTO save(AdFormDTO adFormDTO) {
        log.debug("Request to save AdForm : {}", adFormDTO);
        AdForm adForm = adFormMapper.toEntity(adFormDTO);
        adForm = adFormRepository.save(adForm);
        return adFormMapper.toDto(adForm);
    }

    /**
     * Get all the adForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdFormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdForms");
        return adFormRepository.findAll(pageable)
            .map(adFormMapper::toDto);
    }

    /**
     * Get one adForm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdFormDTO> findOne(Long id) {
        log.debug("Request to get AdForm : {}", id);
        return adFormRepository.findById(id)
            .map(adFormMapper::toDto);
    }

    /**
     * Delete the adForm by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdForm : {}", id);
        adFormRepository.deleteById(id);
    }
}
