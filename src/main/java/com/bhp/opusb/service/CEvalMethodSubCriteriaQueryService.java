package com.bhp.opusb.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.JoinType;

import com.bhp.opusb.service.dto.CBiddingSubCriteriaCriteria;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaDTO;
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

import com.bhp.opusb.domain.CEvalMethodSubCriteria;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CEvalMethodSubCriteriaRepository;
import com.bhp.opusb.service.dto.CEvalMethodSubCriteriaCriteria;
import com.bhp.opusb.service.dto.CEvalMethodSubCriteriaDTO;
import com.bhp.opusb.service.mapper.CEvalMethodSubCriteriaMapper;

/**
 * Service for executing complex queries for {@link CEvalMethodSubCriteria} entities in the database.
 * The main input is a {@link CEvalMethodSubCriteriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CEvalMethodSubCriteriaDTO} or a {@link Page} of {@link CEvalMethodSubCriteriaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CEvalMethodSubCriteriaQueryService extends QueryService<CEvalMethodSubCriteria> {

    private final Logger log = LoggerFactory.getLogger(CEvalMethodSubCriteriaQueryService.class);

    private final CEvalMethodSubCriteriaRepository cEvalMethodSubCriteriaRepository;

    private final CEvalMethodSubCriteriaMapper cEvalMethodSubCriteriaMapper;

    public CEvalMethodSubCriteriaQueryService(CEvalMethodSubCriteriaRepository cEvalMethodSubCriteriaRepository, CEvalMethodSubCriteriaMapper cEvalMethodSubCriteriaMapper) {
        this.cEvalMethodSubCriteriaRepository = cEvalMethodSubCriteriaRepository;
        this.cEvalMethodSubCriteriaMapper = cEvalMethodSubCriteriaMapper;
    }

    @Autowired
    CBiddingSubCriteriaQueryService cBiddingSubCriteriaQueryService;

    /**
     * Return a {@link List} of {@link CEvalMethodSubCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CEvalMethodSubCriteriaDTO> findByCriteria(CEvalMethodSubCriteriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CEvalMethodSubCriteria> specification = createSpecification(criteria);
        return cEvalMethodSubCriteriaMapper.toDto(cEvalMethodSubCriteriaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CEvalMethodSubCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CEvalMethodSubCriteriaDTO> findByCriteria(CEvalMethodSubCriteriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);

        final Specification<CEvalMethodSubCriteria> specification = createSpecification(criteria);
        Page<CEvalMethodSubCriteriaDTO> cEvalMethodSubCriteriaDTOS = cEvalMethodSubCriteriaRepository
                .findAll(specification, page).map(cEvalMethodSubCriteriaMapper::toDto);

        for (CEvalMethodSubCriteriaDTO evalMethodSubCriteriaDTO : cEvalMethodSubCriteriaDTOS.getContent()) {
            LongFilter longFilter = new LongFilter();

            CBiddingSubCriteriaCriteria criteriaCriteria = new CBiddingSubCriteriaCriteria();
            criteriaCriteria
                    .setId((LongFilter) longFilter.setEquals(evalMethodSubCriteriaDTO.getBiddingSubCriteriaId()));

            Page<CBiddingSubCriteriaDTO> biddingSubCriteriaDTOs = cBiddingSubCriteriaQueryService
                    .findByCriteria(criteriaCriteria, page);

            log.debug("this biddingSubCriteriaDTOS_ {} ", biddingSubCriteriaDTOs);
            evalMethodSubCriteriaDTO.setBiddingSubCriteriaDTO(biddingSubCriteriaDTOs.getContent());
        }

        return cEvalMethodSubCriteriaDTOS;
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CEvalMethodSubCriteriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CEvalMethodSubCriteria> specification = createSpecification(criteria);
        return cEvalMethodSubCriteriaRepository.count(specification);
    }

    /**
     * Function to convert {@link CEvalMethodSubCriteriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CEvalMethodSubCriteria> createSpecification(CEvalMethodSubCriteriaCriteria criteria) {
        Specification<CEvalMethodSubCriteria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CEvalMethodSubCriteria_.id));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), CEvalMethodSubCriteria_.weight));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CEvalMethodSubCriteria_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CEvalMethodSubCriteria_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CEvalMethodSubCriteria_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingCriteriaId(),
                    root -> root.join(CEvalMethodSubCriteria_.biddingCriteria, JoinType.LEFT).get(CBiddingCriteria_.id)));
            }
            if (criteria.getBiddingSubCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaId(),
                    root -> root.join(CEvalMethodSubCriteria_.biddingSubCriteria, JoinType.LEFT).get(CBiddingSubCriteria_.id)));
            }
            if (criteria.getEvaluationMethodCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationMethodCriteriaId(),
                    root -> root.join(CEvalMethodSubCriteria_.evaluationMethodCriteria, JoinType.LEFT).get(CEvaluationMethodCriteria_.id)));
            }
        }
        return specification;
    }
}
