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

import com.bhp.opusb.domain.MBiddingEvaluationTeam;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingEvaluationTeamRepository;
import com.bhp.opusb.service.dto.MBiddingEvaluationTeamCriteria;
import com.bhp.opusb.service.dto.MBiddingEvaluationTeamDTO;
import com.bhp.opusb.service.mapper.MBiddingEvaluationTeamMapper;

/**
 * Service for executing complex queries for {@link MBiddingEvaluationTeam} entities in the database.
 * The main input is a {@link MBiddingEvaluationTeamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingEvaluationTeamDTO} or a {@link Page} of {@link MBiddingEvaluationTeamDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingEvaluationTeamQueryService extends QueryService<MBiddingEvaluationTeam> {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvaluationTeamQueryService.class);

    private final MBiddingEvaluationTeamRepository mBiddingEvaluationTeamRepository;

    private final MBiddingEvaluationTeamMapper mBiddingEvaluationTeamMapper;

    public MBiddingEvaluationTeamQueryService(MBiddingEvaluationTeamRepository mBiddingEvaluationTeamRepository, MBiddingEvaluationTeamMapper mBiddingEvaluationTeamMapper) {
        this.mBiddingEvaluationTeamRepository = mBiddingEvaluationTeamRepository;
        this.mBiddingEvaluationTeamMapper = mBiddingEvaluationTeamMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingEvaluationTeamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingEvaluationTeamDTO> findByCriteria(MBiddingEvaluationTeamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingEvaluationTeam> specification = createSpecification(criteria);
        return mBiddingEvaluationTeamMapper.toDto(mBiddingEvaluationTeamRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingEvaluationTeamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvaluationTeamDTO> findByCriteria(MBiddingEvaluationTeamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingEvaluationTeam> specification = createSpecification(criteria);
        return mBiddingEvaluationTeamRepository.findAll(specification, page)
            .map(mBiddingEvaluationTeamMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingEvaluationTeamCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingEvaluationTeam> specification = createSpecification(criteria);
        return mBiddingEvaluationTeamRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingEvaluationTeamCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingEvaluationTeam> createSpecification(MBiddingEvaluationTeamCriteria criteria) {
        Specification<MBiddingEvaluationTeam> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingEvaluationTeam_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), MBiddingEvaluationTeam_.status));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingEvaluationTeam_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingEvaluationTeam_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingEvaluationTeam_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MBiddingEvaluationTeam_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getPrequalificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationId(),
                    root -> root.join(MBiddingEvaluationTeam_.prequalification, JoinType.LEFT).get(MPrequalificationInformation_.id)));
            }
        }
        return specification;
    }
}
