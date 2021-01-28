package com.bhp.opusb.service;

import com.bhp.opusb.domain.PaDashboard;
import com.bhp.opusb.repository.PaDashboardRepository;
import com.bhp.opusb.service.dto.PaDashboardDTO;
import com.bhp.opusb.service.mapper.PaDashboardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PaDashboard}.
 */
@Service
@Transactional
public class PaDashboardService {

    private final Logger log = LoggerFactory.getLogger(PaDashboardService.class);

    private final PaDashboardRepository paDashboardRepository;

    private final PaDashboardMapper paDashboardMapper;

    public PaDashboardService(PaDashboardRepository paDashboardRepository, PaDashboardMapper paDashboardMapper) {
        this.paDashboardRepository = paDashboardRepository;
        this.paDashboardMapper = paDashboardMapper;
    }

    /**
     * Save a paDashboard.
     *
     * @param paDashboardDTO the entity to save.
     * @return the persisted entity.
     */
    public PaDashboardDTO save(PaDashboardDTO paDashboardDTO) {
        log.debug("Request to save PaDashboard : {}", paDashboardDTO);
        PaDashboard paDashboard = paDashboardMapper.toEntity(paDashboardDTO);
        paDashboard = paDashboardRepository.save(paDashboard);
        return paDashboardMapper.toDto(paDashboard);
    }

    /**
     * Get all the paDashboards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaDashboardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaDashboards");
        return paDashboardRepository.findAll(pageable)
            .map(paDashboardMapper::toDto);
    }

    /**
     * Get one paDashboard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaDashboardDTO> findOne(Long id) {
        log.debug("Request to get PaDashboard : {}", id);
        return paDashboardRepository.findById(id)
            .map(paDashboardMapper::toDto);
    }

    /**
     * Delete the paDashboard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaDashboard : {}", id);
        paDashboardRepository.deleteById(id);
    }
}
