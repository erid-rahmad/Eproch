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

import com.bhp.opusb.domain.AdTrigger;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdTriggerRepository;
import com.bhp.opusb.service.dto.AdTriggerCriteria;
import com.bhp.opusb.service.dto.AdTriggerDTO;
import com.bhp.opusb.service.mapper.AdTriggerMapper;

/**
 * Service for executing complex queries for {@link AdTrigger} entities in the database.
 * The main input is a {@link AdTriggerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdTriggerDTO} or a {@link Page} of {@link AdTriggerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdTriggerQueryService extends QueryService<AdTrigger> {

    private final Logger log = LoggerFactory.getLogger(AdTriggerQueryService.class);

    private final AdTriggerRepository adTriggerRepository;

    private final AdTriggerMapper adTriggerMapper;

    public AdTriggerQueryService(AdTriggerRepository adTriggerRepository, AdTriggerMapper adTriggerMapper) {
        this.adTriggerRepository = adTriggerRepository;
        this.adTriggerMapper = adTriggerMapper;
    }

    /**
     * Return a {@link List} of {@link AdTriggerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdTriggerDTO> findByCriteria(AdTriggerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdTrigger> specification = createSpecification(criteria);
        return adTriggerMapper.toDto(adTriggerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdTriggerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTriggerDTO> findByCriteria(AdTriggerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdTrigger> specification = createSpecification(criteria);
        return adTriggerRepository.findAll(specification, page)
            .map(adTriggerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdTriggerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdTrigger> specification = createSpecification(criteria);
        return adTriggerRepository.count(specification);
    }

    /**
     * Function to convert {@link AdTriggerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdTrigger> createSpecification(AdTriggerCriteria criteria) {
        Specification<AdTrigger> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdTrigger_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdTrigger_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdTrigger_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdTrigger_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), AdTrigger_.value));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AdTrigger_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), AdTrigger_.type));
            }
            if (criteria.getAdTriggerParamId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdTriggerParamId(),
                    root -> root.join(AdTrigger_.adTriggerParams, JoinType.LEFT).get(AdTriggerParam_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdTrigger_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
