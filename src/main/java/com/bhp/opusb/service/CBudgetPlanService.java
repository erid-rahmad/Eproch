package com.bhp.opusb.service;

import com.bhp.opusb.domain.CBudgetPlan;
import com.bhp.opusb.repository.CBudgetPlanRepository;
import com.bhp.opusb.service.dto.CBudgetPlanDTO;
import com.bhp.opusb.service.mapper.CBudgetPlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CBudgetPlan}.
 */
@Service
@Transactional
public class CBudgetPlanService {

    private final Logger log = LoggerFactory.getLogger(CBudgetPlanService.class);

    private final CBudgetPlanRepository cBudgetPlanRepository;

    private final CBudgetPlanMapper cBudgetPlanMapper;

    public CBudgetPlanService(CBudgetPlanRepository cBudgetPlanRepository, CBudgetPlanMapper cBudgetPlanMapper) {
        this.cBudgetPlanRepository = cBudgetPlanRepository;
        this.cBudgetPlanMapper = cBudgetPlanMapper;
    }

    /**
     * Save a cBudgetPlan.
     *
     * @param cBudgetPlanDTO the entity to save.
     * @return the persisted entity.
     */
    public CBudgetPlanDTO save(CBudgetPlanDTO cBudgetPlanDTO) {
        log.debug("Request to save CBudgetPlan : {}", cBudgetPlanDTO);
        CBudgetPlan cBudgetPlan = cBudgetPlanMapper.toEntity(cBudgetPlanDTO);
        cBudgetPlan = cBudgetPlanRepository.save(cBudgetPlan);
        return cBudgetPlanMapper.toDto(cBudgetPlan);
    }

    /**
     * Get all the cBudgetPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CBudgetPlanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CBudgetPlans");
        return cBudgetPlanRepository.findAll(pageable)
            .map(cBudgetPlanMapper::toDto);
    }

    /**
     * Get one cBudgetPlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CBudgetPlanDTO> findOne(Long id) {
        log.debug("Request to get CBudgetPlan : {}", id);
        return cBudgetPlanRepository.findById(id)
            .map(cBudgetPlanMapper::toDto);
    }

    /**
     * Delete the cBudgetPlan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CBudgetPlan : {}", id);
        cBudgetPlanRepository.deleteById(id);
    }
}
