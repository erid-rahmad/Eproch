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

import com.bhp.opusb.domain.MBiddingEvaluation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingEvaluationRepository;
import com.bhp.opusb.service.dto.MBiddingEvaluationCriteria;
import com.bhp.opusb.service.dto.MBiddingEvaluationDTO;
import com.bhp.opusb.service.mapper.MBiddingEvaluationMapper;

/**
 * Service for executing complex queries for {@link MBiddingEvaluation} entities in the database.
 * The main input is a {@link MBiddingEvaluationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingEvaluationDTO} or a {@link Page} of {@link MBiddingEvaluationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingEvaluationQueryService extends QueryService<MBiddingEvaluation> {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvaluationQueryService.class);

    private final MBiddingEvaluationRepository mBiddingEvaluationRepository;

    private final MBiddingEvaluationMapper mBiddingEvaluationMapper;

    public MBiddingEvaluationQueryService(MBiddingEvaluationRepository mBiddingEvaluationRepository, MBiddingEvaluationMapper mBiddingEvaluationMapper) {
        this.mBiddingEvaluationRepository = mBiddingEvaluationRepository;
        this.mBiddingEvaluationMapper = mBiddingEvaluationMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingEvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingEvaluationDTO> findByCriteria(MBiddingEvaluationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingEvaluation> specification = createSpecification(criteria);
        return mBiddingEvaluationMapper.toDto(mBiddingEvaluationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingEvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvaluationDTO> findByCriteria(MBiddingEvaluationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingEvaluation> specification = createSpecification(criteria);
        return mBiddingEvaluationRepository.findAll(specification, page)
            .map(mBiddingEvaluationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingEvaluationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingEvaluation> specification = createSpecification(criteria);
        return mBiddingEvaluationRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingEvaluationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingEvaluation> createSpecification(MBiddingEvaluationCriteria criteria) {
        Specification<MBiddingEvaluation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingEvaluation_.id));
            }
            if (criteria.getEvaluationStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluationStatus(), MBiddingEvaluation_.evaluationStatus));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingEvaluation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingEvaluation_.active));
            }
            if (criteria.getBiddingSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubmissionId(),
                    root -> root.join(MBiddingEvaluation_.biddingSubmission, JoinType.LEFT).get(MBiddingSubmission_.id)));
            }
            if (criteria.getBiddingScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingScheduleId(),
                    root -> root.join(MBiddingEvaluation_.biddingSchedule, JoinType.LEFT).get(MBiddingSchedule_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingEvaluation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
