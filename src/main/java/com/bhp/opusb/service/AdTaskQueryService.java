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

import com.bhp.opusb.domain.AdTask;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdTaskRepository;
import com.bhp.opusb.service.dto.AdTaskCriteria;
import com.bhp.opusb.service.dto.AdTaskDTO;
import com.bhp.opusb.service.mapper.AdTaskMapper;

/**
 * Service for executing complex queries for {@link AdTask} entities in the database.
 * The main input is a {@link AdTaskCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdTaskDTO} or a {@link Page} of {@link AdTaskDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdTaskQueryService extends QueryService<AdTask> {

    private final Logger log = LoggerFactory.getLogger(AdTaskQueryService.class);

    private final AdTaskRepository adTaskRepository;

    private final AdTaskMapper adTaskMapper;

    public AdTaskQueryService(AdTaskRepository adTaskRepository, AdTaskMapper adTaskMapper) {
        this.adTaskRepository = adTaskRepository;
        this.adTaskMapper = adTaskMapper;
    }

    /**
     * Return a {@link List} of {@link AdTaskDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdTaskDTO> findByCriteria(AdTaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdTask> specification = createSpecification(criteria);
        return adTaskMapper.toDto(adTaskRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdTaskDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTaskDTO> findByCriteria(AdTaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdTask> specification = createSpecification(criteria);
        return adTaskRepository.findAll(specification, page)
            .map(adTaskMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdTaskCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdTask> specification = createSpecification(criteria);
        return adTaskRepository.count(specification);
    }

    /**
     * Function to convert {@link AdTaskCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdTask> createSpecification(AdTaskCriteria criteria) {
        Specification<AdTask> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdTask_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdTask_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdTask_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdTask_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), AdTask_.value));
            }
            if (criteria.getAsync() != null) {
                specification = specification.and(buildSpecification(criteria.getAsync(), AdTask_.async));
            }
            if (criteria.getAdTaskProcessId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdTaskProcessId(),
                    root -> root.join(AdTask_.adTaskProcesses, JoinType.LEFT).get(AdTaskProcess_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdTask_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
