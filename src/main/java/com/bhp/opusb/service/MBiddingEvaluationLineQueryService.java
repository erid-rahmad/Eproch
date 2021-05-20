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

import com.bhp.opusb.domain.MBiddingEvaluationLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingEvaluationLineRepository;
import com.bhp.opusb.service.dto.MBiddingEvaluationLineCriteria;
import com.bhp.opusb.service.dto.MBiddingEvaluationLineDTO;
import com.bhp.opusb.service.mapper.MBiddingEvaluationLineMapper;

/**
 * Service for executing complex queries for {@link MBiddingEvaluationLine} entities in the database.
 * The main input is a {@link MBiddingEvaluationLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingEvaluationLineDTO} or a {@link Page} of {@link MBiddingEvaluationLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingEvaluationLineQueryService extends QueryService<MBiddingEvaluationLine> {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvaluationLineQueryService.class);

    private final MBiddingEvaluationLineRepository mBiddingEvaluationLineRepository;

    private final MBiddingEvaluationLineMapper mBiddingEvaluationLineMapper;

    public MBiddingEvaluationLineQueryService(MBiddingEvaluationLineRepository mBiddingEvaluationLineRepository, MBiddingEvaluationLineMapper mBiddingEvaluationLineMapper) {
        this.mBiddingEvaluationLineRepository = mBiddingEvaluationLineRepository;
        this.mBiddingEvaluationLineMapper = mBiddingEvaluationLineMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingEvaluationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingEvaluationLineDTO> findByCriteria(MBiddingEvaluationLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingEvaluationLine> specification = createSpecification(criteria);
        return mBiddingEvaluationLineMapper.toDto(mBiddingEvaluationLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingEvaluationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvaluationLineDTO> findByCriteria(MBiddingEvaluationLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingEvaluationLine> specification = createSpecification(criteria);
        return mBiddingEvaluationLineRepository.findAll(specification, page)
            .map(mBiddingEvaluationLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingEvaluationLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingEvaluationLine> specification = createSpecification(criteria);
        return mBiddingEvaluationLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingEvaluationLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingEvaluationLine> createSpecification(MBiddingEvaluationLineCriteria criteria) {
        Specification<MBiddingEvaluationLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingEvaluationLine_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), MBiddingEvaluationLine_.status));
            }
            if (criteria.getEvaluationStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluationStatus(), MBiddingEvaluationLine_.evaluationStatus));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingEvaluationLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingEvaluationLine_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingEvaluationLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingEvaluationId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingEvaluationId(),
                    root -> root.join(MBiddingEvaluationLine_.biddingEvaluation, JoinType.LEFT).get(MBiddingEvaluation_.id)));
            }
        }
        return specification;
    }
}
