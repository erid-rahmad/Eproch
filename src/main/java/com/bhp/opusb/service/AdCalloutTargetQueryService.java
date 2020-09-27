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

import com.bhp.opusb.domain.AdCalloutTarget;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdCalloutTargetRepository;
import com.bhp.opusb.service.dto.AdCalloutTargetCriteria;
import com.bhp.opusb.service.dto.AdCalloutTargetDTO;
import com.bhp.opusb.service.mapper.AdCalloutTargetMapper;

/**
 * Service for executing complex queries for {@link AdCalloutTarget} entities in the database.
 * The main input is a {@link AdCalloutTargetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdCalloutTargetDTO} or a {@link Page} of {@link AdCalloutTargetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdCalloutTargetQueryService extends QueryService<AdCalloutTarget> {

    private final Logger log = LoggerFactory.getLogger(AdCalloutTargetQueryService.class);

    private final AdCalloutTargetRepository adCalloutTargetRepository;

    private final AdCalloutTargetMapper adCalloutTargetMapper;

    public AdCalloutTargetQueryService(AdCalloutTargetRepository adCalloutTargetRepository, AdCalloutTargetMapper adCalloutTargetMapper) {
        this.adCalloutTargetRepository = adCalloutTargetRepository;
        this.adCalloutTargetMapper = adCalloutTargetMapper;
    }

    /**
     * Return a {@link List} of {@link AdCalloutTargetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdCalloutTargetDTO> findByCriteria(AdCalloutTargetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdCalloutTarget> specification = createSpecification(criteria);
        return adCalloutTargetMapper.toDto(adCalloutTargetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdCalloutTargetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdCalloutTargetDTO> findByCriteria(AdCalloutTargetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdCalloutTarget> specification = createSpecification(criteria);
        return adCalloutTargetRepository.findAll(specification, page)
            .map(adCalloutTargetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdCalloutTargetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdCalloutTarget> specification = createSpecification(criteria);
        return adCalloutTargetRepository.count(specification);
    }

    /**
     * Function to convert {@link AdCalloutTargetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdCalloutTarget> createSpecification(AdCalloutTargetCriteria criteria) {
        Specification<AdCalloutTarget> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdCalloutTarget_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdCalloutTarget_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdCalloutTarget_.active));
            }
            if (criteria.getSourceField() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourceField(), AdCalloutTarget_.sourceField));
            }
            if (criteria.getTargetName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTargetName(), AdCalloutTarget_.targetName));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdCalloutTarget_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCalloutId() != null) {
                specification = specification.and(buildSpecification(criteria.getCalloutId(),
                    root -> root.join(AdCalloutTarget_.callout, JoinType.LEFT).get(AdCallout_.id)));
            }
        }
        return specification;
    }
}
