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

import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MContractRepository;
import com.bhp.opusb.service.dto.MContractCriteria;
import com.bhp.opusb.service.dto.MContractDTO;
import com.bhp.opusb.service.mapper.MContractMapper;

/**
 * Service for executing complex queries for {@link MContract} entities in the database.
 * The main input is a {@link MContractCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MContractDTO} or a {@link Page} of {@link MContractDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MContractQueryService extends QueryService<MContract> {

    private final Logger log = LoggerFactory.getLogger(MContractQueryService.class);

    private final MContractRepository mContractRepository;

    private final MContractMapper mContractMapper;

    public MContractQueryService(MContractRepository mContractRepository, MContractMapper mContractMapper) {
        this.mContractRepository = mContractRepository;
        this.mContractMapper = mContractMapper;
    }

    /**
     * Return a {@link List} of {@link MContractDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MContractDTO> findByCriteria(MContractCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MContract> specification = createSpecification(criteria);
        return mContractMapper.toDto(mContractRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MContractDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractDTO> findByCriteria(MContractCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MContract> specification = createSpecification(criteria);
        return mContractRepository.findAll(specification, page)
            .map(mContractMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MContractCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MContract> specification = createSpecification(criteria);
        return mContractRepository.count(specification);
    }

    /**
     * Function to convert {@link MContractCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MContract> createSpecification(MContractCriteria criteria) {
        Specification<MContract> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MContract_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MContract_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MContract_.description));
            }
            if (criteria.getUseBanCode() != null) {
                specification = specification.and(buildSpecification(criteria.getUseBanCode(), MContract_.useBanCode));
            }
            if (criteria.getBanCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBanCode(), MContract_.banCode));
            }
            if (criteria.getPurpose() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurpose(), MContract_.purpose));
            }
            if (criteria.getForPriceConfirmation() != null) {
                specification = specification.and(buildSpecification(criteria.getForPriceConfirmation(), MContract_.forPriceConfirmation));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), MContract_.startDate));
            }
            if (criteria.getExpirationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpirationDate(), MContract_.expirationDate));
            }
            if (criteria.getEvaluationPeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluationPeriod(), MContract_.evaluationPeriod));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MContract_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MContract_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MContract_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MContract_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MContract_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MContract_.processed));
            }
            if (criteria.getDateApprove() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateApprove(), MContract_.dateApprove));
            }
            if (criteria.getDateReject() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReject(), MContract_.dateReject));
            }
            if (criteria.getRejectedReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectedReason(), MContract_.rejectedReason));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), MContract_.price));
            }
            if (criteria.getPriceProposed() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceProposed(), MContract_.priceProposed));
            }
            if (criteria.getExpMailReceipt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpMailReceipt(), MContract_.expMailReceipt));
            }
            if (criteria.getNoticePeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoticePeriod(), MContract_.noticePeriod));
            }
            if (criteria.getReminderSent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReminderSent(), MContract_.reminderSent));
            }
            if (criteria.getEmailNotification() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmailNotification(), MContract_.emailNotification));
            }
            if (criteria.getTermType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTermType(), MContract_.termType));
            }
            if (criteria.getHierarchicalType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHierarchicalType(), MContract_.hierarchicalType));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MContract_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MContract_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MContract_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MContract_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MContract_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(MContract_.currency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeId(),
                    root -> root.join(MContract_.documentType, JoinType.LEFT).get(CDocumentType_.id)));
            }
            if (criteria.getPicId() != null) {
                specification = specification.and(buildSpecification(criteria.getPicId(),
                    root -> root.join(MContract_.pic, JoinType.LEFT).get(AdUser_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MContract_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getVendorEvaluationId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorEvaluationId(),
                    root -> root.join(MContract_.vendorEvaluation, JoinType.LEFT).get(CVendorEvaluation_.id)));
            }
        }
        return specification;
    }
}
