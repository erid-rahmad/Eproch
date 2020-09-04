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

import com.bhp.opusb.domain.CNonBusinessDay;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CNonBusinessDayRepository;
import com.bhp.opusb.service.dto.CNonBusinessDayCriteria;
import com.bhp.opusb.service.dto.CNonBusinessDayDTO;
import com.bhp.opusb.service.mapper.CNonBusinessDayMapper;

/**
 * Service for executing complex queries for {@link CNonBusinessDay} entities in the database.
 * The main input is a {@link CNonBusinessDayCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CNonBusinessDayDTO} or a {@link Page} of {@link CNonBusinessDayDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CNonBusinessDayQueryService extends QueryService<CNonBusinessDay> {

    private final Logger log = LoggerFactory.getLogger(CNonBusinessDayQueryService.class);

    private final CNonBusinessDayRepository cNonBusinessDayRepository;

    private final CNonBusinessDayMapper cNonBusinessDayMapper;

    public CNonBusinessDayQueryService(CNonBusinessDayRepository cNonBusinessDayRepository, CNonBusinessDayMapper cNonBusinessDayMapper) {
        this.cNonBusinessDayRepository = cNonBusinessDayRepository;
        this.cNonBusinessDayMapper = cNonBusinessDayMapper;
    }

    /**
     * Return a {@link List} of {@link CNonBusinessDayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CNonBusinessDayDTO> findByCriteria(CNonBusinessDayCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CNonBusinessDay> specification = createSpecification(criteria);
        return cNonBusinessDayMapper.toDto(cNonBusinessDayRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CNonBusinessDayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CNonBusinessDayDTO> findByCriteria(CNonBusinessDayCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CNonBusinessDay> specification = createSpecification(criteria);
        return cNonBusinessDayRepository.findAll(specification, page)
            .map(cNonBusinessDayMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CNonBusinessDayCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CNonBusinessDay> specification = createSpecification(criteria);
        return cNonBusinessDayRepository.count(specification);
    }

    /**
     * Function to convert {@link CNonBusinessDayCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CNonBusinessDay> createSpecification(CNonBusinessDayCriteria criteria) {
        Specification<CNonBusinessDay> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CNonBusinessDay_.id));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), CNonBusinessDay_.category));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), CNonBusinessDay_.date));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CNonBusinessDay_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CNonBusinessDay_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CNonBusinessDay_.active));
            }
            if (criteria.getCalendarId() != null) {
                specification = specification.and(buildSpecification(criteria.getCalendarId(),
                    root -> root.join(CNonBusinessDay_.calendar, JoinType.LEFT).get(CCalendar_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CNonBusinessDay_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
