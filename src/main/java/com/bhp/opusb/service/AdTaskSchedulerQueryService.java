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

import com.bhp.opusb.domain.AdTaskScheduler;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdTaskSchedulerRepository;
import com.bhp.opusb.service.dto.AdTaskSchedulerCriteria;
import com.bhp.opusb.service.dto.AdTaskSchedulerDTO;
import com.bhp.opusb.service.mapper.AdTaskSchedulerMapper;

/**
 * Service for executing complex queries for {@link AdTaskScheduler} entities in the database.
 * The main input is a {@link AdTaskSchedulerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdTaskSchedulerDTO} or a {@link Page} of {@link AdTaskSchedulerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdTaskSchedulerQueryService extends QueryService<AdTaskScheduler> {

    private final Logger log = LoggerFactory.getLogger(AdTaskSchedulerQueryService.class);

    private final AdTaskSchedulerRepository adTaskSchedulerRepository;

    private final AdTaskSchedulerMapper adTaskSchedulerMapper;

    public AdTaskSchedulerQueryService(AdTaskSchedulerRepository adTaskSchedulerRepository, AdTaskSchedulerMapper adTaskSchedulerMapper) {
        this.adTaskSchedulerRepository = adTaskSchedulerRepository;
        this.adTaskSchedulerMapper = adTaskSchedulerMapper;
    }

    /**
     * Return a {@link List} of {@link AdTaskSchedulerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdTaskSchedulerDTO> findByCriteria(AdTaskSchedulerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdTaskScheduler> specification = createSpecification(criteria);
        return adTaskSchedulerMapper.toDto(adTaskSchedulerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdTaskSchedulerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTaskSchedulerDTO> findByCriteria(AdTaskSchedulerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdTaskScheduler> specification = createSpecification(criteria);
        return adTaskSchedulerRepository.findAll(specification, page)
            .map(adTaskSchedulerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdTaskSchedulerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdTaskScheduler> specification = createSpecification(criteria);
        return adTaskSchedulerRepository.count(specification);
    }

    /**
     * Function to convert {@link AdTaskSchedulerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdTaskScheduler> createSpecification(AdTaskSchedulerCriteria criteria) {
        Specification<AdTaskScheduler> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdTaskScheduler_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdTaskScheduler_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdTaskScheduler_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdTaskScheduler_.name));
            }
            if (criteria.getTrigger() != null) {
                specification = specification.and(buildSpecification(criteria.getTrigger(), AdTaskScheduler_.trigger));
            }
            if (criteria.getCronExpression() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCronExpression(), AdTaskScheduler_.cronExpression));
            }
            if (criteria.getPeriodicCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodicCount(), AdTaskScheduler_.periodicCount));
            }
            if (criteria.getPeriodicUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPeriodicUnit(), AdTaskScheduler_.periodicUnit));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdTaskScheduler_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAdTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdTaskId(),
                    root -> root.join(AdTaskScheduler_.adTask, JoinType.LEFT).get(AdTask_.id)));
            }
        }
        return specification;
    }
}
