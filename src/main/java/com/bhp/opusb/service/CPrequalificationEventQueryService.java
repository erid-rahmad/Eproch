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

import com.bhp.opusb.domain.CPrequalificationEvent;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CPrequalificationEventRepository;
import com.bhp.opusb.service.dto.CPrequalificationEventCriteria;
import com.bhp.opusb.service.dto.CPrequalificationEventDTO;
import com.bhp.opusb.service.mapper.CPrequalificationEventMapper;

/**
 * Service for executing complex queries for {@link CPrequalificationEvent} entities in the database.
 * The main input is a {@link CPrequalificationEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPrequalificationEventDTO} or a {@link Page} of {@link CPrequalificationEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPrequalificationEventQueryService extends QueryService<CPrequalificationEvent> {

    private final Logger log = LoggerFactory.getLogger(CPrequalificationEventQueryService.class);

    private final CPrequalificationEventRepository cPrequalificationEventRepository;

    private final CPrequalificationEventMapper cPrequalificationEventMapper;

    public CPrequalificationEventQueryService(CPrequalificationEventRepository cPrequalificationEventRepository, CPrequalificationEventMapper cPrequalificationEventMapper) {
        this.cPrequalificationEventRepository = cPrequalificationEventRepository;
        this.cPrequalificationEventMapper = cPrequalificationEventMapper;
    }

    /**
     * Return a {@link List} of {@link CPrequalificationEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPrequalificationEventDTO> findByCriteria(CPrequalificationEventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CPrequalificationEvent> specification = createSpecification(criteria);
        return cPrequalificationEventMapper.toDto(cPrequalificationEventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPrequalificationEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalificationEventDTO> findByCriteria(CPrequalificationEventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CPrequalificationEvent> specification = createSpecification(criteria);
        return cPrequalificationEventRepository.findAll(specification, page)
            .map(cPrequalificationEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CPrequalificationEventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CPrequalificationEvent> specification = createSpecification(criteria);
        return cPrequalificationEventRepository.count(specification);
    }

    /**
     * Function to convert {@link CPrequalificationEventCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CPrequalificationEvent> createSpecification(CPrequalificationEventCriteria criteria) {
        Specification<CPrequalificationEvent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CPrequalificationEvent_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CPrequalificationEvent_.name));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CPrequalificationEvent_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CPrequalificationEvent_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CPrequalificationEvent_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
