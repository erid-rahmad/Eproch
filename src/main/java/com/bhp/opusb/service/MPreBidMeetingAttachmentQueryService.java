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

import com.bhp.opusb.domain.MPreBidMeetingAttachment;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPreBidMeetingAttachmentRepository;
import com.bhp.opusb.service.dto.MPreBidMeetingAttachmentCriteria;
import com.bhp.opusb.service.dto.MPreBidMeetingAttachmentDTO;
import com.bhp.opusb.service.mapper.MPreBidMeetingAttachmentMapper;

/**
 * Service for executing complex queries for {@link MPreBidMeetingAttachment} entities in the database.
 * The main input is a {@link MPreBidMeetingAttachmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPreBidMeetingAttachmentDTO} or a {@link Page} of {@link MPreBidMeetingAttachmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPreBidMeetingAttachmentQueryService extends QueryService<MPreBidMeetingAttachment> {

    private final Logger log = LoggerFactory.getLogger(MPreBidMeetingAttachmentQueryService.class);

    private final MPreBidMeetingAttachmentRepository mPreBidMeetingAttachmentRepository;

    private final MPreBidMeetingAttachmentMapper mPreBidMeetingAttachmentMapper;

    public MPreBidMeetingAttachmentQueryService(MPreBidMeetingAttachmentRepository mPreBidMeetingAttachmentRepository, MPreBidMeetingAttachmentMapper mPreBidMeetingAttachmentMapper) {
        this.mPreBidMeetingAttachmentRepository = mPreBidMeetingAttachmentRepository;
        this.mPreBidMeetingAttachmentMapper = mPreBidMeetingAttachmentMapper;
    }

    /**
     * Return a {@link List} of {@link MPreBidMeetingAttachmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPreBidMeetingAttachmentDTO> findByCriteria(MPreBidMeetingAttachmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPreBidMeetingAttachment> specification = createSpecification(criteria);
        return mPreBidMeetingAttachmentMapper.toDto(mPreBidMeetingAttachmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPreBidMeetingAttachmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPreBidMeetingAttachmentDTO> findByCriteria(MPreBidMeetingAttachmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPreBidMeetingAttachment> specification = createSpecification(criteria);
        return mPreBidMeetingAttachmentRepository.findAll(specification, page)
            .map(mPreBidMeetingAttachmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPreBidMeetingAttachmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPreBidMeetingAttachment> specification = createSpecification(criteria);
        return mPreBidMeetingAttachmentRepository.count(specification);
    }

    /**
     * Function to convert {@link MPreBidMeetingAttachmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPreBidMeetingAttachment> createSpecification(MPreBidMeetingAttachmentCriteria criteria) {
        Specification<MPreBidMeetingAttachment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPreBidMeetingAttachment_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPreBidMeetingAttachment_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPreBidMeetingAttachment_.active));
            }
            if (criteria.getCAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCAttachmentId(),
                    root -> root.join(MPreBidMeetingAttachment_.cAttachment, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPreBidMeetingAttachment_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getPreBidMeetingId() != null) {
                specification = specification.and(buildSpecification(criteria.getPreBidMeetingId(),
                    root -> root.join(MPreBidMeetingAttachment_.preBidMeeting, JoinType.LEFT).get(MPreBidMeeting_.id)));
            }
        }
        return specification;
    }
}
