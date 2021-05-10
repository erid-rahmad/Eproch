package com.bhp.opusb.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.JoinType;

import com.bhp.opusb.service.dto.*;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bhp.opusb.domain.CEvaluationMethodCriteria;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CEvaluationMethodCriteriaRepository;
import com.bhp.opusb.service.mapper.CEvaluationMethodCriteriaMapper;

/**
 * Service for executing complex queries for {@link CEvaluationMethodCriteria} entities in the database.
 * The main input is a {@link CEvaluationMethodCriteriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CEvaluationMethodCriteriaDTO} or a {@link Page} of {@link CEvaluationMethodCriteriaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CEvaluationMethodCriteriaQueryService extends QueryService<CEvaluationMethodCriteria> {

    private final Logger log = LoggerFactory.getLogger(CEvaluationMethodCriteriaQueryService.class);

    private final CEvaluationMethodCriteriaRepository cEvaluationMethodCriteriaRepository;

    private final CEvaluationMethodCriteriaMapper cEvaluationMethodCriteriaMapper;

    public CEvaluationMethodCriteriaQueryService(CEvaluationMethodCriteriaRepository cEvaluationMethodCriteriaRepository, CEvaluationMethodCriteriaMapper cEvaluationMethodCriteriaMapper) {
        this.cEvaluationMethodCriteriaRepository = cEvaluationMethodCriteriaRepository;
        this.cEvaluationMethodCriteriaMapper = cEvaluationMethodCriteriaMapper;
    }

    @Autowired
    CEvalMethodSubCriteriaQueryService cEvalMethodSubCriteriaQueryService;

    /**
     * Return a {@link List} of {@link CEvaluationMethodCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CEvaluationMethodCriteriaDTO> findByCriteria(CEvaluationMethodCriteriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CEvaluationMethodCriteria> specification = createSpecification(criteria);
        return cEvaluationMethodCriteriaMapper.toDto(cEvaluationMethodCriteriaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CEvaluationMethodCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CEvaluationMethodCriteriaDTO> findByCriteria(CEvaluationMethodCriteriaCriteria criteria,
            Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CEvaluationMethodCriteria> specification = createSpecification(criteria);

        Page<CEvaluationMethodCriteriaDTO> evaluationMethodCriteriaDTOS = cEvaluationMethodCriteriaRepository
                .findAll(specification, page).map(cEvaluationMethodCriteriaMapper::toDto);

        for (CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO_ : evaluationMethodCriteriaDTOS) {
            LongFilter longFilter = new LongFilter();
            
            CEvalMethodSubCriteriaCriteria criteriaCriteria = new CEvalMethodSubCriteriaCriteria();
            criteriaCriteria.setEvaluationMethodCriteriaId(
                    (LongFilter) longFilter.setEquals(cEvaluationMethodCriteriaDTO_.getId()));

            Page<CEvalMethodSubCriteriaDTO> cEvalMethodSubCriteriaDTOS = cEvalMethodSubCriteriaQueryService
                    .findByCriteria(criteriaCriteria, page);

            log.debug("this biddingSubCriteriaDTOS_ {} ", cEvalMethodSubCriteriaDTOS);
            cEvaluationMethodCriteriaDTO_.setEvalMethodSubCriteriaList(cEvalMethodSubCriteriaDTOS.getContent());
        }
        return evaluationMethodCriteriaDTOS;
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CEvaluationMethodCriteriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CEvaluationMethodCriteria> specification = createSpecification(criteria);
        return cEvaluationMethodCriteriaRepository.count(specification);
    }

    /**
     * Function to convert {@link CEvaluationMethodCriteriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CEvaluationMethodCriteria> createSpecification(CEvaluationMethodCriteriaCriteria criteria) {
        Specification<CEvaluationMethodCriteria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CEvaluationMethodCriteria_.id));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), CEvaluationMethodCriteria_.weight));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CEvaluationMethodCriteria_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CEvaluationMethodCriteria_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CEvaluationMethodCriteria_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getEvaluationMethodLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationMethodLineId(),
                    root -> root.join(CEvaluationMethodCriteria_.evaluationMethodLine, JoinType.LEFT).get(CEvaluationMethodLine_.id)));
            }
            if (criteria.getBiddingCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingCriteriaId(),
                    root -> root.join(CEvaluationMethodCriteria_.biddingCriteria, JoinType.LEFT).get(CBiddingCriteria_.id)));
            }
        }
        return specification;
    }
}
