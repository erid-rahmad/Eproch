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

import com.bhp.opusb.domain.CPrequalMethodSubCriteria;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CPrequalMethodSubCriteriaRepository;
import com.bhp.opusb.service.dto.CPrequalMethodSubCriteriaCriteria;
import com.bhp.opusb.service.dto.CPrequalMethodSubCriteriaDTO;
import com.bhp.opusb.service.mapper.CPrequalMethodSubCriteriaMapper;

/**
 * Service for executing complex queries for {@link CPrequalMethodSubCriteria} entities in the database.
 * The main input is a {@link CPrequalMethodSubCriteriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPrequalMethodSubCriteriaDTO} or a {@link Page} of {@link CPrequalMethodSubCriteriaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPrequalMethodSubCriteriaQueryService extends QueryService<CPrequalMethodSubCriteria> {

    private final Logger log = LoggerFactory.getLogger(CPrequalMethodSubCriteriaQueryService.class);

    private final CPrequalMethodSubCriteriaRepository cPrequalMethodSubCriteriaRepository;

    private final CPrequalMethodSubCriteriaMapper cPrequalMethodSubCriteriaMapper;

    public CPrequalMethodSubCriteriaQueryService(CPrequalMethodSubCriteriaRepository cPrequalMethodSubCriteriaRepository, CPrequalMethodSubCriteriaMapper cPrequalMethodSubCriteriaMapper) {
        this.cPrequalMethodSubCriteriaRepository = cPrequalMethodSubCriteriaRepository;
        this.cPrequalMethodSubCriteriaMapper = cPrequalMethodSubCriteriaMapper;
    }

    /**
     * Return a {@link List} of {@link CPrequalMethodSubCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPrequalMethodSubCriteriaDTO> findByCriteria(CPrequalMethodSubCriteriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CPrequalMethodSubCriteria> specification = createSpecification(criteria);
        return cPrequalMethodSubCriteriaMapper.toDto(cPrequalMethodSubCriteriaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPrequalMethodSubCriteriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalMethodSubCriteriaDTO> findByCriteria(CPrequalMethodSubCriteriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CPrequalMethodSubCriteria> specification = createSpecification(criteria);
        return cPrequalMethodSubCriteriaRepository.findAll(specification, page)
            .map(cPrequalMethodSubCriteriaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CPrequalMethodSubCriteriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CPrequalMethodSubCriteria> specification = createSpecification(criteria);
        return cPrequalMethodSubCriteriaRepository.count(specification);
    }

    /**
     * Function to convert {@link CPrequalMethodSubCriteriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CPrequalMethodSubCriteria> createSpecification(CPrequalMethodSubCriteriaCriteria criteria) {
        Specification<CPrequalMethodSubCriteria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CPrequalMethodSubCriteria_.id));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), CPrequalMethodSubCriteria_.weight));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CPrequalMethodSubCriteria_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CPrequalMethodSubCriteria_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CPrequalMethodSubCriteria_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingCriteriaId(),
                    root -> root.join(CPrequalMethodSubCriteria_.biddingCriteria, JoinType.LEFT).get(CBiddingCriteria_.id)));
            }
            if (criteria.getBiddingSubCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaId(),
                    root -> root.join(CPrequalMethodSubCriteria_.biddingSubCriteria, JoinType.LEFT).get(CBiddingSubCriteria_.id)));
            }
            if (criteria.getPrequalMethodCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalMethodCriteriaId(),
                    root -> root.join(CPrequalMethodSubCriteria_.prequalMethodCriteria, JoinType.LEFT).get(CPrequalMethodCriteria_.id)));
            }
        }
        return specification;
    }
}
