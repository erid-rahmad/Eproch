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

import com.bhp.opusb.domain.CPrequalMethodCriteria;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CPrequalMethodCriteriaRepository;
import com.bhp.opusb.service.dto.CPrequalMethodCriteriaCriteria;
import com.bhp.opusb.service.dto.CPrequalMethodCriteriaDTO;
import com.bhp.opusb.service.mapper.CPrequalMethodCriteriaMapper;

/**
 * Service for executing complex queries for {@link CPrequalMethodCriteria} entities in the database.
 * The main input is a {@link CPrequalMethodCriteriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPrequalMethodCriteriaDTO} or a {@link Page} of {@link CPrequalMethodCriteriaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPrequalMethodCriteriaQueryService extends QueryService<CPrequalMethodCriteria> {

    private final Logger log = LoggerFactory.getLogger(CPrequalMethodCriteriaQueryService.class);

    private final CPrequalMethodCriteriaRepository cPrequalMethodCriteriaRepository;

    private final CPrequalMethodCriteriaMapper cPrequalMethodCriteriaMapper;

    public CPrequalMethodCriteriaQueryService(CPrequalMethodCriteriaRepository cPrequalMethodCriteriaRepository, CPrequalMethodCriteriaMapper cPrequalMethodCriteriaMapper) {
        this.cPrequalMethodCriteriaRepository = cPrequalMethodCriteriaRepository;
        this.cPrequalMethodCriteriaMapper = cPrequalMethodCriteriaMapper;
    }

    /**
     * Return a {@link List} of {@link CPrequalMethodCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPrequalMethodCriteriaDTO> findByCriteria(CPrequalMethodCriteriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CPrequalMethodCriteria> specification = createSpecification(criteria);
        return cPrequalMethodCriteriaMapper.toDto(cPrequalMethodCriteriaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPrequalMethodCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalMethodCriteriaDTO> findByCriteria(CPrequalMethodCriteriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CPrequalMethodCriteria> specification = createSpecification(criteria);
        return cPrequalMethodCriteriaRepository.findAll(specification, page)
            .map(cPrequalMethodCriteriaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CPrequalMethodCriteriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CPrequalMethodCriteria> specification = createSpecification(criteria);
        return cPrequalMethodCriteriaRepository.count(specification);
    }

    /**
     * Function to convert {@link CPrequalMethodCriteriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CPrequalMethodCriteria> createSpecification(CPrequalMethodCriteriaCriteria criteria) {
        Specification<CPrequalMethodCriteria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CPrequalMethodCriteria_.id));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), CPrequalMethodCriteria_.weight));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CPrequalMethodCriteria_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CPrequalMethodCriteria_.active));
            }
            if (criteria.getBiddingCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingCriteriaId(),
                    root -> root.join(CPrequalMethodCriteria_.biddingCriteria, JoinType.LEFT).get(CBiddingCriteria_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CPrequalMethodCriteria_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getPrequalMethodLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalMethodLineId(),
                    root -> root.join(CPrequalMethodCriteria_.prequalMethodLine, JoinType.LEFT).get(CPrequalMethodLine_.id)));
            }
        }
        return specification;
    }
}
