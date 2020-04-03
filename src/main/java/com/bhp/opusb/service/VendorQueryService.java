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

import com.bhp.opusb.domain.Vendor;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.VendorRepository;
import com.bhp.opusb.service.dto.VendorCriteria;
import com.bhp.opusb.service.dto.VendorDTO;
import com.bhp.opusb.service.mapper.VendorMapper;

/**
 * Service for executing complex queries for {@link Vendor} entities in the database.
 * The main input is a {@link VendorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VendorDTO} or a {@link Page} of {@link VendorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VendorQueryService extends QueryService<Vendor> {

    private final Logger log = LoggerFactory.getLogger(VendorQueryService.class);

    private final VendorRepository vendorRepository;

    private final VendorMapper vendorMapper;

    public VendorQueryService(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    /**
     * Return a {@link List} of {@link VendorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VendorDTO> findByCriteria(VendorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Vendor> specification = createSpecification(criteria);
        return vendorMapper.toDto(vendorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VendorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VendorDTO> findByCriteria(VendorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vendor> specification = createSpecification(criteria);
        return vendorRepository.findAll(specification, page)
            .map(vendorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VendorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vendor> specification = createSpecification(criteria);
        return vendorRepository.count(specification);
    }

    /**
     * Function to convert {@link VendorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Vendor> createSpecification(VendorCriteria criteria) {
        Specification<Vendor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Vendor_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Vendor_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Vendor_.name));
            }
            if (criteria.getNpwp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNpwp(), Vendor_.npwp));
            }
            if (criteria.getBranch() != null) {
                specification = specification.and(buildSpecification(criteria.getBranch(), Vendor_.branch));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Vendor_.email));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Vendor_.phone));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), Vendor_.fax));
            }
            if (criteria.getWebsite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsite(), Vendor_.website));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Vendor_.type));
            }
            if (criteria.getPaymentCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentCategory(), Vendor_.paymentCategory));
            }
            if (criteria.getApprovalStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getApprovalStatus(), Vendor_.approvalStatus));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(Vendor_.location, JoinType.LEFT).get(Location_.id)));
            }
            if (criteria.getCompanyFunctionaryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyFunctionaryId(),
                    root -> root.join(Vendor_.companyFunctionaries, JoinType.LEFT).get(CompanyFunctionary_.id)));
            }
            if (criteria.getPersonInChargeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonInChargeId(),
                    root -> root.join(Vendor_.personInCharges, JoinType.LEFT).get(PersonInCharge_.id)));
            }
            if (criteria.getSupportingDocumentId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupportingDocumentId(),
                    root -> root.join(Vendor_.supportingDocuments, JoinType.LEFT).get(SupportingDocument_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(Vendor_.businessCategories, JoinType.LEFT).get(BusinessCategory_.id)));
            }
        }
        return specification;
    }
}
