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

import com.bhp.opusb.domain.MVendorScoringCriteria;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVendorScoringCriteriaRepository;
import com.bhp.opusb.service.dto.MVendorScoringCriteriaCriteria;
import com.bhp.opusb.service.dto.MVendorScoringCriteriaDTO;
import com.bhp.opusb.service.mapper.MVendorScoringCriteriaMapper;

/**
 * Service for executing complex queries for {@link MVendorScoringCriteria} entities in the database.
 * The main input is a {@link MVendorScoringCriteriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVendorScoringCriteriaDTO} or a {@link Page} of {@link MVendorScoringCriteriaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVendorScoringCriteriaQueryService extends QueryService<MVendorScoringCriteria> {

    private final Logger log = LoggerFactory.getLogger(MVendorScoringCriteriaQueryService.class);

    private final MVendorScoringCriteriaRepository mVendorScoringCriteriaRepository;

    private final MVendorScoringCriteriaMapper mVendorScoringCriteriaMapper;

    public MVendorScoringCriteriaQueryService(MVendorScoringCriteriaRepository mVendorScoringCriteriaRepository, MVendorScoringCriteriaMapper mVendorScoringCriteriaMapper) {
        this.mVendorScoringCriteriaRepository = mVendorScoringCriteriaRepository;
        this.mVendorScoringCriteriaMapper = mVendorScoringCriteriaMapper;
    }

    /**
     * Return a {@link List} of {@link MVendorScoringCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVendorScoringCriteriaDTO> findByCriteria(MVendorScoringCriteriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVendorScoringCriteria> specification = createSpecification(criteria);
        return mVendorScoringCriteriaMapper.toDto(mVendorScoringCriteriaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVendorScoringCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorScoringCriteriaDTO> findByCriteria(MVendorScoringCriteriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVendorScoringCriteria> specification = createSpecification(criteria);
        return mVendorScoringCriteriaRepository.findAll(specification, page)
            .map(mVendorScoringCriteriaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVendorScoringCriteriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVendorScoringCriteria> specification = createSpecification(criteria);
        return mVendorScoringCriteriaRepository.count(specification);
    }

    /**
     * Function to convert {@link MVendorScoringCriteriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVendorScoringCriteria> createSpecification(MVendorScoringCriteriaCriteria criteria) {
        Specification<MVendorScoringCriteria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVendorScoringCriteria_.id));
            }
            if (criteria.getRequirement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequirement(), MVendorScoringCriteria_.requirement));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVendorScoringCriteria_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVendorScoringCriteria_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVendorScoringCriteria_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getEvaluationMethodCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationMethodCriteriaId(),
                    root -> root.join(MVendorScoringCriteria_.evaluationMethodCriteria, JoinType.LEFT).get(CEvaluationMethodCriteria_.id)));
            }
            if (criteria.getEvalMethodSubCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvalMethodSubCriteriaId(),
                    root -> root.join(MVendorScoringCriteria_.evalMethodSubCriteria, JoinType.LEFT).get(CEvalMethodSubCriteria_.id)));
            }
            if (criteria.getVendorScoringLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorScoringLineId(),
                    root -> root.join(MVendorScoringCriteria_.vendorScoringLine, JoinType.LEFT).get(MVendorScoringLine_.id)));
            }
            if (criteria.getBiddingSubCriteriaLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaLineId(),
                    root -> root.join(MVendorScoringCriteria_.biddingSubCriteriaLine, JoinType.LEFT).get(CBiddingSubCriteriaLine_.id)));
            }
        }
        return specification;
    }
}
