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

import com.bhp.opusb.domain.MBiddingScheduleAttachment;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingScheduleAttachmentRepository;
import com.bhp.opusb.service.dto.MBiddingScheduleAttachmentCriteria;
import com.bhp.opusb.service.dto.MBiddingScheduleAttachmentDTO;
import com.bhp.opusb.service.mapper.MBiddingScheduleAttachmentMapper;

/**
 * Service for executing complex queries for {@link MBiddingScheduleAttachment} entities in the database.
 * The main input is a {@link MBiddingScheduleAttachmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingScheduleAttachmentDTO} or a {@link Page} of {@link MBiddingScheduleAttachmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingScheduleAttachmentQueryService extends QueryService<MBiddingScheduleAttachment> {

    private final Logger log = LoggerFactory.getLogger(MBiddingScheduleAttachmentQueryService.class);

    private final MBiddingScheduleAttachmentRepository mBiddingScheduleAttachmentRepository;

    private final MBiddingScheduleAttachmentMapper mBiddingScheduleAttachmentMapper;

    public MBiddingScheduleAttachmentQueryService(MBiddingScheduleAttachmentRepository mBiddingScheduleAttachmentRepository, MBiddingScheduleAttachmentMapper mBiddingScheduleAttachmentMapper) {
        this.mBiddingScheduleAttachmentRepository = mBiddingScheduleAttachmentRepository;
        this.mBiddingScheduleAttachmentMapper = mBiddingScheduleAttachmentMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingScheduleAttachmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingScheduleAttachmentDTO> findByCriteria(MBiddingScheduleAttachmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingScheduleAttachment> specification = createSpecification(criteria);
        return mBiddingScheduleAttachmentMapper.toDto(mBiddingScheduleAttachmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingScheduleAttachmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingScheduleAttachmentDTO> findByCriteria(MBiddingScheduleAttachmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingScheduleAttachment> specification = createSpecification(criteria);
        return mBiddingScheduleAttachmentRepository.findAll(specification, page)
            .map(mBiddingScheduleAttachmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingScheduleAttachmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingScheduleAttachment> specification = createSpecification(criteria);
        return mBiddingScheduleAttachmentRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingScheduleAttachmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingScheduleAttachment> createSpecification(MBiddingScheduleAttachmentCriteria criteria) {
        Specification<MBiddingScheduleAttachment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingScheduleAttachment_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingScheduleAttachment_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingScheduleAttachment_.active));
            }
            if (criteria.getCAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCAttachmentId(),
                    root -> root.join(MBiddingScheduleAttachment_.cAttachment, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingScheduleAttachment_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingScheduleId(),
                    root -> root.join(MBiddingScheduleAttachment_.biddingSchedule, JoinType.LEFT).get(MBiddingSchedule_.id)));
            }
        }
        return specification;
    }
}
