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

import com.bhp.opusb.domain.CTaxInvoice;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CTaxInvoiceRepository;
import com.bhp.opusb.service.dto.CTaxInvoiceCriteria;
import com.bhp.opusb.service.dto.CTaxInvoiceDTO;
import com.bhp.opusb.service.mapper.CTaxInvoiceMapper;

/**
 * Service for executing complex queries for {@link CTaxInvoice} entities in the database.
 * The main input is a {@link CTaxInvoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CTaxInvoiceDTO} or a {@link Page} of {@link CTaxInvoiceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CTaxInvoiceQueryService extends QueryService<CTaxInvoice> {

    private final Logger log = LoggerFactory.getLogger(CTaxInvoiceQueryService.class);

    private final CTaxInvoiceRepository cTaxInvoiceRepository;

    private final CTaxInvoiceMapper cTaxInvoiceMapper;

    public CTaxInvoiceQueryService(CTaxInvoiceRepository cTaxInvoiceRepository, CTaxInvoiceMapper cTaxInvoiceMapper) {
        this.cTaxInvoiceRepository = cTaxInvoiceRepository;
        this.cTaxInvoiceMapper = cTaxInvoiceMapper;
    }

    /**
     * Return a {@link List} of {@link CTaxInvoiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CTaxInvoiceDTO> findByCriteria(CTaxInvoiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CTaxInvoice> specification = createSpecification(criteria);
        return cTaxInvoiceMapper.toDto(cTaxInvoiceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CTaxInvoiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CTaxInvoiceDTO> findByCriteria(CTaxInvoiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CTaxInvoice> specification = createSpecification(criteria);
        return cTaxInvoiceRepository.findAll(specification, page)
            .map(cTaxInvoiceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CTaxInvoiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CTaxInvoice> specification = createSpecification(criteria);
        return cTaxInvoiceRepository.count(specification);
    }

    /**
     * Function to convert {@link CTaxInvoiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CTaxInvoice> createSpecification(CTaxInvoiceCriteria criteria) {
        Specification<CTaxInvoice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CTaxInvoice_.id));
            }
            if (criteria.getStartNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartNo(), CTaxInvoice_.startNo));
            }
            if (criteria.getEndNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndNo(), CTaxInvoice_.endNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CTaxInvoice_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CTaxInvoice_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CTaxInvoice_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(CTaxInvoice_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
