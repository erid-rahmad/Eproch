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

import com.bhp.opusb.domain.MPrequalificationSchedule;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalificationScheduleRepository;
import com.bhp.opusb.service.dto.MPrequalificationScheduleCriteria;
import com.bhp.opusb.service.dto.MPrequalificationScheduleDTO;
import com.bhp.opusb.service.mapper.MPrequalificationScheduleMapper;

/**
 * Service for executing complex queries for {@link MPrequalificationSchedule} entities in the database.
 * The main input is a {@link MPrequalificationScheduleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalificationScheduleDTO} or a {@link Page} of {@link MPrequalificationScheduleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalificationScheduleQueryService extends QueryService<MPrequalificationSchedule> {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationScheduleQueryService.class);

    private final MPrequalificationScheduleRepository mPrequalificationScheduleRepository;

    private final MPrequalificationScheduleMapper mPrequalificationScheduleMapper;

    public MPrequalificationScheduleQueryService(MPrequalificationScheduleRepository mPrequalificationScheduleRepository, MPrequalificationScheduleMapper mPrequalificationScheduleMapper) {
        this.mPrequalificationScheduleRepository = mPrequalificationScheduleRepository;
        this.mPrequalificationScheduleMapper = mPrequalificationScheduleMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalificationScheduleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalificationScheduleDTO> findByCriteria(MPrequalificationScheduleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalificationSchedule> specification = createSpecification(criteria);
        return mPrequalificationScheduleMapper.toDto(mPrequalificationScheduleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalificationScheduleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationScheduleDTO> findByCriteria(MPrequalificationScheduleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalificationSchedule> specification = createSpecification(criteria);
        return mPrequalificationScheduleRepository.findAll(specification, page)
            .map(mPrequalificationScheduleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalificationScheduleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalificationSchedule> specification = createSpecification(criteria);
        return mPrequalificationScheduleRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalificationScheduleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalificationSchedule> createSpecification(MPrequalificationScheduleCriteria criteria) {
        Specification<MPrequalificationSchedule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalificationSchedule_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), MPrequalificationSchedule_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), MPrequalificationSchedule_.endDate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalificationSchedule_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalificationSchedule_.active));
            }
            if (criteria.getPrequalificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationId(),
                    root -> root.join(MPrequalificationSchedule_.prequalification, JoinType.LEFT).get(MPrequalificationInformation_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalificationSchedule_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getEventLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getEventLineId(),
                    root -> root.join(MPrequalificationSchedule_.eventLine, JoinType.LEFT).get(CPrequalificationEventLine_.id)));
            }
        }
        return specification;
    }
}
