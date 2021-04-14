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

import com.bhp.opusb.domain.CEvent;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CEventRepository;
import com.bhp.opusb.service.dto.CEventCriteria;
import com.bhp.opusb.service.dto.CEventDTO;
import com.bhp.opusb.service.mapper.CEventMapper;

/**
 * Service for executing complex queries for {@link CEvent} entities in the database.
 * The main input is a {@link CEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CEventDTO} or a {@link Page} of {@link CEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CEventQueryService extends QueryService<CEvent> {

    private final Logger log = LoggerFactory.getLogger(CEventQueryService.class);

    private final CEventRepository cEventRepository;

    private final CEventMapper cEventMapper;

    public CEventQueryService(CEventRepository cEventRepository, CEventMapper cEventMapper) {
        this.cEventRepository = cEventRepository;
        this.cEventMapper = cEventMapper;
    }

    /**
     * Return a {@link List} of {@link CEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CEventDTO> findByCriteria(CEventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CEvent> specification = createSpecification(criteria);
        return cEventMapper.toDto(cEventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CEventDTO> findByCriteria(CEventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CEvent> specification = createSpecification(criteria);
        return cEventRepository.findAll(specification, page)
            .map(cEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CEventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CEvent> specification = createSpecification(criteria);
        return cEventRepository.count(specification);
    }

    /**
     * Function to convert {@link CEventCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CEvent> createSpecification(CEventCriteria criteria) {
        Specification<CEvent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CEvent_.id));
            }
            if (criteria.getEvent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvent(), CEvent_.event));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CEvent_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CEvent_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CEvent_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CEvent_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCProductClassificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getCProductClassificationId(),
                    root -> root.join(CEvent_.cProductClassification, JoinType.LEFT).get(CProductClassification_.id)));
            }
        }
        return specification;
    }
}
