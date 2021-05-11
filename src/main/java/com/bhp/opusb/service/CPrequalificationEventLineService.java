package com.bhp.opusb.service;

import com.bhp.opusb.domain.CPrequalificationEventLine;
import com.bhp.opusb.repository.CPrequalificationEventLineRepository;
import com.bhp.opusb.service.dto.CPrequalificationEventLineDTO;
import com.bhp.opusb.service.mapper.CPrequalificationEventLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CPrequalificationEventLine}.
 */
@Service
@Transactional
public class CPrequalificationEventLineService {

    private final Logger log = LoggerFactory.getLogger(CPrequalificationEventLineService.class);

    private final CPrequalificationEventLineRepository cPrequalificationEventLineRepository;

    private final CPrequalificationEventLineMapper cPrequalificationEventLineMapper;

    public CPrequalificationEventLineService(CPrequalificationEventLineRepository cPrequalificationEventLineRepository, CPrequalificationEventLineMapper cPrequalificationEventLineMapper) {
        this.cPrequalificationEventLineRepository = cPrequalificationEventLineRepository;
        this.cPrequalificationEventLineMapper = cPrequalificationEventLineMapper;
    }

    /**
     * Save a cPrequalificationEventLine.
     *
     * @param cPrequalificationEventLineDTO the entity to save.
     * @return the persisted entity.
     */
    public CPrequalificationEventLineDTO save(CPrequalificationEventLineDTO cPrequalificationEventLineDTO) {
        log.debug("Request to save CPrequalificationEventLine : {}", cPrequalificationEventLineDTO);
        CPrequalificationEventLine cPrequalificationEventLine = cPrequalificationEventLineMapper.toEntity(cPrequalificationEventLineDTO);
        cPrequalificationEventLine = cPrequalificationEventLineRepository.save(cPrequalificationEventLine);
        return cPrequalificationEventLineMapper.toDto(cPrequalificationEventLine);
    }

    /**
     * Get all the cPrequalificationEventLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalificationEventLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPrequalificationEventLines");
        return cPrequalificationEventLineRepository.findAll(pageable)
            .map(cPrequalificationEventLineMapper::toDto);
    }

    /**
     * Get one cPrequalificationEventLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CPrequalificationEventLineDTO> findOne(Long id) {
        log.debug("Request to get CPrequalificationEventLine : {}", id);
        return cPrequalificationEventLineRepository.findById(id)
            .map(cPrequalificationEventLineMapper::toDto);
    }

    /**
     * Delete the cPrequalificationEventLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CPrequalificationEventLine : {}", id);
        cPrequalificationEventLineRepository.deleteById(id);
    }
}
