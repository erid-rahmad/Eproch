package com.bhp.opusb.service;

import com.bhp.opusb.domain.CBudgetPlanLine;
import com.bhp.opusb.repository.CBudgetPlanLineRepository;
import com.bhp.opusb.service.dto.CBudgetPlanLineDTO;
import com.bhp.opusb.service.mapper.CBudgetPlanLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CBudgetPlanLine}.
 */
@Service
@Transactional
public class CBudgetPlanLineService {

    private final Logger log = LoggerFactory.getLogger(CBudgetPlanLineService.class);

    private final CBudgetPlanLineRepository cBudgetPlanLineRepository;

    private final CBudgetPlanLineMapper cBudgetPlanLineMapper;

    public CBudgetPlanLineService(CBudgetPlanLineRepository cBudgetPlanLineRepository, CBudgetPlanLineMapper cBudgetPlanLineMapper) {
        this.cBudgetPlanLineRepository = cBudgetPlanLineRepository;
        this.cBudgetPlanLineMapper = cBudgetPlanLineMapper;
    }

    /**
     * Save a cBudgetPlanLine.
     *
     * @param cBudgetPlanLineDTO the entity to save.
     * @return the persisted entity.
     */
    public CBudgetPlanLineDTO save(CBudgetPlanLineDTO cBudgetPlanLineDTO) {
        log.debug("Request to save CBudgetPlanLine : {}", cBudgetPlanLineDTO);
        CBudgetPlanLine cBudgetPlanLine = cBudgetPlanLineMapper.toEntity(cBudgetPlanLineDTO);
        cBudgetPlanLine = cBudgetPlanLineRepository.save(cBudgetPlanLine);
        return cBudgetPlanLineMapper.toDto(cBudgetPlanLine);
    }

    /**
     * Get all the cBudgetPlanLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CBudgetPlanLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CBudgetPlanLines");
        return cBudgetPlanLineRepository.findAll(pageable)
            .map(cBudgetPlanLineMapper::toDto);
    }

    /**
     * Get one cBudgetPlanLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CBudgetPlanLineDTO> findOne(Long id) {
        log.debug("Request to get CBudgetPlanLine : {}", id);
        return cBudgetPlanLineRepository.findById(id)
            .map(cBudgetPlanLineMapper::toDto);
    }

    /**
     * Delete the cBudgetPlanLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CBudgetPlanLine : {}", id);
        cBudgetPlanLineRepository.deleteById(id);
    }
}
