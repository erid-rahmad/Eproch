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

import com.bhp.opusb.domain.MRfqSubmission;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MRfqSubmissionRepository;
import com.bhp.opusb.service.dto.MRfqSubmissionCriteria;
import com.bhp.opusb.service.dto.MRfqSubmissionDTO;
import com.bhp.opusb.service.mapper.MRfqSubmissionMapper;

/**
 * Service for executing complex queries for {@link MRfqSubmission} entities in the database.
 * The main input is a {@link MRfqSubmissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MRfqSubmissionDTO} or a {@link Page} of {@link MRfqSubmissionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MRfqSubmissionQueryService extends QueryService<MRfqSubmission> {

    private final Logger log = LoggerFactory.getLogger(MRfqSubmissionQueryService.class);

    private final MRfqSubmissionRepository mRfqSubmissionRepository;

    private final MRfqSubmissionMapper mRfqSubmissionMapper;

    public MRfqSubmissionQueryService(MRfqSubmissionRepository mRfqSubmissionRepository, MRfqSubmissionMapper mRfqSubmissionMapper) {
        this.mRfqSubmissionRepository = mRfqSubmissionRepository;
        this.mRfqSubmissionMapper = mRfqSubmissionMapper;
    }

    /**
     * Return a {@link List} of {@link MRfqSubmissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MRfqSubmissionDTO> findByCriteria(MRfqSubmissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MRfqSubmission> specification = createSpecification(criteria);
        return mRfqSubmissionMapper.toDto(mRfqSubmissionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MRfqSubmissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MRfqSubmissionDTO> findByCriteria(MRfqSubmissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MRfqSubmission> specification = createSpecification(criteria);
        return mRfqSubmissionRepository.findAll(specification, page)
            .map(mRfqSubmissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MRfqSubmissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MRfqSubmission> specification = createSpecification(criteria);
        return mRfqSubmissionRepository.count(specification);
    }

    /**
     * Function to convert {@link MRfqSubmissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MRfqSubmission> createSpecification(MRfqSubmissionCriteria criteria) {
        Specification<MRfqSubmission> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MRfqSubmission_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MRfqSubmission_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MRfqSubmission_.active));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MRfqSubmission_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MRfqSubmission_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MRfqSubmission_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MRfqSubmission_.processed));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MRfqSubmission_.dateTrx));
            }
            if (criteria.getDateRequired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRequired(), MRfqSubmission_.dateRequired));
            }
            if (criteria.getDateSubmitted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateSubmitted(), MRfqSubmission_.dateSubmitted));
            }
            if (criteria.getSubmissionGrandTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmissionGrandTotal(), MRfqSubmission_.submissionGrandTotal));
            }
            if (criteria.getQuotationId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuotationId(),
                    root -> root.join(MRfqSubmission_.quotation, JoinType.LEFT).get(MRfq_.id)));
            }
            if (criteria.getQuoteSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuoteSupplierId(),
                    root -> root.join(MRfqSubmission_.quoteSupplier, JoinType.LEFT).get(MQuoteSupplier_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MRfqSubmission_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBusinessClassificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessClassificationId(),
                    root -> root.join(MRfqSubmission_.businessClassification, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(MRfqSubmission_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(MRfqSubmission_.currency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getWarehouseId() != null) {
                specification = specification.and(buildSpecification(criteria.getWarehouseId(),
                    root -> root.join(MRfqSubmission_.warehouse, JoinType.LEFT).get(CWarehouse_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MRfqSubmission_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
        }
        return specification;
    }
}
