package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADTab;
import com.bhp.opusb.repository.ADTabRepository;
import com.bhp.opusb.service.dto.ADTabDTO;
import com.bhp.opusb.service.mapper.ADTabMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ADTab}.
 */
@Service
@Transactional
public class ADTabService {

    private final Logger log = LoggerFactory.getLogger(ADTabService.class);

    private final ADTabRepository aDTabRepository;

    private final ADTabMapper aDTabMapper;

    public ADTabService(ADTabRepository aDTabRepository, ADTabMapper aDTabMapper) {
        this.aDTabRepository = aDTabRepository;
        this.aDTabMapper = aDTabMapper;
    }

    /**
     * Save a aDTab.
     *
     * @param aDTabDTO the entity to save.
     * @return the persisted entity.
     */
    public ADTabDTO save(ADTabDTO aDTabDTO) {
        log.debug("Request to save ADTab : {}", aDTabDTO);
        ADTab aDTab = aDTabMapper.toEntity(aDTabDTO);
        aDTab = aDTabRepository.save(aDTab);
        return aDTabMapper.toDto(aDTab);
    }

    /**
     * Get all the aDTabs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADTabDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADTabs");
        return aDTabRepository.findAll(pageable)
            .map(aDTabMapper::toDto);
    }

    /**
     * Get one aDTab by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADTabDTO> findOne(Long id) {
        log.debug("Request to get ADTab : {}", id);
        return aDTabRepository.findById(id)
            .map(aDTabMapper::toDto);
    }

    /**
     * Delete the aDTab by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ADTab : {}", id);
        aDTabRepository.deleteById(id);
    }
}
