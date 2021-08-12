package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPrequalificationCriteria;
import com.bhp.opusb.repository.MPrequalificationCriteriaRepository;
import com.bhp.opusb.service.dto.MPrequalificationCriteriaDTO;
import com.bhp.opusb.service.mapper.MPrequalificationCriteriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

/**
 * Service Implementation for managing {@link MPrequalificationCriteria}.
 */
@Service
@Transactional
public class MPrequalificationCriteriaService {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationCriteriaService.class);

    private final MPrequalificationCriteriaRepository mPrequalificationCriteriaRepository;

    private final MPrequalificationCriteriaMapper mPrequalificationCriteriaMapper;

    public MPrequalificationCriteriaService(MPrequalificationCriteriaRepository mPrequalificationCriteriaRepository, MPrequalificationCriteriaMapper mPrequalificationCriteriaMapper) {
        this.mPrequalificationCriteriaRepository = mPrequalificationCriteriaRepository;
        this.mPrequalificationCriteriaMapper = mPrequalificationCriteriaMapper;
    }

    /**
     * Save a mPrequalificationCriteria.
     *
     * @param mPrequalificationCriteriaDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalificationCriteriaDTO save(MPrequalificationCriteriaDTO mPrequalificationCriteriaDTO) {
        log.debug("Request to save MPrequalificationCriteria : {}", mPrequalificationCriteriaDTO);
        MPrequalificationCriteria mPrequalificationCriteria = mPrequalificationCriteriaMapper.toEntity(mPrequalificationCriteriaDTO);
        mPrequalificationCriteria = mPrequalificationCriteriaRepository.save(mPrequalificationCriteria);
        return mPrequalificationCriteriaMapper.toDto(mPrequalificationCriteria);
    }

    /**
     * Get all the mPrequalificationCriteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationCriteriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalificationCriteria");
        return mPrequalificationCriteriaRepository.findAll(pageable)
            .map(mPrequalificationCriteriaMapper::toDto);
    }

    /**
     * Get one mPrequalificationCriteria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalificationCriteriaDTO> findOne(Long id) {
        log.debug("Request to get MPrequalificationCriteria : {}", id);
        return mPrequalificationCriteriaRepository.findById(id)
            .map(mPrequalificationCriteriaMapper::toDto);
    }

    /**
     * Delete the mPrequalificationCriteria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalificationCriteria : {}", id);
        mPrequalificationCriteriaRepository.deleteById(id);
    }

    public List<MPrequalificationCriteriaDTO> saveRequirements(
            @Valid List<MPrequalificationCriteriaDTO> mPrequalificationCriteriaDTOs) {
        log.debug("Request to save MVendorScoringCriterias. size : {}", mPrequalificationCriteriaDTOs.size());
        List<MPrequalificationCriteria> mPrequalificationCriteria = mPrequalificationCriteriaMapper.toEntity(mPrequalificationCriteriaDTOs);
        mPrequalificationCriteria = mPrequalificationCriteriaRepository.saveAll(mPrequalificationCriteria);
        return mPrequalificationCriteriaMapper.toDto(mPrequalificationCriteria);
    }
}
