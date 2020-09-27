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

import com.bhp.opusb.domain.AdCallout;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdCalloutRepository;
import com.bhp.opusb.service.dto.AdCalloutCriteria;
import com.bhp.opusb.service.dto.AdCalloutDTO;
import com.bhp.opusb.service.mapper.AdCalloutMapper;

/**
 * Service for executing complex queries for {@link AdCallout} entities in the database.
 * The main input is a {@link AdCalloutCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdCalloutDTO} or a {@link Page} of {@link AdCalloutDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdCalloutQueryService extends QueryService<AdCallout> {

    private final Logger log = LoggerFactory.getLogger(AdCalloutQueryService.class);

    private final AdCalloutRepository adCalloutRepository;

    private final AdCalloutMapper adCalloutMapper;

    public AdCalloutQueryService(AdCalloutRepository adCalloutRepository, AdCalloutMapper adCalloutMapper) {
        this.adCalloutRepository = adCalloutRepository;
        this.adCalloutMapper = adCalloutMapper;
    }

    /**
     * Return a {@link List} of {@link AdCalloutDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdCalloutDTO> findByCriteria(AdCalloutCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdCallout> specification = createSpecification(criteria);
        return adCalloutMapper.toDto(adCalloutRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdCalloutDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdCalloutDTO> findByCriteria(AdCalloutCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdCallout> specification = createSpecification(criteria);
        return adCalloutRepository.findAll(specification, page)
            .map(adCalloutMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdCalloutCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdCallout> specification = createSpecification(criteria);
        return adCalloutRepository.count(specification);
    }

    /**
     * Function to convert {@link AdCalloutCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdCallout> createSpecification(AdCalloutCriteria criteria) {
        Specification<AdCallout> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdCallout_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdCallout_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdCallout_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdCallout_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AdCallout_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), AdCallout_.type));
            }
            if (criteria.getAdCalloutTargetId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdCalloutTargetId(),
                    root -> root.join(AdCallout_.adCalloutTargets, JoinType.LEFT).get(AdCalloutTarget_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdCallout_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getTriggerId() != null) {
                specification = specification.and(buildSpecification(criteria.getTriggerId(),
                    root -> root.join(AdCallout_.trigger, JoinType.LEFT).get(AdTrigger_.id)));
            }
            if (criteria.getFieldId() != null) {
                specification = specification.and(buildSpecification(criteria.getFieldId(),
                    root -> root.join(AdCallout_.field, JoinType.LEFT).get(ADField_.id)));
            }
        }
        return specification;
    }
}
