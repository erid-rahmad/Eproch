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

import com.bhp.opusb.domain.CEventTypeline;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CEventTypelineRepository;
import com.bhp.opusb.service.dto.CEventTypelineCriteria;
import com.bhp.opusb.service.dto.CEventTypelineDTO;
import com.bhp.opusb.service.mapper.CEventTypelineMapper;

/**
 * Service for executing complex queries for {@link CEventTypeline} entities in the database.
 * The main input is a {@link CEventTypelineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CEventTypelineDTO} or a {@link Page} of {@link CEventTypelineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CEventTypelineQueryService extends QueryService<CEventTypeline> {

    private final Logger log = LoggerFactory.getLogger(CEventTypelineQueryService.class);

    private final CEventTypelineRepository cEventTypelineRepository;

    private final CEventTypelineMapper cEventTypelineMapper;

    public CEventTypelineQueryService(CEventTypelineRepository cEventTypelineRepository, CEventTypelineMapper cEventTypelineMapper) {
        this.cEventTypelineRepository = cEventTypelineRepository;
        this.cEventTypelineMapper = cEventTypelineMapper;
    }

    /**
     * Return a {@link List} of {@link CEventTypelineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CEventTypelineDTO> findByCriteria(CEventTypelineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CEventTypeline> specification = createSpecification(criteria);
        return cEventTypelineMapper.toDto(cEventTypelineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CEventTypelineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CEventTypelineDTO> findByCriteria(CEventTypelineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CEventTypeline> specification = createSpecification(criteria);
        return cEventTypelineRepository.findAll(specification, page)
            .map(cEventTypelineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CEventTypelineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CEventTypeline> specification = createSpecification(criteria);
        return cEventTypelineRepository.count(specification);
    }

    /**
     * Function to convert {@link CEventTypelineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CEventTypeline> createSpecification(CEventTypelineCriteria criteria) {
        Specification<CEventTypeline> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CEventTypeline_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CEventTypeline_.description));
            }
            if (criteria.getSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequence(), CEventTypeline_.sequence));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CEventTypeline_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CEventTypeline_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CEventTypeline_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getEventTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEventTypeId(),
                    root -> root.join(CEventTypeline_.eventType, JoinType.LEFT).get(CEventType_.id)));
            }
            if (criteria.getCEventId() != null) {
                specification = specification.and(buildSpecification(criteria.getCEventId(),
                    root -> root.join(CEventTypeline_.cEvent, JoinType.LEFT).get(CEvent_.id)));
            }
        }
        return specification;
    }
}
