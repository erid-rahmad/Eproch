package com.bhp.opusb.service;

import com.bhp.opusb.domain.MVendorEvaluationLine;
import com.bhp.opusb.repository.MVendorEvaluationLineRepository;
import com.bhp.opusb.service.dto.MVendorEvaluationLineDTO;
import com.bhp.opusb.service.mapper.MVendorEvaluationLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MVendorEvaluationLine}.
 */
@Service
@Transactional
public class MVendorEvaluationLineService {

    private final Logger log = LoggerFactory.getLogger(MVendorEvaluationLineService.class);

    private final MVendorEvaluationLineRepository mVendorEvaluationLineRepository;

    private final MVendorEvaluationLineMapper mVendorEvaluationLineMapper;

    public MVendorEvaluationLineService(MVendorEvaluationLineRepository mVendorEvaluationLineRepository, MVendorEvaluationLineMapper mVendorEvaluationLineMapper) {
        this.mVendorEvaluationLineRepository = mVendorEvaluationLineRepository;
        this.mVendorEvaluationLineMapper = mVendorEvaluationLineMapper;
    }

    /**
     * Save a mVendorEvaluationLine.
     *
     * @param mVendorEvaluationLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorEvaluationLineDTO save(MVendorEvaluationLineDTO mVendorEvaluationLineDTO) {
        log.debug("Request to save MVendorEvaluationLine : {}", mVendorEvaluationLineDTO);
        MVendorEvaluationLine mVendorEvaluationLine = mVendorEvaluationLineMapper.toEntity(mVendorEvaluationLineDTO);
        mVendorEvaluationLine = mVendorEvaluationLineRepository.save(mVendorEvaluationLine);
        return mVendorEvaluationLineMapper.toDto(mVendorEvaluationLine);
    }

    /**
     * Get all the mVendorEvaluationLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorEvaluationLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorEvaluationLines");
        return mVendorEvaluationLineRepository.findAll(pageable)
            .map(mVendorEvaluationLineMapper::toDto);
    }

    /**
     * Get one mVendorEvaluationLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorEvaluationLineDTO> findOne(Long id) {
        log.debug("Request to get MVendorEvaluationLine : {}", id);
        return mVendorEvaluationLineRepository.findById(id)
            .map(mVendorEvaluationLineMapper::toDto);
    }

    /**
     * Delete the mVendorEvaluationLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorEvaluationLine : {}", id);
        mVendorEvaluationLineRepository.deleteById(id);
    }
}
