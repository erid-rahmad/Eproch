package com.bhp.opusb.service;

import com.bhp.opusb.domain.CPrequalificationStep;
import com.bhp.opusb.repository.CPrequalificationStepRepository;
import com.bhp.opusb.service.dto.CPrequalificationStepDTO;
import com.bhp.opusb.service.mapper.CPrequalificationStepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CPrequalificationStep}.
 */
@Service
@Transactional
public class CPrequalificationStepService {

    private final Logger log = LoggerFactory.getLogger(CPrequalificationStepService.class);

    private final CPrequalificationStepRepository cPrequalificationStepRepository;

    private final CPrequalificationStepMapper cPrequalificationStepMapper;

    public CPrequalificationStepService(CPrequalificationStepRepository cPrequalificationStepRepository, CPrequalificationStepMapper cPrequalificationStepMapper) {
        this.cPrequalificationStepRepository = cPrequalificationStepRepository;
        this.cPrequalificationStepMapper = cPrequalificationStepMapper;
    }

    /**
     * Save a cPrequalificationStep.
     *
     * @param cPrequalificationStepDTO the entity to save.
     * @return the persisted entity.
     */
    public CPrequalificationStepDTO save(CPrequalificationStepDTO cPrequalificationStepDTO) {
        log.debug("Request to save CPrequalificationStep : {}", cPrequalificationStepDTO);
        CPrequalificationStep cPrequalificationStep = cPrequalificationStepMapper.toEntity(cPrequalificationStepDTO);
        cPrequalificationStep = cPrequalificationStepRepository.save(cPrequalificationStep);
        return cPrequalificationStepMapper.toDto(cPrequalificationStep);
    }

    /**
     * Get all the cPrequalificationSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalificationStepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPrequalificationSteps");
        return cPrequalificationStepRepository.findAll(pageable)
            .map(cPrequalificationStepMapper::toDto);
    }

    /**
     * Get one cPrequalificationStep by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CPrequalificationStepDTO> findOne(Long id) {
        log.debug("Request to get CPrequalificationStep : {}", id);
        return cPrequalificationStepRepository.findById(id)
            .map(cPrequalificationStepMapper::toDto);
    }

    /**
     * Delete the cPrequalificationStep by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CPrequalificationStep : {}", id);
        cPrequalificationStepRepository.deleteById(id);
    }
}
