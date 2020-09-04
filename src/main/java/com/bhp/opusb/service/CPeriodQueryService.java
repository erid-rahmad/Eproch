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

import com.bhp.opusb.domain.CPeriod;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CPeriodRepository;
import com.bhp.opusb.service.dto.CPeriodCriteria;
import com.bhp.opusb.service.dto.CPeriodDTO;
import com.bhp.opusb.service.mapper.CPeriodMapper;

/**
 * Service for executing complex queries for {@link CPeriod} entities in the database.
 * The main input is a {@link CPeriodCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPeriodDTO} or a {@link Page} of {@link CPeriodDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPeriodQueryService extends QueryService<CPeriod> {

    private final Logger log = LoggerFactory.getLogger(CPeriodQueryService.class);

    private final CPeriodRepository cPeriodRepository;

    private final CPeriodMapper cPeriodMapper;

    public CPeriodQueryService(CPeriodRepository cPeriodRepository, CPeriodMapper cPeriodMapper) {
        this.cPeriodRepository = cPeriodRepository;
        this.cPeriodMapper = cPeriodMapper;
    }

    /**
     * Return a {@link List} of {@link CPeriodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPeriodDTO> findByCriteria(CPeriodCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CPeriod> specification = createSpecification(criteria);
        return cPeriodMapper.toDto(cPeriodRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPeriodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPeriodDTO> findByCriteria(CPeriodCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CPeriod> specification = createSpecification(criteria);
        return cPeriodRepository.findAll(specification, page)
            .map(cPeriodMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CPeriodCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CPeriod> specification = createSpecification(criteria);
        return cPeriodRepository.count(specification);
    }

    /**
     * Function to convert {@link CPeriodCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CPeriod> createSpecification(CPeriodCriteria criteria) {
        Specification<CPeriod> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CPeriod_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CPeriod_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CPeriod_.description));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), CPeriod_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), CPeriod_.endDate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CPeriod_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CPeriod_.active));
            }
            if (criteria.getCalendarId() != null) {
                specification = specification.and(buildSpecification(criteria.getCalendarId(),
                    root -> root.join(CPeriod_.calendar, JoinType.LEFT).get(CCalendar_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CPeriod_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
