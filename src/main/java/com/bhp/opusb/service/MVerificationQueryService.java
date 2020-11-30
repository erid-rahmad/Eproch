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

import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVerificationRepository;
import com.bhp.opusb.service.dto.MVerificationCriteria;
import com.bhp.opusb.service.dto.MVerificationDTO;
import com.bhp.opusb.service.mapper.MVerificationMapper;

/**
 * Service for executing complex queries for {@link MVerification} entities in the database.
 * The main input is a {@link MVerificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVerificationDTO} or a {@link Page} of {@link MVerificationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVerificationQueryService extends QueryService<MVerification> {

    private final Logger log = LoggerFactory.getLogger(MVerificationQueryService.class);

    private final MVerificationRepository mVerificationRepository;

    private final MVerificationMapper mVerificationMapper;

    public MVerificationQueryService(MVerificationRepository mVerificationRepository, MVerificationMapper mVerificationMapper) {
        this.mVerificationRepository = mVerificationRepository;
        this.mVerificationMapper = mVerificationMapper;
    }

    /**
     * Return a {@link List} of {@link MVerificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVerificationDTO> findByCriteria(MVerificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVerification> specification = createSpecification(criteria);
        return mVerificationMapper.toDto(mVerificationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVerificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVerificationDTO> findByCriteria(MVerificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVerification> specification = createSpecification(criteria);
        return mVerificationRepository.findAll(specification, page)
            .map(mVerificationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVerificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVerification> specification = createSpecification(criteria);
        return mVerificationRepository.count(specification);
    }

    /**
     * Function to convert {@link MVerificationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVerification> createSpecification(MVerificationCriteria criteria) {
        Specification<MVerification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVerification_.id));
            }
            if (criteria.getVerificationNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVerificationNo(), MVerification_.verificationNo));
            }
            if (criteria.getVerificationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVerificationDate(), MVerification_.verificationDate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MVerification_.description));
            }
            if (criteria.getReceiptNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceiptNo(), MVerification_.receiptNo));
            }
            if (criteria.getInvoiceNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceNo(), MVerification_.invoiceNo));
            }
            if (criteria.getInvoiceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceDate(), MVerification_.invoiceDate));
            }
            if (criteria.getTaxInvoice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxInvoice(), MVerification_.taxInvoice));
            }
            if (criteria.getTaxDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxDate(), MVerification_.taxDate));
            }
            if (criteria.getTotalLines() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalLines(), MVerification_.totalLines));
            }
            if (criteria.getGrandTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrandTotal(), MVerification_.grandTotal));
            }
            if (criteria.getForeignGrandTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getForeignGrandTotal(), MVerification_.foreignGrandTotal));
            }
            if (criteria.getTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxAmount(), MVerification_.taxAmount));
            }
            if (criteria.getForeignTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getForeignTaxAmount(), MVerification_.foreignTaxAmount));
            }
            if (criteria.getDateSubmit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateSubmit(), MVerification_.dateSubmit));
            }
            if (criteria.getDateAcct() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateAcct(), MVerification_.dateAcct));
            }
            if (criteria.getWithholdingAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWithholdingAmt(), MVerification_.withholdingAmt));
            }
            if (criteria.getInvoiceAp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceAp(), MVerification_.invoiceAp));
            }
            if (criteria.getDocType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocType(), MVerification_.docType));
            }
            if (criteria.getPayDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPayDate(), MVerification_.payDate));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), MVerification_.dueDate));
            }
            if (criteria.getPayAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPayAmt(), MVerification_.payAmt));
            }
            if (criteria.getDateReject() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReject(), MVerification_.dateReject));
            }
            if (criteria.getDateApprove() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateApprove(), MVerification_.dateApprove));
            }
            if (criteria.getVerificationStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVerificationStatus(), MVerification_.verificationStatus));
            }
            if (criteria.getPayStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPayStatus(), MVerification_.payStatus));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVerification_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVerification_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVerification_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(MVerification_.currency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MVerification_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getPicId() != null) {
                specification = specification.and(buildSpecification(criteria.getPicId(),
                    root -> root.join(MVerification_.pic, JoinType.LEFT).get(AdUser_.id)));
            }
            if (criteria.getVendorToId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorToId(),
                    root -> root.join(MVerification_.vendorTo, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
