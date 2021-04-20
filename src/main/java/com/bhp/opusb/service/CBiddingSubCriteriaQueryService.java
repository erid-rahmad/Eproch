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

import com.bhp.opusb.domain.CBiddingSubCriteria;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CBiddingSubCriteriaRepository;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaCriteria;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaDTO;
import com.bhp.opusb.service.mapper.CBiddingSubCriteriaMapper;

/**
 * Service for executing complex queries for {@link CBiddingSubCriteria} entities in the database.
 * The main input is a {@link CBiddingSubCriteriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CBiddingSubCriteriaDTO} or a {@link Page} of {@link CBiddingSubCriteriaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CBiddingSubCriteriaQueryService extends QueryService<CBiddingSubCriteria> {

    private final Logger log = LoggerFactory.getLogger(CBiddingSubCriteriaQueryService.class);

    private final CBiddingSubCriteriaRepository cBiddingSubCriteriaRepository;

    private final CBiddingSubCriteriaMapper cBiddingSubCriteriaMapper;

    public CBiddingSubCriteriaQueryService(CBiddingSubCriteriaRepository cBiddingSubCriteriaRepository, CBiddingSubCriteriaMapper cBiddingSubCriteriaMapper) {
        this.cBiddingSubCriteriaRepository = cBiddingSubCriteriaRepository;
        this.cBiddingSubCriteriaMapper = cBiddingSubCriteriaMapper;
    }

    /**
     * Return a {@link List} of {@link CBiddingSubCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CBiddingSubCriteriaDTO> findByCriteria(CBiddingSubCriteriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CBiddingSubCriteria> specification = createSpecification(criteria);
        return cBiddingSubCriteriaMapper.toDto(cBiddingSubCriteriaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CBiddingSubCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CBiddingSubCriteriaDTO> findByCriteria(CBiddingSubCriteriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CBiddingSubCriteria> specification = createSpecification(criteria);
        return cBiddingSubCriteriaRepository.findAll(specification, page)
            .map(cBiddingSubCriteriaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CBiddingSubCriteriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CBiddingSubCriteria> specification = createSpecification(criteria);
        return cBiddingSubCriteriaRepository.count(specification);
    }

    /**
     * Function to convert {@link CBiddingSubCriteriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CBiddingSubCriteria> createSpecification(CBiddingSubCriteriaCriteria criteria) {
        Specification<CBiddingSubCriteria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CBiddingSubCriteria_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CBiddingSubCriteria_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CBiddingSubCriteria_.description));
            }
            if (criteria.getEvaluationType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluationType(), CBiddingSubCriteria_.evaluationType));
            }
            if (criteria.getMultipleOptions() != null) {
                specification = specification.and(buildSpecification(criteria.getMultipleOptions(), CBiddingSubCriteria_.multipleOptions));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CBiddingSubCriteria_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CBiddingSubCriteria_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CBiddingSubCriteria_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingCriteriaId(),
                    root -> root.join(CBiddingSubCriteria_.biddingCriteria, JoinType.LEFT).get(CBiddingCriteria_.id)));
            }
        }
        return specification;
    }
}
