package com.bhp.opusb.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

// for static metamodels
import com.bhp.opusb.domain.ADOrganization_;
// for static metamodels
import com.bhp.opusb.domain.AdTaskSchedulerGroup;
import com.bhp.opusb.domain.AdTaskSchedulerGroup_;
import com.bhp.opusb.domain.AdTaskScheduler_;
import com.bhp.opusb.repository.AdTaskSchedulerGroupRepository;
import com.bhp.opusb.service.dto.AdTaskSchedulerGroupCriteria;
import com.bhp.opusb.service.dto.AdTaskSchedulerGroupDTO;
import com.bhp.opusb.service.mapper.AdTaskSchedulerGroupMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AdTaskSchedulerGroup} entities in the database.
 * The main input is a {@link AdTaskSchedulerGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdTaskSchedulerGroupDTO} or a {@link Page} of {@link AdTaskSchedulerGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdTaskSchedulerGroupQueryService extends QueryService<AdTaskSchedulerGroup> {

    private final Logger log = LoggerFactory.getLogger(AdTaskSchedulerGroupQueryService.class);

    private final AdTaskSchedulerGroupRepository adTaskSchedulerGroupRepository;

    private final AdTaskSchedulerGroupMapper adTaskSchedulerGroupMapper;

    public AdTaskSchedulerGroupQueryService(AdTaskSchedulerGroupRepository adTaskSchedulerGroupRepository, AdTaskSchedulerGroupMapper adTaskSchedulerGroupMapper) {
        this.adTaskSchedulerGroupRepository = adTaskSchedulerGroupRepository;
        this.adTaskSchedulerGroupMapper = adTaskSchedulerGroupMapper;
    }

    /**
     * Return a {@link List} of {@link AdTaskSchedulerGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdTaskSchedulerGroupDTO> findByCriteria(AdTaskSchedulerGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdTaskSchedulerGroup> specification = createSpecification(criteria);
        return adTaskSchedulerGroupMapper.toDto(adTaskSchedulerGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdTaskSchedulerGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTaskSchedulerGroupDTO> findByCriteria(AdTaskSchedulerGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdTaskSchedulerGroup> specification = createSpecification(criteria);
        return adTaskSchedulerGroupRepository.findAll(specification, page)
            .map(adTaskSchedulerGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdTaskSchedulerGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdTaskSchedulerGroup> specification = createSpecification(criteria);
        return adTaskSchedulerGroupRepository.count(specification);
    }

    /**
     * Function to convert {@link AdTaskSchedulerGroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdTaskSchedulerGroup> createSpecification(AdTaskSchedulerGroupCriteria criteria) {
        Specification<AdTaskSchedulerGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdTaskSchedulerGroup_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdTaskSchedulerGroup_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdTaskSchedulerGroup_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdTaskSchedulerGroup_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), AdTaskSchedulerGroup_.value));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AdTaskSchedulerGroup_.description));
            }
            if (criteria.getAdTaskSchedulerId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdTaskSchedulerId(),
                    root -> root.join(AdTaskSchedulerGroup_.adTaskSchedulers, JoinType.LEFT).get(AdTaskScheduler_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdTaskSchedulerGroup_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
