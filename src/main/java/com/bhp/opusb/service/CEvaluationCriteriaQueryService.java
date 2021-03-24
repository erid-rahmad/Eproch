package com.bhp.opusb.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bhp.opusb.domain.CEvaluationCriteria;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CEvaluationCriteriaRepository;
import com.bhp.opusb.service.dto.CEvaluationCriteriaCriteria;
import com.bhp.opusb.service.dto.CEvaluationCriteriaDTO;
import com.bhp.opusb.service.mapper.CEvaluationCriteriaMapper;

/**
 * Service for executing complex queries for {@link CEvaluationCriteria} entities in the database.
 * The main input is a {@link CEvaluationCriteriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CEvaluationCriteriaDTO} or a {@link Page} of {@link CEvaluationCriteriaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CEvaluationCriteriaQueryService extends QueryService<CEvaluationCriteria> {

    private final Logger log = LoggerFactory.getLogger(CEvaluationCriteriaQueryService.class);

    private final CEvaluationCriteriaRepository cEvaluationCriteriaRepository;

    private final CEvaluationCriteriaMapper cEvaluationCriteriaMapper;

    public CEvaluationCriteriaQueryService(CEvaluationCriteriaRepository cEvaluationCriteriaRepository, CEvaluationCriteriaMapper cEvaluationCriteriaMapper) {
        this.cEvaluationCriteriaRepository = cEvaluationCriteriaRepository;
        this.cEvaluationCriteriaMapper = cEvaluationCriteriaMapper;
    }

    /**
     * Return a {@link List} of {@link CEvaluationCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CEvaluationCriteriaDTO> findByCriteria(CEvaluationCriteriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CEvaluationCriteria> specification = createSpecification(criteria);
        return cEvaluationCriteriaMapper.toDto(cEvaluationCriteriaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CEvaluationCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CEvaluationCriteriaDTO> findByCriteria(CEvaluationCriteriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CEvaluationCriteria> specification = createSpecification(criteria);
        return cEvaluationCriteriaRepository.findAll(specification, page)
            .map(cEvaluationCriteriaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CEvaluationCriteriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CEvaluationCriteria> specification = createSpecification(criteria);
        return cEvaluationCriteriaRepository.count(specification);
    }

    /**
     * Function to convert {@link CEvaluationCriteriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CEvaluationCriteria> createSpecification(CEvaluationCriteriaCriteria criteria) {
        Specification<CEvaluationCriteria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CEvaluationCriteria_.id));
            }
            if (criteria.getScoringPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoringPercentage(), CEvaluationCriteria_.scoringPercentage));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CEvaluationCriteria_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CEvaluationCriteria_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CEvaluationCriteria_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getEvaluationMethodLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationMethodLineId(),
                    root -> root.join(CEvaluationCriteria_.evaluationMethodLine, JoinType.LEFT).get(CEvaluationMethodLine_.id)));
            }
            if (criteria.getBiddingCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingCriteriaId(),
                    root -> root.join(CEvaluationCriteria_.biddingCriteria, JoinType.LEFT).get(CBiddingCriteria_.id)));
            }
            if (criteria.getBiddingSubCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaId(),
                    root -> root.join(CEvaluationCriteria_.biddingSubCriteria, JoinType.LEFT).get(CBiddingSubCriteria_.id)));
            }
            if (criteria.getPicId() != null) {
                specification = specification.and(buildSpecification(criteria.getPicId(),
                    root -> root.join(CEvaluationCriteria_.pic, JoinType.LEFT).get(AdUser_.id)));
            }
        }
        return specification;
    }
}
