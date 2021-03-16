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

import com.bhp.opusb.domain.CPaymentSchedule;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CPaymentScheduleRepository;
import com.bhp.opusb.service.dto.CPaymentScheduleCriteria;
import com.bhp.opusb.service.dto.CPaymentScheduleDTO;
import com.bhp.opusb.service.mapper.CPaymentScheduleMapper;

/**
 * Service for executing complex queries for {@link CPaymentSchedule} entities in the database.
 * The main input is a {@link CPaymentScheduleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPaymentScheduleDTO} or a {@link Page} of {@link CPaymentScheduleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPaymentScheduleQueryService extends QueryService<CPaymentSchedule> {

    private final Logger log = LoggerFactory.getLogger(CPaymentScheduleQueryService.class);

    private final CPaymentScheduleRepository cPaymentScheduleRepository;

    private final CPaymentScheduleMapper cPaymentScheduleMapper;

    public CPaymentScheduleQueryService(CPaymentScheduleRepository cPaymentScheduleRepository, CPaymentScheduleMapper cPaymentScheduleMapper) {
        this.cPaymentScheduleRepository = cPaymentScheduleRepository;
        this.cPaymentScheduleMapper = cPaymentScheduleMapper;
    }

    /**
     * Return a {@link List} of {@link CPaymentScheduleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPaymentScheduleDTO> findByCriteria(CPaymentScheduleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CPaymentSchedule> specification = createSpecification(criteria);
        return cPaymentScheduleMapper.toDto(cPaymentScheduleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPaymentScheduleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPaymentScheduleDTO> findByCriteria(CPaymentScheduleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CPaymentSchedule> specification = createSpecification(criteria);
        return cPaymentScheduleRepository.findAll(specification, page)
            .map(cPaymentScheduleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CPaymentScheduleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CPaymentSchedule> specification = createSpecification(criteria);
        return cPaymentScheduleRepository.count(specification);
    }

    /**
     * Function to convert {@link CPaymentScheduleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CPaymentSchedule> createSpecification(CPaymentScheduleCriteria criteria) {
        Specification<CPaymentSchedule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CPaymentSchedule_.id));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), CPaymentSchedule_.discount));
            }
            if (criteria.getDiscountDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountDays(), CPaymentSchedule_.discountDays));
            }
            if (criteria.getGraceDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGraceDays(), CPaymentSchedule_.graceDays));
            }
            if (criteria.getNetDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNetDay(), CPaymentSchedule_.netDay));
            }
            if (criteria.getNetDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNetDays(), CPaymentSchedule_.netDays));
            }
            if (criteria.getPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentage(), CPaymentSchedule_.percentage));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), CPaymentSchedule_.valid));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CPaymentSchedule_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCPaymentTermId() != null) {
                specification = specification.and(buildSpecification(criteria.getCPaymentTermId(),
                    root -> root.join(CPaymentSchedule_.cPaymentTerm, JoinType.LEFT).get(CPaymentTerm_.id)));
            }
        }
        return specification;
    }
}
