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

import com.bhp.opusb.domain.MBiddingNegotiation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingNegotiationRepository;
import com.bhp.opusb.service.dto.MBiddingNegotiationCriteria;
import com.bhp.opusb.service.dto.MBiddingNegotiationDTO;
import com.bhp.opusb.service.mapper.MBiddingNegotiationMapper;

/**
 * Service for executing complex queries for {@link MBiddingNegotiation} entities in the database.
 * The main input is a {@link MBiddingNegotiationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingNegotiationDTO} or a {@link Page} of {@link MBiddingNegotiationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingNegotiationQueryService extends QueryService<MBiddingNegotiation> {

    private final Logger log = LoggerFactory.getLogger(MBiddingNegotiationQueryService.class);

    private final MBiddingNegotiationRepository mBiddingNegotiationRepository;

    private final MBiddingNegotiationMapper mBiddingNegotiationMapper;

    public MBiddingNegotiationQueryService(MBiddingNegotiationRepository mBiddingNegotiationRepository, MBiddingNegotiationMapper mBiddingNegotiationMapper) {
        this.mBiddingNegotiationRepository = mBiddingNegotiationRepository;
        this.mBiddingNegotiationMapper = mBiddingNegotiationMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingNegotiationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingNegotiationDTO> findByCriteria(MBiddingNegotiationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingNegotiation> specification = createSpecification(criteria);
        return mBiddingNegotiationMapper.toDto(mBiddingNegotiationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingNegotiationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingNegotiationDTO> findByCriteria(MBiddingNegotiationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingNegotiation> specification = createSpecification(criteria);
        return mBiddingNegotiationRepository.findAll(specification, page)
            .map(mBiddingNegotiationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingNegotiationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingNegotiation> specification = createSpecification(criteria);
        return mBiddingNegotiationRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingNegotiationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingNegotiation> createSpecification(MBiddingNegotiationCriteria criteria) {
        Specification<MBiddingNegotiation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingNegotiation_.id));
            }
            if (criteria.getBiddingStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBiddingStatus(), MBiddingNegotiation_.biddingStatus));
            }
            if (criteria.getEvaluationStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluationStatus(), MBiddingNegotiation_.evaluationStatus));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), MBiddingNegotiation_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), MBiddingNegotiation_.endDate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingNegotiation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingNegotiation_.active));
            }
            if (criteria.getBiddingEvalResultId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingEvalResultId(),
                    root -> root.join(MBiddingNegotiation_.biddingEvalResult, JoinType.LEFT).get(MBiddingEvalResult_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingNegotiation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingScheduleId(),
                    root -> root.join(MBiddingNegotiation_.biddingSchedule, JoinType.LEFT).get(MBiddingSchedule_.id)));
            }
        }
        return specification;
    }
}
