package com.bhp.opusb.service;

import com.bhp.opusb.domain.MVendorScoringCriteria;
import com.bhp.opusb.repository.MVendorScoringCriteriaRepository;
import com.bhp.opusb.service.dto.MVendorScoringCriteriaDTO;
import com.bhp.opusb.service.mapper.MVendorScoringCriteriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MVendorScoringCriteria}.
 */
@Service
@Transactional
public class MVendorScoringCriteriaService {

    private final Logger log = LoggerFactory.getLogger(MVendorScoringCriteriaService.class);

    private final MVendorScoringCriteriaRepository mVendorScoringCriteriaRepository;

    private final MVendorScoringCriteriaMapper mVendorScoringCriteriaMapper;

    public MVendorScoringCriteriaService(MVendorScoringCriteriaRepository mVendorScoringCriteriaRepository, MVendorScoringCriteriaMapper mVendorScoringCriteriaMapper) {
        this.mVendorScoringCriteriaRepository = mVendorScoringCriteriaRepository;
        this.mVendorScoringCriteriaMapper = mVendorScoringCriteriaMapper;
    }

    /**
     * Save a mVendorScoringCriteria.
     *
     * @param mVendorScoringCriteriaDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorScoringCriteriaDTO save(MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO) {
        log.debug("Request to save MVendorScoringCriteria : {}", mVendorScoringCriteriaDTO);
        MVendorScoringCriteria mVendorScoringCriteria = mVendorScoringCriteriaMapper.toEntity(mVendorScoringCriteriaDTO);
        mVendorScoringCriteria = mVendorScoringCriteriaRepository.save(mVendorScoringCriteria);
        return mVendorScoringCriteriaMapper.toDto(mVendorScoringCriteria);
    }

    /**
     * Get all the mVendorScoringCriteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorScoringCriteriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorScoringCriteria");
        return mVendorScoringCriteriaRepository.findAll(pageable)
            .map(mVendorScoringCriteriaMapper::toDto);
    }

    /**
     * Get one mVendorScoringCriteria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorScoringCriteriaDTO> findOne(Long id) {
        log.debug("Request to get MVendorScoringCriteria : {}", id);
        return mVendorScoringCriteriaRepository.findById(id)
            .map(mVendorScoringCriteriaMapper::toDto);
    }

    /**
     * Delete the mVendorScoringCriteria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorScoringCriteria : {}", id);
        mVendorScoringCriteriaRepository.deleteById(id);
    }
}
