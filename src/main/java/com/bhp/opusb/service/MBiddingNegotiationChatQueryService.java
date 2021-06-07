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

import com.bhp.opusb.domain.MBiddingNegotiationChat;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingNegotiationChatRepository;
import com.bhp.opusb.service.dto.MBiddingNegotiationChatCriteria;
import com.bhp.opusb.service.dto.MBiddingNegotiationChatDTO;
import com.bhp.opusb.service.mapper.MBiddingNegotiationChatMapper;

/**
 * Service for executing complex queries for {@link MBiddingNegotiationChat} entities in the database.
 * The main input is a {@link MBiddingNegotiationChatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingNegotiationChatDTO} or a {@link Page} of {@link MBiddingNegotiationChatDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingNegotiationChatQueryService extends QueryService<MBiddingNegotiationChat> {

    private final Logger log = LoggerFactory.getLogger(MBiddingNegotiationChatQueryService.class);

    private final MBiddingNegotiationChatRepository mBiddingNegotiationChatRepository;

    private final MBiddingNegotiationChatMapper mBiddingNegotiationChatMapper;

    public MBiddingNegotiationChatQueryService(MBiddingNegotiationChatRepository mBiddingNegotiationChatRepository, MBiddingNegotiationChatMapper mBiddingNegotiationChatMapper) {
        this.mBiddingNegotiationChatRepository = mBiddingNegotiationChatRepository;
        this.mBiddingNegotiationChatMapper = mBiddingNegotiationChatMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingNegotiationChatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingNegotiationChatDTO> findByCriteria(MBiddingNegotiationChatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingNegotiationChat> specification = createSpecification(criteria);
        return mBiddingNegotiationChatMapper.toDto(mBiddingNegotiationChatRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingNegotiationChatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingNegotiationChatDTO> findByCriteria(MBiddingNegotiationChatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingNegotiationChat> specification = createSpecification(criteria);
        return mBiddingNegotiationChatRepository.findAll(specification, page)
            .map(mBiddingNegotiationChatMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingNegotiationChatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingNegotiationChat> specification = createSpecification(criteria);
        return mBiddingNegotiationChatRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingNegotiationChatCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingNegotiationChat> createSpecification(MBiddingNegotiationChatCriteria criteria) {
        Specification<MBiddingNegotiationChat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingNegotiationChat_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingNegotiationChat_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingNegotiationChat_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingNegotiationChat_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getNegotiationLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getNegotiationLineId(),
                    root -> root.join(MBiddingNegotiationChat_.negotiationLine, JoinType.LEFT).get(MBiddingNegotiationLine_.id)));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MBiddingNegotiationChat_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MBiddingNegotiationChat_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentId(),
                    root -> root.join(MBiddingNegotiationChat_.attachment, JoinType.LEFT).get(CAttachment_.id)));
            }
        }
        return specification;
    }
}
