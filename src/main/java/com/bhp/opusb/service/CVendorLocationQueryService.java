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

import com.bhp.opusb.domain.CVendorLocation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CVendorLocationRepository;
import com.bhp.opusb.service.dto.CVendorLocationCriteria;
import com.bhp.opusb.service.dto.CVendorLocationDTO;
import com.bhp.opusb.service.mapper.CVendorLocationMapper;

/**
 * Service for executing complex queries for {@link CVendorLocation} entities in the database.
 * The main input is a {@link CVendorLocationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CVendorLocationDTO} or a {@link Page} of {@link CVendorLocationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CVendorLocationQueryService extends QueryService<CVendorLocation> {

    private final Logger log = LoggerFactory.getLogger(CVendorLocationQueryService.class);

    private final CVendorLocationRepository cVendorLocationRepository;

    private final CVendorLocationMapper cVendorLocationMapper;

    public CVendorLocationQueryService(CVendorLocationRepository cVendorLocationRepository, CVendorLocationMapper cVendorLocationMapper) {
        this.cVendorLocationRepository = cVendorLocationRepository;
        this.cVendorLocationMapper = cVendorLocationMapper;
    }

    /**
     * Return a {@link List} of {@link CVendorLocationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CVendorLocationDTO> findByCriteria(CVendorLocationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CVendorLocation> specification = createSpecification(criteria);
        return cVendorLocationMapper.toDto(cVendorLocationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CVendorLocationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorLocationDTO> findByCriteria(CVendorLocationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CVendorLocation> specification = createSpecification(criteria);
        return cVendorLocationRepository.findAll(specification, page)
            .map(cVendorLocationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CVendorLocationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CVendorLocation> specification = createSpecification(criteria);
        return cVendorLocationRepository.count(specification);
    }

    /**
     * Function to convert {@link CVendorLocationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CVendorLocation> createSpecification(CVendorLocationCriteria criteria) {
        Specification<CVendorLocation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CVendorLocation_.id));
            }
            if (criteria.getTaxInvoiceAddress() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxInvoiceAddress(), CVendorLocation_.taxInvoiceAddress));
            }
            if (criteria.getShipAddress() != null) {
                specification = specification.and(buildSpecification(criteria.getShipAddress(), CVendorLocation_.shipAddress));
            }
            if (criteria.getInvoiceAddress() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceAddress(), CVendorLocation_.invoiceAddress));
            }
            if (criteria.getPayFromAddress() != null) {
                specification = specification.and(buildSpecification(criteria.getPayFromAddress(), CVendorLocation_.payFromAddress));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CVendorLocation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CVendorLocation_.active));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(CVendorLocation_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getVendorName() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorName(),
                    root -> root.join(CVendorLocation_.vendor, JoinType.LEFT).get(CVendor_.name)));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(CVendorLocation_.location, JoinType.LEFT).get(CLocation_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CVendorLocation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
