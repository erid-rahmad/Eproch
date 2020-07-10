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

import com.bhp.opusb.domain.AdTaskProcess;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdTaskProcessRepository;
import com.bhp.opusb.service.dto.AdTaskProcessCriteria;
import com.bhp.opusb.service.dto.AdTaskProcessDTO;
import com.bhp.opusb.service.mapper.AdTaskProcessMapper;

/**
 * Service for executing complex queries for {@link AdTaskProcess} entities in the database.
 * The main input is a {@link AdTaskProcessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdTaskProcessDTO} or a {@link Page} of {@link AdTaskProcessDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdTaskProcessQueryService extends QueryService<AdTaskProcess> {

    private final Logger log = LoggerFactory.getLogger(AdTaskProcessQueryService.class);

    private final AdTaskProcessRepository adTaskProcessRepository;

    private final AdTaskProcessMapper adTaskProcessMapper;

    public AdTaskProcessQueryService(AdTaskProcessRepository adTaskProcessRepository, AdTaskProcessMapper adTaskProcessMapper) {
        this.adTaskProcessRepository = adTaskProcessRepository;
        this.adTaskProcessMapper = adTaskProcessMapper;
    }

    /**
     * Return a {@link List} of {@link AdTaskProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdTaskProcessDTO> findByCriteria(AdTaskProcessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdTaskProcess> specification = createSpecification(criteria);
        return adTaskProcessMapper.toDto(adTaskProcessRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdTaskProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTaskProcessDTO> findByCriteria(AdTaskProcessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdTaskProcess> specification = createSpecification(criteria);
        return adTaskProcessRepository.findAll(specification, page)
            .map(adTaskProcessMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdTaskProcessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdTaskProcess> specification = createSpecification(criteria);
        return adTaskProcessRepository.count(specification);
    }

    /**
     * Function to convert {@link AdTaskProcessCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdTaskProcess> createSpecification(AdTaskProcessCriteria criteria) {
        Specification<AdTaskProcess> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdTaskProcess_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdTaskProcess_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdTaskProcess_.active));
            }
            if (criteria.getRunSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRunSequence(), AdTaskProcess_.runSequence));
            }
            if (criteria.getParallel() != null) {
                specification = specification.and(buildSpecification(criteria.getParallel(), AdTaskProcess_.parallel));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdTaskProcess_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAdTaskApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdTaskApplicationId(),
                    root -> root.join(AdTaskProcess_.adTaskApplication, JoinType.LEFT).get(AdTaskApplication_.id)));
            }
            if (criteria.getAdTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdTaskId(),
                    root -> root.join(AdTaskProcess_.adTask, JoinType.LEFT).get(AdTask_.id)));
            }
        }
        return specification;
    }
}
