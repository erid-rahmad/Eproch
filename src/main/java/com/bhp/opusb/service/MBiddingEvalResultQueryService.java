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

import com.bhp.opusb.domain.MBiddingEvalResult;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingEvalResultRepository;
import com.bhp.opusb.service.dto.MBiddingEvalResultCriteria;
import com.bhp.opusb.service.dto.MBiddingEvalResultDTO;
import com.bhp.opusb.service.mapper.MBiddingEvalResultMapper;

/**
 * Service for executing complex queries for {@link MBiddingEvalResult} entities in the database.
 * The main input is a {@link MBiddingEvalResultCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingEvalResultDTO} or a {@link Page} of {@link MBiddingEvalResultDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingEvalResultQueryService extends QueryService<MBiddingEvalResult> {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvalResultQueryService.class);

    private final MBiddingEvalResultRepository mBiddingEvalResultRepository;

    private final MBiddingEvalResultMapper mBiddingEvalResultMapper;

    public MBiddingEvalResultQueryService(MBiddingEvalResultRepository mBiddingEvalResultRepository, MBiddingEvalResultMapper mBiddingEvalResultMapper) {
        this.mBiddingEvalResultRepository = mBiddingEvalResultRepository;
        this.mBiddingEvalResultMapper = mBiddingEvalResultMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingEvalResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingEvalResultDTO> findByCriteria(MBiddingEvalResultCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingEvalResult> specification = createSpecification(criteria);
        return mBiddingEvalResultMapper.toDto(mBiddingEvalResultRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingEvalResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvalResultDTO> findByCriteria(MBiddingEvalResultCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingEvalResult> specification = createSpecification(criteria);
        return mBiddingEvalResultRepository.findAll(specification, page)
            .map(mBiddingEvalResultMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingEvalResultCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingEvalResult> specification = createSpecification(criteria);
        return mBiddingEvalResultRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingEvalResultCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingEvalResult> createSpecification(MBiddingEvalResultCriteria criteria) {
        Specification<MBiddingEvalResult> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingEvalResult_.id));
            }
            if (criteria.getEvaluationStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluationStatus(), MBiddingEvalResult_.evaluationStatus));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), MBiddingEvalResult_.status));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), MBiddingEvalResult_.score));
            }
            if (criteria.getRank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRank(), MBiddingEvalResult_.rank));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingEvalResult_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingEvalResult_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingEvalResult_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubmissionId(),
                    root -> root.join(MBiddingEvalResult_.biddingSubmission, JoinType.LEFT).get(MBiddingSubmission_.id)));
            }
        }
        return specification;
    }
}
