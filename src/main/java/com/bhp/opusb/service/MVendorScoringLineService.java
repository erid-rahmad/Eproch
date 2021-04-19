package com.bhp.opusb.service;

import com.bhp.opusb.domain.MVendorScoringLine;
import com.bhp.opusb.repository.MVendorScoringLineRepository;
import com.bhp.opusb.service.dto.MVendorScoringLineDTO;
import com.bhp.opusb.service.mapper.MVendorScoringLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MVendorScoringLine}.
 */
@Service
@Transactional
public class MVendorScoringLineService {

    private final Logger log = LoggerFactory.getLogger(MVendorScoringLineService.class);

    private final MVendorScoringLineRepository mVendorScoringLineRepository;

    private final MVendorScoringLineMapper mVendorScoringLineMapper;

    public MVendorScoringLineService(MVendorScoringLineRepository mVendorScoringLineRepository, MVendorScoringLineMapper mVendorScoringLineMapper) {
        this.mVendorScoringLineRepository = mVendorScoringLineRepository;
        this.mVendorScoringLineMapper = mVendorScoringLineMapper;
    }

    /**
     * Save a mVendorScoringLine.
     *
     * @param mVendorScoringLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorScoringLineDTO save(MVendorScoringLineDTO mVendorScoringLineDTO) {
        log.debug("Request to save MVendorScoringLine : {}", mVendorScoringLineDTO);
        MVendorScoringLine mVendorScoringLine = mVendorScoringLineMapper.toEntity(mVendorScoringLineDTO);
        mVendorScoringLine = mVendorScoringLineRepository.save(mVendorScoringLine);
        return mVendorScoringLineMapper.toDto(mVendorScoringLine);
    }

    /**
     * Get all the mVendorScoringLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorScoringLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorScoringLines");
        return mVendorScoringLineRepository.findAll(pageable)
            .map(mVendorScoringLineMapper::toDto);
    }

    /**
     * Get one mVendorScoringLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorScoringLineDTO> findOne(Long id) {
        log.debug("Request to get MVendorScoringLine : {}", id);
        return mVendorScoringLineRepository.findById(id)
            .map(mVendorScoringLineMapper::toDto);
    }

    /**
     * Delete the mVendorScoringLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorScoringLine : {}", id);
        mVendorScoringLineRepository.deleteById(id);
    }
}
