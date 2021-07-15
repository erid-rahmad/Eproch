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

import com.bhp.opusb.domain.CPrequalificationEventLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CPrequalificationEventLineRepository;
import com.bhp.opusb.service.dto.CPrequalificationEventLineCriteria;
import com.bhp.opusb.service.dto.CPrequalificationEventLineDTO;
import com.bhp.opusb.service.mapper.CPrequalificationEventLineMapper;

/**
 * Service for executing complex queries for {@link CPrequalificationEventLine} entities in the database.
 * The main input is a {@link CPrequalificationEventLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPrequalificationEventLineDTO} or a {@link Page} of {@link CPrequalificationEventLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPrequalificationEventLineQueryService extends QueryService<CPrequalificationEventLine> {

    private final Logger log = LoggerFactory.getLogger(CPrequalificationEventLineQueryService.class);

    private final CPrequalificationEventLineRepository cPrequalificationEventLineRepository;

    private final CPrequalificationEventLineMapper cPrequalificationEventLineMapper;

    public CPrequalificationEventLineQueryService(CPrequalificationEventLineRepository cPrequalificationEventLineRepository, CPrequalificationEventLineMapper cPrequalificationEventLineMapper) {
        this.cPrequalificationEventLineRepository = cPrequalificationEventLineRepository;
        this.cPrequalificationEventLineMapper = cPrequalificationEventLineMapper;
    }

    /**
     * Return a {@link List} of {@link CPrequalificationEventLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPrequalificationEventLineDTO> findByCriteria(CPrequalificationEventLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CPrequalificationEventLine> specification = createSpecification(criteria);
        return cPrequalificationEventLineMapper.toDto(cPrequalificationEventLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPrequalificationEventLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalificationEventLineDTO> findByCriteria(CPrequalificationEventLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CPrequalificationEventLine> specification = createSpecification(criteria);
        return cPrequalificationEventLineRepository.findAll(specification, page)
            .map(cPrequalificationEventLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CPrequalificationEventLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CPrequalificationEventLine> specification = createSpecification(criteria);
        return cPrequalificationEventLineRepository.count(specification);
    }

    /**
     * Function to convert {@link CPrequalificationEventLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CPrequalificationEventLine> createSpecification(CPrequalificationEventLineCriteria criteria) {
        Specification<CPrequalificationEventLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CPrequalificationEventLine_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CPrequalificationEventLine_.description));
            }
            if (criteria.getSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequence(), CPrequalificationEventLine_.sequence));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CPrequalificationEventLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CPrequalificationEventLine_.active));
            }
            if (criteria.getPrequalificationEventId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationEventId(),
                    root -> root.join(CPrequalificationEventLine_.prequalificationEvent, JoinType.LEFT).get(CPrequalificationEvent_.id)));
            }
            if (criteria.getPrequalificationStepId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationStepId(),
                    root -> root.join(CPrequalificationEventLine_.prequalificationStep, JoinType.LEFT).get(CPrequalificationStep_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CPrequalificationEventLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
