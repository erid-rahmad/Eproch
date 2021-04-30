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

import com.bhp.opusb.domain.MPreBidMeeting;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPreBidMeetingRepository;
import com.bhp.opusb.service.dto.MPreBidMeetingCriteria;
import com.bhp.opusb.service.dto.MPreBidMeetingDTO;
import com.bhp.opusb.service.mapper.MPreBidMeetingMapper;

/**
 * Service for executing complex queries for {@link MPreBidMeeting} entities in the database.
 * The main input is a {@link MPreBidMeetingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPreBidMeetingDTO} or a {@link Page} of {@link MPreBidMeetingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPreBidMeetingQueryService extends QueryService<MPreBidMeeting> {

    private final Logger log = LoggerFactory.getLogger(MPreBidMeetingQueryService.class);

    private final MPreBidMeetingRepository mPreBidMeetingRepository;

    private final MPreBidMeetingMapper mPreBidMeetingMapper;

    public MPreBidMeetingQueryService(MPreBidMeetingRepository mPreBidMeetingRepository, MPreBidMeetingMapper mPreBidMeetingMapper) {
        this.mPreBidMeetingRepository = mPreBidMeetingRepository;
        this.mPreBidMeetingMapper = mPreBidMeetingMapper;
    }

    /**
     * Return a {@link List} of {@link MPreBidMeetingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPreBidMeetingDTO> findByCriteria(MPreBidMeetingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPreBidMeeting> specification = createSpecification(criteria);
        return mPreBidMeetingMapper.toDto(mPreBidMeetingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPreBidMeetingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPreBidMeetingDTO> findByCriteria(MPreBidMeetingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPreBidMeeting> specification = createSpecification(criteria);
        return mPreBidMeetingRepository.findAll(specification, page)
            .map(mPreBidMeetingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPreBidMeetingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPreBidMeeting> specification = createSpecification(criteria);
        return mPreBidMeetingRepository.count(specification);
    }

    /**
     * Function to convert {@link MPreBidMeetingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPreBidMeeting> createSpecification(MPreBidMeetingCriteria criteria) {
        Specification<MPreBidMeeting> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPreBidMeeting_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPreBidMeeting_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPreBidMeeting_.active));
            }
            if (criteria.getBiddingScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingScheduleId(),
                    root -> root.join(MPreBidMeeting_.biddingSchedule, JoinType.LEFT).get(MBiddingSchedule_.id)));
            }
            if (criteria.getMPreBidMeetingAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getMPreBidMeetingAttachmentId(),
                    root -> root.join(MPreBidMeeting_.mPreBidMeetingAttachments, JoinType.LEFT).get(MPreBidMeetingAttachment_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPreBidMeeting_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
