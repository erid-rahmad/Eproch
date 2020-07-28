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

import com.bhp.opusb.domain.CTaxRate;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CTaxRateRepository;
import com.bhp.opusb.service.dto.CTaxRateCriteria;
import com.bhp.opusb.service.dto.CTaxRateDTO;
import com.bhp.opusb.service.mapper.CTaxRateMapper;

/**
 * Service for executing complex queries for {@link CTaxRate} entities in the database.
 * The main input is a {@link CTaxRateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CTaxRateDTO} or a {@link Page} of {@link CTaxRateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CTaxRateQueryService extends QueryService<CTaxRate> {

    private final Logger log = LoggerFactory.getLogger(CTaxRateQueryService.class);

    private final CTaxRateRepository cTaxRateRepository;

    private final CTaxRateMapper cTaxRateMapper;

    public CTaxRateQueryService(CTaxRateRepository cTaxRateRepository, CTaxRateMapper cTaxRateMapper) {
        this.cTaxRateRepository = cTaxRateRepository;
        this.cTaxRateMapper = cTaxRateMapper;
    }

    /**
     * Return a {@link List} of {@link CTaxRateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CTaxRateDTO> findByCriteria(CTaxRateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CTaxRate> specification = createSpecification(criteria);
        return cTaxRateMapper.toDto(cTaxRateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CTaxRateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CTaxRateDTO> findByCriteria(CTaxRateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CTaxRate> specification = createSpecification(criteria);
        return cTaxRateRepository.findAll(specification, page)
            .map(cTaxRateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CTaxRateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CTaxRate> specification = createSpecification(criteria);
        return cTaxRateRepository.count(specification);
    }

    /**
     * Function to convert {@link CTaxRateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CTaxRate> createSpecification(CTaxRateCriteria criteria) {
        Specification<CTaxRate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CTaxRate_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CTaxRate_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CTaxRate_.description));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRate(), CTaxRate_.rate));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), CTaxRate_.validFrom));
            }
            if (criteria.getOrderType() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderType(), CTaxRate_.orderType));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CTaxRate_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CTaxRate_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CTaxRate_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getTaxCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxCategoryId(),
                    root -> root.join(CTaxRate_.taxCategory, JoinType.LEFT).get(CTaxCategory_.id)));
            }
        }
        return specification;
    }
}
