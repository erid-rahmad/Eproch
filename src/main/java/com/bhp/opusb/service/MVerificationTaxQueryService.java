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

import com.bhp.opusb.domain.MVerificationTax;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVerificationTaxRepository;
import com.bhp.opusb.service.dto.MVerificationTaxCriteria;
import com.bhp.opusb.service.dto.MVerificationTaxDTO;
import com.bhp.opusb.service.mapper.MVerificationTaxMapper;

/**
 * Service for executing complex queries for {@link MVerificationTax} entities in the database.
 * The main input is a {@link MVerificationTaxCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVerificationTaxDTO} or a {@link Page} of {@link MVerificationTaxDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVerificationTaxQueryService extends QueryService<MVerificationTax> {

    private final Logger log = LoggerFactory.getLogger(MVerificationTaxQueryService.class);

    private final MVerificationTaxRepository mVerificationTaxRepository;

    private final MVerificationTaxMapper mVerificationTaxMapper;

    public MVerificationTaxQueryService(MVerificationTaxRepository mVerificationTaxRepository, MVerificationTaxMapper mVerificationTaxMapper) {
        this.mVerificationTaxRepository = mVerificationTaxRepository;
        this.mVerificationTaxMapper = mVerificationTaxMapper;
    }

    /**
     * Return a {@link List} of {@link MVerificationTaxDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVerificationTaxDTO> findByCriteria(MVerificationTaxCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVerificationTax> specification = createSpecification(criteria);
        return mVerificationTaxMapper.toDto(mVerificationTaxRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVerificationTaxDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVerificationTaxDTO> findByCriteria(MVerificationTaxCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVerificationTax> specification = createSpecification(criteria);
        return mVerificationTaxRepository.findAll(specification, page)
            .map(mVerificationTaxMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVerificationTaxCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVerificationTax> specification = createSpecification(criteria);
        return mVerificationTaxRepository.count(specification);
    }

    /**
     * Function to convert {@link MVerificationTaxCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVerificationTax> createSpecification(MVerificationTaxCriteria criteria) {
        Specification<MVerificationTax> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVerificationTax_.id));
            }
            if (criteria.getTaxPeriod() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxPeriod(), MVerificationTax_.taxPeriod));
            }
            if (criteria.getTraxCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTraxCode(), MVerificationTax_.traxCode));
            }
            if (criteria.getStatusCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusCode(), MVerificationTax_.statusCode));
            }
            if (criteria.getDocCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocCode(), MVerificationTax_.docCode));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), MVerificationTax_.year));
            }
            if (criteria.getReturnDocType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReturnDocType(), MVerificationTax_.returnDocType));
            }
            if (criteria.getRepSerNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRepSerNo(), MVerificationTax_.repSerNo));
            }
            if (criteria.getTaxExpCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxExpCode(), MVerificationTax_.taxExpCode));
            }
            if (criteria.getDateSSP() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateSSP(), MVerificationTax_.dateSSP));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVerificationTax_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVerificationTax_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVerificationTax_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(MVerificationTax_.currency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getTaxCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxCategoryId(),
                    root -> root.join(MVerificationTax_.taxCategory, JoinType.LEFT).get(CTaxCategory_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MVerificationTax_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
        }
        return specification;
    }
}
