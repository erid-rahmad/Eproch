package com.bhp.opusb.service;

import com.bhp.opusb.domain.CClauseLine;
import com.bhp.opusb.repository.CClauseLineRepository;
import com.bhp.opusb.service.dto.CClauseLineDTO;
import com.bhp.opusb.service.mapper.CClauseLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CClauseLine}.
 */
@Service
@Transactional
public class CClauseLineService {

    private final Logger log = LoggerFactory.getLogger(CClauseLineService.class);

    private final CClauseLineRepository cClauseLineRepository;

    private final CClauseLineMapper cClauseLineMapper;

    public CClauseLineService(CClauseLineRepository cClauseLineRepository, CClauseLineMapper cClauseLineMapper) {
        this.cClauseLineRepository = cClauseLineRepository;
        this.cClauseLineMapper = cClauseLineMapper;
    }

    /**
     * Save a cClauseLine.
     *
     * @param cClauseLineDTO the entity to save.
     * @return the persisted entity.
     */
    public CClauseLineDTO save(CClauseLineDTO cClauseLineDTO) {
        log.debug("Request to save CClauseLine : {}", cClauseLineDTO);
        CClauseLine cClauseLine = cClauseLineMapper.toEntity(cClauseLineDTO);
        cClauseLine = cClauseLineRepository.save(cClauseLine);
        return cClauseLineMapper.toDto(cClauseLine);
    }

    /**
     * Get all the cClauseLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CClauseLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CClauseLines");
        return cClauseLineRepository.findAll(pageable)
            .map(cClauseLineMapper::toDto);
    }

    /**
     * Get one cClauseLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CClauseLineDTO> findOne(Long id) {
        log.debug("Request to get CClauseLine : {}", id);
        return cClauseLineRepository.findById(id)
            .map(cClauseLineMapper::toDto);
    }

    /**
     * Delete the cClauseLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CClauseLine : {}", id);
        cClauseLineRepository.deleteById(id);
    }
}
