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

import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingRepository;
import com.bhp.opusb.service.dto.MBiddingCriteria;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.mapper.MBiddingMapper;

/**
 * Service for executing complex queries for {@link MBidding} entities in the database.
 * The main input is a {@link MBiddingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingDTO} or a {@link Page} of {@link MBiddingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingQueryService extends QueryService<MBidding> {

    private final Logger log = LoggerFactory.getLogger(MBiddingQueryService.class);

    private final MBiddingRepository mBiddingRepository;

    private final MBiddingMapper mBiddingMapper;

    public MBiddingQueryService(MBiddingRepository mBiddingRepository, MBiddingMapper mBiddingMapper) {
        this.mBiddingRepository = mBiddingRepository;
        this.mBiddingMapper = mBiddingMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingDTO> findByCriteria(MBiddingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBidding> specification = createSpecification(criteria);
        return mBiddingMapper.toDto(mBiddingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingDTO> findByCriteria(MBiddingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBidding> specification = createSpecification(criteria);
        return mBiddingRepository.findAll(specification, page)
            .map(mBiddingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBidding> specification = createSpecification(criteria);
        return mBiddingRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBidding> createSpecification(MBiddingCriteria criteria) {
        Specification<MBidding> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBidding_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MBidding_.name));
            }
            if (criteria.getVendorSelection() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorSelection(), MBidding_.vendorSelection));
            }
            if (criteria.getCeilingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCeilingPrice(), MBidding_.ceilingPrice));
            }
            if (criteria.getEstimatedPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimatedPrice(), MBidding_.estimatedPrice));
            }
            if (criteria.getBiddingStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBiddingStatus(), MBidding_.biddingStatus));
            }
            if (criteria.getJoinedVendorCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJoinedVendorCount(), MBidding_.joinedVendorCount));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MBidding_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MBidding_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MBidding_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MBidding_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MBidding_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MBidding_.processed));
            }
            if (criteria.getDateApprove() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateApprove(), MBidding_.dateApprove));
            }
            if (criteria.getDateReject() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReject(), MBidding_.dateReject));
            }
            if (criteria.getRejectedReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectedReason(), MBidding_.rejectedReason));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBidding_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBidding_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBidding_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MBidding_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(MBidding_.currency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeId(),
                    root -> root.join(MBidding_.documentType, JoinType.LEFT).get(CDocumentType_.id)));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(MBidding_.requisition, JoinType.LEFT).get(MRequisition_.id)));
            }
            if (criteria.getReferenceTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getReferenceTypeId(),
                    root -> root.join(MBidding_.referenceType, JoinType.LEFT).get(CDocumentType_.id)));
            }
            if (criteria.getBiddingTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingTypeId(),
                    root -> root.join(MBidding_.biddingType, JoinType.LEFT).get(CBiddingType_.id)));
            }
            if (criteria.getEventTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEventTypeId(),
                    root -> root.join(MBidding_.eventType, JoinType.LEFT).get(CEventType_.id)));
            }
            if (criteria.getAdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdUserId(),
                    root -> root.join(MBidding_.adUser, JoinType.LEFT).get(AdUser_.id)));
            }
        }
        return specification;
    }
}
