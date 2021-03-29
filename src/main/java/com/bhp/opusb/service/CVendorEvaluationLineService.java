package com.bhp.opusb.service;

import com.bhp.opusb.domain.CVendorEvaluationLine;
import com.bhp.opusb.repository.CVendorEvaluationLineRepository;
import com.bhp.opusb.service.dto.CVendorEvaluationLineDTO;
import com.bhp.opusb.service.mapper.CVendorEvaluationLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CVendorEvaluationLine}.
 */
@Service
@Transactional
public class CVendorEvaluationLineService {

    private final Logger log = LoggerFactory.getLogger(CVendorEvaluationLineService.class);

    private final CVendorEvaluationLineRepository cVendorEvaluationLineRepository;

    private final CVendorEvaluationLineMapper cVendorEvaluationLineMapper;

    public CVendorEvaluationLineService(CVendorEvaluationLineRepository cVendorEvaluationLineRepository, CVendorEvaluationLineMapper cVendorEvaluationLineMapper) {
        this.cVendorEvaluationLineRepository = cVendorEvaluationLineRepository;
        this.cVendorEvaluationLineMapper = cVendorEvaluationLineMapper;
    }

    /**
     * Save a cVendorEvaluationLine.
     *
     * @param cVendorEvaluationLineDTO the entity to save.
     * @return the persisted entity.
     */
    public CVendorEvaluationLineDTO save(CVendorEvaluationLineDTO cVendorEvaluationLineDTO) {
        log.debug("Request to save CVendorEvaluationLine : {}", cVendorEvaluationLineDTO);
        CVendorEvaluationLine cVendorEvaluationLine = cVendorEvaluationLineMapper.toEntity(cVendorEvaluationLineDTO);
        cVendorEvaluationLine = cVendorEvaluationLineRepository.save(cVendorEvaluationLine);
        return cVendorEvaluationLineMapper.toDto(cVendorEvaluationLine);
    }

    /**
     * Get all the cVendorEvaluationLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorEvaluationLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CVendorEvaluationLines");
        return cVendorEvaluationLineRepository.findAll(pageable)
            .map(cVendorEvaluationLineMapper::toDto);
    }

    /**
     * Get one cVendorEvaluationLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CVendorEvaluationLineDTO> findOne(Long id) {
        log.debug("Request to get CVendorEvaluationLine : {}", id);
        return cVendorEvaluationLineRepository.findById(id)
            .map(cVendorEvaluationLineMapper::toDto);
    }

    /**
     * Delete the cVendorEvaluationLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CVendorEvaluationLine : {}", id);
        cVendorEvaluationLineRepository.deleteById(id);
    }
}
