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

import com.bhp.opusb.domain.MPrequalificationCriteria;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalificationCriteriaRepository;
import com.bhp.opusb.service.dto.MPrequalificationCriteriaCriteria;
import com.bhp.opusb.service.dto.MPrequalificationCriteriaDTO;
import com.bhp.opusb.service.mapper.MPrequalificationCriteriaMapper;

/**
 * Service for executing complex queries for {@link MPrequalificationCriteria} entities in the database.
 * The main input is a {@link MPrequalificationCriteriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalificationCriteriaDTO} or a {@link Page} of {@link MPrequalificationCriteriaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalificationCriteriaQueryService extends QueryService<MPrequalificationCriteria> {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationCriteriaQueryService.class);

    private final MPrequalificationCriteriaRepository mPrequalificationCriteriaRepository;

    private final MPrequalificationCriteriaMapper mPrequalificationCriteriaMapper;

    public MPrequalificationCriteriaQueryService(MPrequalificationCriteriaRepository mPrequalificationCriteriaRepository, MPrequalificationCriteriaMapper mPrequalificationCriteriaMapper) {
        this.mPrequalificationCriteriaRepository = mPrequalificationCriteriaRepository;
        this.mPrequalificationCriteriaMapper = mPrequalificationCriteriaMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalificationCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalificationCriteriaDTO> findByCriteria(MPrequalificationCriteriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalificationCriteria> specification = createSpecification(criteria);
        return mPrequalificationCriteriaMapper.toDto(mPrequalificationCriteriaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalificationCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationCriteriaDTO> findByCriteria(MPrequalificationCriteriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalificationCriteria> specification = createSpecification(criteria);
        return mPrequalificationCriteriaRepository.findAll(specification, page)
            .map(mPrequalificationCriteriaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalificationCriteriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalificationCriteria> specification = createSpecification(criteria);
        return mPrequalificationCriteriaRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalificationCriteriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalificationCriteria> createSpecification(MPrequalificationCriteriaCriteria criteria) {
        Specification<MPrequalificationCriteria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalificationCriteria_.id));
            }
            if (criteria.getRequirement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequirement(), MPrequalificationCriteria_.requirement));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalificationCriteria_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalificationCriteria_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalificationCriteria_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getPrequalificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationId(),
                    root -> root.join(MPrequalificationCriteria_.prequalification, JoinType.LEFT).get(MPrequalificationInformation_.id)));
            }
            if (criteria.getPrequalMethodCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalMethodCriteriaId(),
                    root -> root.join(MPrequalificationCriteria_.prequalMethodCriteria, JoinType.LEFT).get(CPrequalMethodCriteria_.id)));
            }
            if (criteria.getPrequalMethodSubCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalMethodSubCriteriaId(),
                    root -> root.join(MPrequalificationCriteria_.prequalMethodSubCriteria, JoinType.LEFT).get(CPrequalMethodSubCriteria_.id)));
            }
            if (criteria.getBiddingSubCriteriaLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaLineId(),
                    root -> root.join(MPrequalificationCriteria_.biddingSubCriteriaLine, JoinType.LEFT).get(CBiddingSubCriteriaLine_.id)));
            }
        }
        return specification;
    }
}
