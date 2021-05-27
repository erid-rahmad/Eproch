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

import com.bhp.opusb.domain.MBiddingEvalResultLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingEvalResultLineRepository;
import com.bhp.opusb.service.dto.MBiddingEvalResultLineCriteria;
import com.bhp.opusb.service.dto.MBiddingEvalResultLineDTO;
import com.bhp.opusb.service.mapper.MBiddingEvalResultLineMapper;

/**
 * Service for executing complex queries for {@link MBiddingEvalResultLine} entities in the database.
 * The main input is a {@link MBiddingEvalResultLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingEvalResultLineDTO} or a {@link Page} of {@link MBiddingEvalResultLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingEvalResultLineQueryService extends QueryService<MBiddingEvalResultLine> {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvalResultLineQueryService.class);

    private final MBiddingEvalResultLineRepository mBiddingEvalResultLineRepository;

    private final MBiddingEvalResultLineMapper mBiddingEvalResultLineMapper;

    public MBiddingEvalResultLineQueryService(MBiddingEvalResultLineRepository mBiddingEvalResultLineRepository, MBiddingEvalResultLineMapper mBiddingEvalResultLineMapper) {
        this.mBiddingEvalResultLineRepository = mBiddingEvalResultLineRepository;
        this.mBiddingEvalResultLineMapper = mBiddingEvalResultLineMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingEvalResultLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingEvalResultLineDTO> findByCriteria(MBiddingEvalResultLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingEvalResultLine> specification = createSpecification(criteria);
        return mBiddingEvalResultLineMapper.toDto(mBiddingEvalResultLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingEvalResultLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvalResultLineDTO> findByCriteria(MBiddingEvalResultLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingEvalResultLine> specification = createSpecification(criteria);
        return mBiddingEvalResultLineRepository.findAll(specification, page)
            .map(mBiddingEvalResultLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingEvalResultLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingEvalResultLine> specification = createSpecification(criteria);
        return mBiddingEvalResultLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingEvalResultLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingEvalResultLine> createSpecification(MBiddingEvalResultLineCriteria criteria) {
        Specification<MBiddingEvalResultLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingEvalResultLine_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), MBiddingEvalResultLine_.status));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), MBiddingEvalResultLine_.score));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingEvalResultLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingEvalResultLine_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingEvalResultLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getEvaluationMethodLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationMethodLineId(),
                    root -> root.join(MBiddingEvalResultLine_.evaluationMethodLine, JoinType.LEFT).get(CEvaluationMethodLine_.id)));
            }
            if (criteria.getBiddingEvalResultId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingEvalResultId(),
                    root -> root.join(MBiddingEvalResultLine_.biddingEvalResult, JoinType.LEFT).get(MBiddingEvalResult_.id)));
            }
        }
        return specification;
    }
}
