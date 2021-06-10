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

import com.bhp.opusb.domain.MAuctionInvitation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MAuctionInvitationRepository;
import com.bhp.opusb.service.dto.MAuctionInvitationCriteria;
import com.bhp.opusb.service.dto.MAuctionInvitationDTO;
import com.bhp.opusb.service.mapper.MAuctionInvitationMapper;

/**
 * Service for executing complex queries for {@link MAuctionInvitation} entities in the database.
 * The main input is a {@link MAuctionInvitationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MAuctionInvitationDTO} or a {@link Page} of {@link MAuctionInvitationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MAuctionInvitationQueryService extends QueryService<MAuctionInvitation> {

    private final Logger log = LoggerFactory.getLogger(MAuctionInvitationQueryService.class);

    private final MAuctionInvitationRepository mAuctionInvitationRepository;

    private final MAuctionInvitationMapper mAuctionInvitationMapper;

    public MAuctionInvitationQueryService(MAuctionInvitationRepository mAuctionInvitationRepository, MAuctionInvitationMapper mAuctionInvitationMapper) {
        this.mAuctionInvitationRepository = mAuctionInvitationRepository;
        this.mAuctionInvitationMapper = mAuctionInvitationMapper;
    }

    /**
     * Return a {@link List} of {@link MAuctionInvitationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MAuctionInvitationDTO> findByCriteria(MAuctionInvitationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MAuctionInvitation> specification = createSpecification(criteria);
        return mAuctionInvitationMapper.toDto(mAuctionInvitationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MAuctionInvitationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionInvitationDTO> findByCriteria(MAuctionInvitationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MAuctionInvitation> specification = createSpecification(criteria);
        return mAuctionInvitationRepository.findAll(specification, page)
            .map(mAuctionInvitationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MAuctionInvitationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MAuctionInvitation> specification = createSpecification(criteria);
        return mAuctionInvitationRepository.count(specification);
    }

    /**
     * Function to convert {@link MAuctionInvitationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MAuctionInvitation> createSpecification(MAuctionInvitationCriteria criteria) {
        Specification<MAuctionInvitation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MAuctionInvitation_.id));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MAuctionInvitation_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MAuctionInvitation_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MAuctionInvitation_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MAuctionInvitation_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MAuctionInvitation_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MAuctionInvitation_.processed));
            }
            if (criteria.getDateApprove() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateApprove(), MAuctionInvitation_.dateApprove));
            }
            if (criteria.getDateReject() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReject(), MAuctionInvitation_.dateReject));
            }
            if (criteria.getRejectedReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectedReason(), MAuctionInvitation_.rejectedReason));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MAuctionInvitation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MAuctionInvitation_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MAuctionInvitation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAuctionId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionId(),
                    root -> root.join(MAuctionInvitation_.auction, JoinType.LEFT).get(MAuction_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MAuctionInvitation_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
