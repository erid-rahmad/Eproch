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

import com.bhp.opusb.domain.MPreBidMeetingParticipant;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPreBidMeetingParticipantRepository;
import com.bhp.opusb.service.dto.MPreBidMeetingParticipantCriteria;
import com.bhp.opusb.service.dto.MPreBidMeetingParticipantDTO;
import com.bhp.opusb.service.mapper.MPreBidMeetingParticipantMapper;

/**
 * Service for executing complex queries for {@link MPreBidMeetingParticipant} entities in the database.
 * The main input is a {@link MPreBidMeetingParticipantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPreBidMeetingParticipantDTO} or a {@link Page} of {@link MPreBidMeetingParticipantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPreBidMeetingParticipantQueryService extends QueryService<MPreBidMeetingParticipant> {

    private final Logger log = LoggerFactory.getLogger(MPreBidMeetingParticipantQueryService.class);

    private final MPreBidMeetingParticipantRepository mPreBidMeetingParticipantRepository;

    private final MPreBidMeetingParticipantMapper mPreBidMeetingParticipantMapper;

    public MPreBidMeetingParticipantQueryService(MPreBidMeetingParticipantRepository mPreBidMeetingParticipantRepository, MPreBidMeetingParticipantMapper mPreBidMeetingParticipantMapper) {
        this.mPreBidMeetingParticipantRepository = mPreBidMeetingParticipantRepository;
        this.mPreBidMeetingParticipantMapper = mPreBidMeetingParticipantMapper;
    }

    /**
     * Return a {@link List} of {@link MPreBidMeetingParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPreBidMeetingParticipantDTO> findByCriteria(MPreBidMeetingParticipantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPreBidMeetingParticipant> specification = createSpecification(criteria);
        return mPreBidMeetingParticipantMapper.toDto(mPreBidMeetingParticipantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPreBidMeetingParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPreBidMeetingParticipantDTO> findByCriteria(MPreBidMeetingParticipantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPreBidMeetingParticipant> specification = createSpecification(criteria);
        return mPreBidMeetingParticipantRepository.findAll(specification, page)
            .map(mPreBidMeetingParticipantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPreBidMeetingParticipantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPreBidMeetingParticipant> specification = createSpecification(criteria);
        return mPreBidMeetingParticipantRepository.count(specification);
    }

    /**
     * Function to convert {@link MPreBidMeetingParticipantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPreBidMeetingParticipant> createSpecification(MPreBidMeetingParticipantCriteria criteria) {
        Specification<MPreBidMeetingParticipant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPreBidMeetingParticipant_.id));
            }
            if (criteria.getAttended() != null) {
                specification = specification.and(buildSpecification(criteria.getAttended(), MPreBidMeetingParticipant_.attended));
            }
            if (criteria.getRegisterDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisterDate(), MPreBidMeetingParticipant_.registerDate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPreBidMeetingParticipant_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPreBidMeetingParticipant_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPreBidMeetingParticipant_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getPreBidMeetingId() != null) {
                specification = specification.and(buildSpecification(criteria.getPreBidMeetingId(),
                    root -> root.join(MPreBidMeetingParticipant_.preBidMeeting, JoinType.LEFT).get(MPreBidMeeting_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MPreBidMeetingParticipant_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
