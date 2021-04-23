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

import com.bhp.opusb.domain.MBiddingInvitation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingInvitationRepository;
import com.bhp.opusb.service.dto.MBiddingInvitationCriteria;
import com.bhp.opusb.service.dto.MBiddingInvitationDTO;
import com.bhp.opusb.service.mapper.MBiddingInvitationMapper;

/**
 * Service for executing complex queries for {@link MBiddingInvitation} entities in the database.
 * The main input is a {@link MBiddingInvitationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingInvitationDTO} or a {@link Page} of {@link MBiddingInvitationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingInvitationQueryService extends QueryService<MBiddingInvitation> {

    private final Logger log = LoggerFactory.getLogger(MBiddingInvitationQueryService.class);

    private final MBiddingInvitationRepository mBiddingInvitationRepository;

    private final MBiddingInvitationMapper mBiddingInvitationMapper;

    public MBiddingInvitationQueryService(MBiddingInvitationRepository mBiddingInvitationRepository, MBiddingInvitationMapper mBiddingInvitationMapper) {
        this.mBiddingInvitationRepository = mBiddingInvitationRepository;
        this.mBiddingInvitationMapper = mBiddingInvitationMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingInvitationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingInvitationDTO> findByCriteria(MBiddingInvitationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingInvitation> specification = createSpecification(criteria);
        return mBiddingInvitationMapper.toDto(mBiddingInvitationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingInvitationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingInvitationDTO> findByCriteria(MBiddingInvitationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingInvitation> specification = createSpecification(criteria);
        return mBiddingInvitationRepository.findAll(specification, page)
            .map(mBiddingInvitationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingInvitationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingInvitation> specification = createSpecification(criteria);
        return mBiddingInvitationRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingInvitationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingInvitation> createSpecification(MBiddingInvitationCriteria criteria) {
        Specification<MBiddingInvitation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingInvitation_.id));
            }
            if (criteria.getInvitationStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvitationStatus(), MBiddingInvitation_.invitationStatus));
            }
            if (criteria.getReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReason(), MBiddingInvitation_.reason));
            }
            if (criteria.getAnswerDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnswerDate(), MBiddingInvitation_.answerDate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingInvitation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingInvitation_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingInvitation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAnnouncementId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnnouncementId(),
                    root -> root.join(MBiddingInvitation_.announcement, JoinType.LEFT).get(CAnnouncement_.id)));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MBiddingInvitation_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MBiddingInvitation_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
