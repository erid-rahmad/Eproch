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

import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingScheduleRepository;
import com.bhp.opusb.service.dto.MBiddingScheduleCriteria;
import com.bhp.opusb.service.dto.MBiddingScheduleDTO;
import com.bhp.opusb.service.mapper.MBiddingScheduleMapper;

/**
 * Service for executing complex queries for {@link MBiddingSchedule} entities in the database.
 * The main input is a {@link MBiddingScheduleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingScheduleDTO} or a {@link Page} of {@link MBiddingScheduleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingScheduleQueryService extends QueryService<MBiddingSchedule> {

    private final Logger log = LoggerFactory.getLogger(MBiddingScheduleQueryService.class);

    private final MBiddingScheduleRepository mBiddingScheduleRepository;

    private final MBiddingScheduleMapper mBiddingScheduleMapper;

    public MBiddingScheduleQueryService(MBiddingScheduleRepository mBiddingScheduleRepository, MBiddingScheduleMapper mBiddingScheduleMapper) {
        this.mBiddingScheduleRepository = mBiddingScheduleRepository;
        this.mBiddingScheduleMapper = mBiddingScheduleMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingScheduleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingScheduleDTO> findByCriteria(MBiddingScheduleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingSchedule> specification = createSpecification(criteria);
        return mBiddingScheduleMapper.toDto(mBiddingScheduleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingScheduleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingScheduleDTO> findByCriteria(MBiddingScheduleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingSchedule> specification = createSpecification(criteria);
        return mBiddingScheduleRepository.findAll(specification, page)
            .map(mBiddingScheduleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingScheduleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingSchedule> specification = createSpecification(criteria);
        return mBiddingScheduleRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingScheduleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingSchedule> createSpecification(MBiddingScheduleCriteria criteria) {
        Specification<MBiddingSchedule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingSchedule_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), MBiddingSchedule_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), MBiddingSchedule_.endDate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingSchedule_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingSchedule_.active));
            }
            if (criteria.getDateSetId() != null) {
                specification = specification.and(buildSpecification(criteria.getDateSetId(),
                    root -> root.join(MBiddingSchedule_.dateSet, JoinType.INNER).get(MPrequalificationDateSet_.id)));
            }
            if (criteria.getActualStartDate() != null) {
                specification = specification.and(buildSpecification(criteria.getActualStartDate(),
                    root -> root.join(MBiddingSchedule_.dateSet, JoinType.INNER).get(MPrequalificationDateSet_.startDate)));
            }
            if (criteria.getActualEndDate() != null) {
                specification = specification.and(buildSpecification(criteria.getActualEndDate(),
                    root -> root.join(MBiddingSchedule_.dateSet, JoinType.INNER).get(MPrequalificationDateSet_.endDate)));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(),
                    root -> root.join(MBiddingSchedule_.dateSet, JoinType.INNER).get(MPrequalificationDateSet_.status)));
            }
            if (criteria.getMBiddingScheduleAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getMBiddingScheduleAttachmentId(),
                    root -> root.join(MBiddingSchedule_.mBiddingScheduleAttachments, JoinType.LEFT).get(MBiddingScheduleAttachment_.id)));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MBiddingSchedule_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingSchedule_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getEventTypeLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getEventTypeLineId(),
                    root -> root.join(MBiddingSchedule_.eventTypeLine, JoinType.LEFT).get(CEventTypeline_.id)));
            }
        }
        return specification;
    }
}
