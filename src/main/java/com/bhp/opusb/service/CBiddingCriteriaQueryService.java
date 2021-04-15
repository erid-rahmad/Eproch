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

import com.bhp.opusb.domain.CBiddingCriteria;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CBiddingCriteriaRepository;
import com.bhp.opusb.service.dto.CBiddingCriteriaCriteria;
import com.bhp.opusb.service.dto.CBiddingCriteriaDTO;
import com.bhp.opusb.service.mapper.CBiddingCriteriaMapper;

/**
 * Service for executing complex queries for {@link CBiddingCriteria} entities in the database.
 * The main input is a {@link CBiddingCriteriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CBiddingCriteriaDTO} or a {@link Page} of {@link CBiddingCriteriaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CBiddingCriteriaQueryService extends QueryService<CBiddingCriteria> {

    private final Logger log = LoggerFactory.getLogger(CBiddingCriteriaQueryService.class);

    private final CBiddingCriteriaRepository cBiddingCriteriaRepository;

    private final CBiddingCriteriaMapper cBiddingCriteriaMapper;

    public CBiddingCriteriaQueryService(CBiddingCriteriaRepository cBiddingCriteriaRepository, CBiddingCriteriaMapper cBiddingCriteriaMapper) {
        this.cBiddingCriteriaRepository = cBiddingCriteriaRepository;
        this.cBiddingCriteriaMapper = cBiddingCriteriaMapper;
    }

    /**
     * Return a {@link List} of {@link CBiddingCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CBiddingCriteriaDTO> findByCriteria(CBiddingCriteriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CBiddingCriteria> specification = createSpecification(criteria);
        return cBiddingCriteriaMapper.toDto(cBiddingCriteriaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CBiddingCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CBiddingCriteriaDTO> findByCriteria(CBiddingCriteriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CBiddingCriteria> specification = createSpecification(criteria);
        return cBiddingCriteriaRepository.findAll(specification, page)
            .map(cBiddingCriteriaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CBiddingCriteriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CBiddingCriteria> specification = createSpecification(criteria);
        return cBiddingCriteriaRepository.count(specification);
    }

    /**
     * Function to convert {@link CBiddingCriteriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CBiddingCriteria> createSpecification(CBiddingCriteriaCriteria criteria) {
        Specification<CBiddingCriteria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CBiddingCriteria_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CBiddingCriteria_.name));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CBiddingCriteria_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CBiddingCriteria_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CBiddingCriteria_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
