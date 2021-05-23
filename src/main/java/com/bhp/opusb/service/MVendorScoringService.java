package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;

import com.bhp.opusb.domain.MVendorScoring;
import com.bhp.opusb.repository.MVendorScoringRepository;
import com.bhp.opusb.service.dto.MVendorScoringDTO;
import com.bhp.opusb.service.dto.MVendorScoringLineDTO;
import com.bhp.opusb.service.mapper.MVendorScoringMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MVendorScoring}.
 */
@Service
@Transactional
public class MVendorScoringService {

    private final Logger log = LoggerFactory.getLogger(MVendorScoringService.class);
    
    private final MVendorScoringLineService mVendorScoringLineService;

    private final MVendorScoringRepository mVendorScoringRepository;
    
    private final MVendorScoringMapper mVendorScoringMapper;
    
    public MVendorScoringService(MVendorScoringLineService mVendorScoringLineService,
            MVendorScoringRepository mVendorScoringRepository, MVendorScoringMapper mVendorScoringMapper) {
        this.mVendorScoringLineService = mVendorScoringLineService;
        this.mVendorScoringRepository = mVendorScoringRepository;
        this.mVendorScoringMapper = mVendorScoringMapper;
    }

    /**
     * Save a mVendorScoring.
     *
     * @param mVendorScoringDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorScoringDTO save(MVendorScoringDTO mVendorScoringDTO) {
        log.debug("Request to save MVendorScoring : {}", mVendorScoringDTO);
        MVendorScoring mVendorScoring = mVendorScoringMapper.toEntity(mVendorScoringDTO);
        mVendorScoring = mVendorScoringRepository.save(mVendorScoring);

        MVendorScoringDTO result = mVendorScoringMapper.toDto(mVendorScoring);
        List<MVendorScoringLineDTO> lines = mVendorScoringLineService.saveAll(mVendorScoringDTO.getVendorScoringLineDTOList(), result.getId());

        result.setVendorScoringLineDTOList(lines);
        return result;
    }


    /**
     * Get all the mVendorScorings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorScoringDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorScorings");
        return mVendorScoringRepository.findAll(pageable)
            .map(mVendorScoringMapper::toDto);
    }

    /**
     * Get one mVendorScoring by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorScoringDTO> findOne(Long id) {
        log.debug("Request to get MVendorScoring : {}", id);
        return mVendorScoringRepository.findById(id)
            .map(mVendorScoringMapper::toDto);
    }

    /**
     * Delete the mVendorScoring by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorScoring : {}", id);
        mVendorScoringRepository.deleteById(id);
    }
}
