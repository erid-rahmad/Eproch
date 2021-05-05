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

import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingSubmissionRepository;
import com.bhp.opusb.service.dto.MBiddingSubmissionCriteria;
import com.bhp.opusb.service.dto.MBiddingSubmissionDTO;
import com.bhp.opusb.service.mapper.MBiddingSubmissionMapper;

/**
 * Service for executing complex queries for {@link MBiddingSubmission} entities in the database.
 * The main input is a {@link MBiddingSubmissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingSubmissionDTO} or a {@link Page} of {@link MBiddingSubmissionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingSubmissionQueryService extends QueryService<MBiddingSubmission> {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubmissionQueryService.class);

    private final MBiddingSubmissionRepository mBiddingSubmissionRepository;

    private final MBiddingSubmissionMapper mBiddingSubmissionMapper;

    public MBiddingSubmissionQueryService(MBiddingSubmissionRepository mBiddingSubmissionRepository, MBiddingSubmissionMapper mBiddingSubmissionMapper) {
        this.mBiddingSubmissionRepository = mBiddingSubmissionRepository;
        this.mBiddingSubmissionMapper = mBiddingSubmissionMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingSubmissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingSubmissionDTO> findByCriteria(MBiddingSubmissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingSubmission> specification = createSpecification(criteria);
        return mBiddingSubmissionMapper.toDto(mBiddingSubmissionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingSubmissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingSubmissionDTO> findByCriteria(MBiddingSubmissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingSubmission> specification = createSpecification(criteria);
        return mBiddingSubmissionRepository.findAll(specification, page)
            .map(mBiddingSubmissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingSubmissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingSubmission> specification = createSpecification(criteria);
        return mBiddingSubmissionRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingSubmissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingSubmission> createSpecification(MBiddingSubmissionCriteria criteria) {
        Specification<MBiddingSubmission> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingSubmission_.id));
            }
            if (criteria.getJoined() != null) {
                specification = specification.and(buildSpecification(criteria.getJoined(), MBiddingSubmission_.joined));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MBiddingSubmission_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MBiddingSubmission_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MBiddingSubmission_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MBiddingSubmission_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MBiddingSubmission_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MBiddingSubmission_.processed));
            }
            if (criteria.getDateApprove() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateApprove(), MBiddingSubmission_.dateApprove));
            }
            if (criteria.getDateReject() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReject(), MBiddingSubmission_.dateReject));
            }
            if (criteria.getRejectedReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectedReason(), MBiddingSubmission_.rejectedReason));
            }
            if (criteria.getDateSubmit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateSubmit(), MBiddingSubmission_.dateSubmit));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingSubmission_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingSubmission_.active));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MBiddingSubmission_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getBiddingScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingScheduleId(),
                    root -> root.join(MBiddingSubmission_.biddingSchedule, JoinType.LEFT).get(MBiddingSchedule_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MBiddingSubmission_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingSubmission_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
