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

import com.bhp.opusb.domain.CCalendar;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CCalendarRepository;
import com.bhp.opusb.service.dto.CCalendarCriteria;
import com.bhp.opusb.service.dto.CCalendarDTO;
import com.bhp.opusb.service.mapper.CCalendarMapper;

/**
 * Service for executing complex queries for {@link CCalendar} entities in the database.
 * The main input is a {@link CCalendarCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CCalendarDTO} or a {@link Page} of {@link CCalendarDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CCalendarQueryService extends QueryService<CCalendar> {

    private final Logger log = LoggerFactory.getLogger(CCalendarQueryService.class);

    private final CCalendarRepository cCalendarRepository;

    private final CCalendarMapper cCalendarMapper;

    public CCalendarQueryService(CCalendarRepository cCalendarRepository, CCalendarMapper cCalendarMapper) {
        this.cCalendarRepository = cCalendarRepository;
        this.cCalendarMapper = cCalendarMapper;
    }

    /**
     * Return a {@link List} of {@link CCalendarDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CCalendarDTO> findByCriteria(CCalendarCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CCalendar> specification = createSpecification(criteria);
        return cCalendarMapper.toDto(cCalendarRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CCalendarDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CCalendarDTO> findByCriteria(CCalendarCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CCalendar> specification = createSpecification(criteria);
        return cCalendarRepository.findAll(specification, page)
            .map(cCalendarMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CCalendarCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CCalendar> specification = createSpecification(criteria);
        return cCalendarRepository.count(specification);
    }

    /**
     * Function to convert {@link CCalendarCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CCalendar> createSpecification(CCalendarCriteria criteria) {
        Specification<CCalendar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CCalendar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CCalendar_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CCalendar_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CCalendar_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CCalendar_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CCalendar_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
