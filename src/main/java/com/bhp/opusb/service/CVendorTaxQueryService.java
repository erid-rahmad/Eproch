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

import com.bhp.opusb.domain.CVendorTax;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CVendorTaxRepository;
import com.bhp.opusb.service.dto.CVendorTaxCriteria;
import com.bhp.opusb.service.dto.CVendorTaxDTO;
import com.bhp.opusb.service.mapper.CVendorTaxMapper;

/**
 * Service for executing complex queries for {@link CVendorTax} entities in the database.
 * The main input is a {@link CVendorTaxCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CVendorTaxDTO} or a {@link Page} of {@link CVendorTaxDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CVendorTaxQueryService extends QueryService<CVendorTax> {

    private final Logger log = LoggerFactory.getLogger(CVendorTaxQueryService.class);

    private final CVendorTaxRepository cVendorTaxRepository;

    private final CVendorTaxMapper cVendorTaxMapper;

    public CVendorTaxQueryService(CVendorTaxRepository cVendorTaxRepository, CVendorTaxMapper cVendorTaxMapper) {
        this.cVendorTaxRepository = cVendorTaxRepository;
        this.cVendorTaxMapper = cVendorTaxMapper;
    }

    /**
     * Return a {@link List} of {@link CVendorTaxDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CVendorTaxDTO> findByCriteria(CVendorTaxCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CVendorTax> specification = createSpecification(criteria);
        return cVendorTaxMapper.toDto(cVendorTaxRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CVendorTaxDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorTaxDTO> findByCriteria(CVendorTaxCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CVendorTax> specification = createSpecification(criteria);
        return cVendorTaxRepository.findAll(specification, page)
            .map(cVendorTaxMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CVendorTaxCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CVendorTax> specification = createSpecification(criteria);
        return cVendorTaxRepository.count(specification);
    }

    /**
     * Function to convert {@link CVendorTaxCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CVendorTax> createSpecification(CVendorTaxCriteria criteria) {
        Specification<CVendorTax> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CVendorTax_.id));
            }
            if (criteria.getIsFaktur() != null) {
                specification = specification.and(buildSpecification(criteria.getIsFaktur(), CVendorTax_.isFaktur));
            }
            if (criteria.getIsPkp() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPkp(), CVendorTax_.isPkp));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRate(), CVendorTax_.rate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CVendorTax_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CVendorTax_.active));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(CVendorTax_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getTaxCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxCategoryId(),
                    root -> root.join(CVendorTax_.taxCategory, JoinType.LEFT).get(CTaxCategory_.id)));
            }
            if (criteria.getTaxRateId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxRateId(),
                    root -> root.join(CVendorTax_.taxRate, JoinType.LEFT).get(CTaxRate_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CVendorTax_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
