package com.bhp.opusb.service;

import com.bhp.opusb.domain.PaDashboardPreference;
import com.bhp.opusb.repository.PaDashboardPreferenceRepository;
import com.bhp.opusb.service.dto.PaDashboardPreferenceDTO;
import com.bhp.opusb.service.mapper.PaDashboardPreferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PaDashboardPreference}.
 */
@Service
@Transactional
public class PaDashboardPreferenceService {

    private final Logger log = LoggerFactory.getLogger(PaDashboardPreferenceService.class);

    private final PaDashboardPreferenceRepository paDashboardPreferenceRepository;

    private final PaDashboardPreferenceMapper paDashboardPreferenceMapper;

    public PaDashboardPreferenceService(PaDashboardPreferenceRepository paDashboardPreferenceRepository, PaDashboardPreferenceMapper paDashboardPreferenceMapper) {
        this.paDashboardPreferenceRepository = paDashboardPreferenceRepository;
        this.paDashboardPreferenceMapper = paDashboardPreferenceMapper;
    }

    /**
     * Save a paDashboardPreference.
     *
     * @param paDashboardPreferenceDTO the entity to save.
     * @return the persisted entity.
     */
    public PaDashboardPreferenceDTO save(PaDashboardPreferenceDTO paDashboardPreferenceDTO) {
        log.debug("Request to save PaDashboardPreference : {}", paDashboardPreferenceDTO);
        PaDashboardPreference paDashboardPreference = paDashboardPreferenceMapper.toEntity(paDashboardPreferenceDTO);
        paDashboardPreference = paDashboardPreferenceRepository.save(paDashboardPreference);
        return paDashboardPreferenceMapper.toDto(paDashboardPreference);
    }

    /**
     * Get all the paDashboardPreferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaDashboardPreferenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaDashboardPreferences");
        return paDashboardPreferenceRepository.findAll(pageable)
            .map(paDashboardPreferenceMapper::toDto);
    }

    /**
     * Get one paDashboardPreference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaDashboardPreferenceDTO> findOne(Long id) {
        log.debug("Request to get PaDashboardPreference : {}", id);
        return paDashboardPreferenceRepository.findById(id)
            .map(paDashboardPreferenceMapper::toDto);
    }

    /**
     * Delete the paDashboardPreference by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaDashboardPreference : {}", id);
        paDashboardPreferenceRepository.deleteById(id);
    }
}
