package com.bhp.opusb.service;

import com.bhp.opusb.domain.CPrequalMethodLine;
import com.bhp.opusb.repository.CPrequalMethodLineRepository;
import com.bhp.opusb.service.dto.CPrequalMethodLineDTO;
import com.bhp.opusb.service.mapper.CPrequalMethodLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CPrequalMethodLine}.
 */
@Service
@Transactional
public class CPrequalMethodLineService {

    private final Logger log = LoggerFactory.getLogger(CPrequalMethodLineService.class);

    private final CPrequalMethodLineRepository cPrequalMethodLineRepository;

    private final CPrequalMethodLineMapper cPrequalMethodLineMapper;

    public CPrequalMethodLineService(CPrequalMethodLineRepository cPrequalMethodLineRepository, CPrequalMethodLineMapper cPrequalMethodLineMapper) {
        this.cPrequalMethodLineRepository = cPrequalMethodLineRepository;
        this.cPrequalMethodLineMapper = cPrequalMethodLineMapper;
    }

    /**
     * Save a cPrequalMethodLine.
     *
     * @param cPrequalMethodLineDTO the entity to save.
     * @return the persisted entity.
     */
    public CPrequalMethodLineDTO save(CPrequalMethodLineDTO cPrequalMethodLineDTO) {
        log.debug("Request to save CPrequalMethodLine : {}", cPrequalMethodLineDTO);
        CPrequalMethodLine cPrequalMethodLine = cPrequalMethodLineMapper.toEntity(cPrequalMethodLineDTO);
        cPrequalMethodLine = cPrequalMethodLineRepository.save(cPrequalMethodLine);
        return cPrequalMethodLineMapper.toDto(cPrequalMethodLine);
    }

    /**
     * Get all the cPrequalMethodLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalMethodLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPrequalMethodLines");
        return cPrequalMethodLineRepository.findAll(pageable)
            .map(cPrequalMethodLineMapper::toDto);
    }

    /**
     * Get one cPrequalMethodLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CPrequalMethodLineDTO> findOne(Long id) {
        log.debug("Request to get CPrequalMethodLine : {}", id);
        return cPrequalMethodLineRepository.findById(id)
            .map(cPrequalMethodLineMapper::toDto);
    }

    /**
     * Delete the cPrequalMethodLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CPrequalMethodLine : {}", id);
        cPrequalMethodLineRepository.deleteById(id);
    }
}
