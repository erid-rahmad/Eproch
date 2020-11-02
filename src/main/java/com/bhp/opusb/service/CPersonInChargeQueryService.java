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

import com.bhp.opusb.domain.CPersonInCharge;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CPersonInChargeRepository;
import com.bhp.opusb.service.dto.CPersonInChargeCriteria;
import com.bhp.opusb.service.dto.CPersonInChargeDTO;
import com.bhp.opusb.service.mapper.CPersonInChargeMapper;

/**
 * Service for executing complex queries for {@link CPersonInCharge} entities in the database.
 * The main input is a {@link CPersonInChargeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPersonInChargeDTO} or a {@link Page} of {@link CPersonInChargeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPersonInChargeQueryService extends QueryService<CPersonInCharge> {

    private final Logger log = LoggerFactory.getLogger(CPersonInChargeQueryService.class);

    private final CPersonInChargeRepository cPersonInChargeRepository;

    private final CPersonInChargeMapper cPersonInChargeMapper;

    public CPersonInChargeQueryService(CPersonInChargeRepository cPersonInChargeRepository, CPersonInChargeMapper cPersonInChargeMapper) {
        this.cPersonInChargeRepository = cPersonInChargeRepository;
        this.cPersonInChargeMapper = cPersonInChargeMapper;
    }

    /**
     * Return a {@link List} of {@link CPersonInChargeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPersonInChargeDTO> findByCriteria(CPersonInChargeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CPersonInCharge> specification = createSpecification(criteria);
        return cPersonInChargeMapper.toDto(cPersonInChargeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPersonInChargeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPersonInChargeDTO> findByCriteria(CPersonInChargeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CPersonInCharge> specification = createSpecification(criteria);
        return cPersonInChargeRepository.findAll(specification, page)
            .map(cPersonInChargeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CPersonInChargeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CPersonInCharge> specification = createSpecification(criteria);
        return cPersonInChargeRepository.count(specification);
    }

    /**
     * Function to convert {@link CPersonInChargeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CPersonInCharge> createSpecification(CPersonInChargeCriteria criteria) {
        Specification<CPersonInCharge> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CPersonInCharge_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CPersonInCharge_.uid));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), CPersonInCharge_.position));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), CPersonInCharge_.phone));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CPersonInCharge_.active));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(CPersonInCharge_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(CPersonInCharge_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CPersonInCharge_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
