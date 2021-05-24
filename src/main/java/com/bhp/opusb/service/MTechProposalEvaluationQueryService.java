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

import com.bhp.opusb.domain.MTechProposalEvaluation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MTechProposalEvaluationRepository;
import com.bhp.opusb.service.dto.MTechProposalEvaluationCriteria;
import com.bhp.opusb.service.dto.MTechProposalEvaluationDTO;
import com.bhp.opusb.service.mapper.MTechProposalEvaluationMapper;

/**
 * Service for executing complex queries for {@link MTechProposalEvaluation} entities in the database.
 * The main input is a {@link MTechProposalEvaluationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MTechProposalEvaluationDTO} or a {@link Page} of {@link MTechProposalEvaluationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MTechProposalEvaluationQueryService extends QueryService<MTechProposalEvaluation> {

    private final Logger log = LoggerFactory.getLogger(MTechProposalEvaluationQueryService.class);

    private final MTechProposalEvaluationRepository mTechProposalEvaluationRepository;

    private final MTechProposalEvaluationMapper mTechProposalEvaluationMapper;

    public MTechProposalEvaluationQueryService(MTechProposalEvaluationRepository mTechProposalEvaluationRepository, MTechProposalEvaluationMapper mTechProposalEvaluationMapper) {
        this.mTechProposalEvaluationRepository = mTechProposalEvaluationRepository;
        this.mTechProposalEvaluationMapper = mTechProposalEvaluationMapper;
    }

    /**
     * Return a {@link List} of {@link MTechProposalEvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MTechProposalEvaluationDTO> findByCriteria(MTechProposalEvaluationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MTechProposalEvaluation> specification = createSpecification(criteria);
        return mTechProposalEvaluationMapper.toDto(mTechProposalEvaluationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MTechProposalEvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MTechProposalEvaluationDTO> findByCriteria(MTechProposalEvaluationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MTechProposalEvaluation> specification = createSpecification(criteria);
        return mTechProposalEvaluationRepository.findAll(specification, page)
            .map(mTechProposalEvaluationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MTechProposalEvaluationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MTechProposalEvaluation> specification = createSpecification(criteria);
        return mTechProposalEvaluationRepository.count(specification);
    }

    /**
     * Function to convert {@link MTechProposalEvaluationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MTechProposalEvaluation> createSpecification(MTechProposalEvaluationCriteria criteria) {
        Specification<MTechProposalEvaluation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MTechProposalEvaluation_.id));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), MTechProposalEvaluation_.notes));
            }
            if (criteria.getEvaluation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluation(), MTechProposalEvaluation_.evaluation));
            }
            if (criteria.getAverageScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAverageScore(), MTechProposalEvaluation_.averageScore));
            }
            if (criteria.getPassFail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassFail(), MTechProposalEvaluation_.passFail));
            }
            if (criteria.getRequirement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequirement(), MTechProposalEvaluation_.requirement));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MTechProposalEvaluation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MTechProposalEvaluation_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MTechProposalEvaluation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MTechProposalEvaluation_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getEvaluationMethodCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationMethodCriteriaId(),
                    root -> root.join(MTechProposalEvaluation_.evaluationMethodCriteria, JoinType.LEFT).get(CEvaluationMethodCriteria_.id)));
            }
            if (criteria.getEvalMethodSubCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvalMethodSubCriteriaId(),
                    root -> root.join(MTechProposalEvaluation_.evalMethodSubCriteria, JoinType.LEFT).get(CEvalMethodSubCriteria_.id)));
            }
            if (criteria.getBiddingSubCriteriaLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaLineId(),
                    root -> root.join(MTechProposalEvaluation_.biddingSubCriteriaLine, JoinType.LEFT).get(CBiddingSubCriteriaLine_.id)));
            }
        }
        return specification;
    }
}
