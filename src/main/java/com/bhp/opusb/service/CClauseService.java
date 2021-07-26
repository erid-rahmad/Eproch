package com.bhp.opusb.service;

import com.bhp.opusb.domain.CClause;
import com.bhp.opusb.repository.CClauseRepository;
import com.bhp.opusb.service.dto.CClauseDTO;
import com.bhp.opusb.service.mapper.CClauseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CClause}.
 */
@Service
@Transactional
public class CClauseService {

    private final Logger log = LoggerFactory.getLogger(CClauseService.class);

    private final CClauseRepository cClauseRepository;

    private final CClauseMapper cClauseMapper;

    public CClauseService(CClauseRepository cClauseRepository, CClauseMapper cClauseMapper) {
        this.cClauseRepository = cClauseRepository;
        this.cClauseMapper = cClauseMapper;
    }

    /**
     * Save a cClause.
     *
     * @param cClauseDTO the entity to save.
     * @return the persisted entity.
     */
    public CClauseDTO save(CClauseDTO cClauseDTO) {
        log.debug("Request to save CClause : {}", cClauseDTO);
        CClause cClause = cClauseMapper.toEntity(cClauseDTO);
        cClause = cClauseRepository.save(cClause);
        return cClauseMapper.toDto(cClause);
    }

    /**
     * Get all the cClauses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CClauseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CClauses");
        return cClauseRepository.findAll(pageable)
            .map(cClauseMapper::toDto);
    }

    /**
     * Get one cClause by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CClauseDTO> findOne(Long id) {
        log.debug("Request to get CClause : {}", id);
        return cClauseRepository.findById(id)
            .map(cClauseMapper::toDto);
    }

    /**
     * Delete the cClause by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CClause : {}", id);
        cClauseRepository.deleteById(id);
    }
}
