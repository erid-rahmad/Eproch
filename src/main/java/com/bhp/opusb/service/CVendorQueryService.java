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

import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CVendorRepository;
import com.bhp.opusb.service.dto.CVendorCriteria;
import com.bhp.opusb.service.dto.CVendorDTO;
import com.bhp.opusb.service.mapper.CVendorMapper;

/**
 * Service for executing complex queries for {@link CVendor} entities in the database.
 * The main input is a {@link CVendorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CVendorDTO} or a {@link Page} of {@link CVendorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CVendorQueryService extends QueryService<CVendor> {

    private final Logger log = LoggerFactory.getLogger(CVendorQueryService.class);

    private final CVendorRepository cVendorRepository;

    private final CVendorMapper cVendorMapper;

    public CVendorQueryService(CVendorRepository cVendorRepository, CVendorMapper cVendorMapper) {
        this.cVendorRepository = cVendorRepository;
        this.cVendorMapper = cVendorMapper;
    }

    /**
     * Return a {@link List} of {@link CVendorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CVendorDTO> findByCriteria(CVendorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CVendor> specification = createSpecification(criteria);
        return cVendorMapper.toDto(cVendorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CVendorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorDTO> findByCriteria(CVendorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CVendor> specification = createSpecification(criteria);
        return cVendorRepository.findAll(specification, page)
            .map(cVendorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CVendorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CVendor> specification = createSpecification(criteria);
        return cVendorRepository.count(specification);
    }

    /**
     * Function to convert {@link CVendorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CVendor> createSpecification(CVendorCriteria criteria) {
        Specification<CVendor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CVendor_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CVendor_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CVendor_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), CVendor_.type));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), CVendor_.location));
            }
            if (criteria.getIdNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdNo(), CVendor_.idNo));
            }
            if (criteria.getTin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTin(), CVendor_.tin));
            }
            if (criteria.getTaxIdNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxIdNo(), CVendor_.taxIdNo));
            }
            if (criteria.getTaxIdName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxIdName(), CVendor_.taxIdName));
            }
            if (criteria.getBranch() != null) {
                specification = specification.and(buildSpecification(criteria.getBranch(), CVendor_.branch));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), CVendor_.email));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), CVendor_.phone));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), CVendor_.fax));
            }
            if (criteria.getWebsite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsite(), CVendor_.website));
            }
            if (criteria.getPaymentCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentCategory(), CVendor_.paymentCategory));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), CVendor_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), CVendor_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), CVendor_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), CVendor_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), CVendor_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), CVendor_.processed));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CVendor_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CVendor_.active));
            }
            if (criteria.getTaxIdFileId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxIdFileId(),
                    root -> root.join(CVendor_.taxIdFile, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CVendor_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeId(),
                    root -> root.join(CVendor_.documentType, JoinType.LEFT).get(CDocumentType_.id)));
            }
            if (criteria.getVendorGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorGroupId(),
                    root -> root.join(CVendor_.vendorGroup, JoinType.LEFT).get(CVendorGroup_.id)));
            }
        }
        return specification;
    }
}
