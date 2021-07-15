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

import com.bhp.opusb.domain.MPrequalificationEvent;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalificationEventRepository;
import com.bhp.opusb.service.dto.MPrequalificationEventCriteria;
import com.bhp.opusb.service.dto.MPrequalificationEventDTO;
import com.bhp.opusb.service.mapper.MPrequalificationEventMapper;

/**
 * Service for executing complex queries for {@link MPrequalificationEvent} entities in the database.
 * The main input is a {@link MPrequalificationEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalificationEventDTO} or a {@link Page} of {@link MPrequalificationEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalificationEventQueryService extends QueryService<MPrequalificationEvent> {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationEventQueryService.class);

    private final MPrequalificationEventRepository mPrequalificationEventRepository;

    private final MPrequalificationEventMapper mPrequalificationEventMapper;

    public MPrequalificationEventQueryService(MPrequalificationEventRepository mPrequalificationEventRepository, MPrequalificationEventMapper mPrequalificationEventMapper) {
        this.mPrequalificationEventRepository = mPrequalificationEventRepository;
        this.mPrequalificationEventMapper = mPrequalificationEventMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalificationEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalificationEventDTO> findByCriteria(MPrequalificationEventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalificationEvent> specification = createSpecification(criteria);
        return mPrequalificationEventMapper.toDto(mPrequalificationEventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalificationEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationEventDTO> findByCriteria(MPrequalificationEventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalificationEvent> specification = createSpecification(criteria);
        return mPrequalificationEventRepository.findAll(specification, page)
            .map(mPrequalificationEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalificationEventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalificationEvent> specification = createSpecification(criteria);
        return mPrequalificationEventRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalificationEventCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalificationEvent> createSpecification(MPrequalificationEventCriteria criteria) {
        Specification<MPrequalificationEvent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalificationEvent_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalificationEvent_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalificationEvent_.active));
            }
            if (criteria.getPrequalificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationId(),
                    root -> root.join(MPrequalificationEvent_.prequalification, JoinType.LEFT).get(MPrequalificationInformation_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalificationEvent_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getEventId() != null) {
                specification = specification.and(buildSpecification(criteria.getEventId(),
                    root -> root.join(MPrequalificationEvent_.event, JoinType.LEFT).get(CPrequalificationEvent_.id)));
            }
            if (criteria.getMethodId() != null) {
                specification = specification.and(buildSpecification(criteria.getMethodId(),
                    root -> root.join(MPrequalificationEvent_.method, JoinType.LEFT).get(CPrequalificationMethod_.id)));
            }
        }
        return specification;
    }
}
