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

import com.bhp.opusb.domain.MPrequalificationEval;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalificationEvalRepository;
import com.bhp.opusb.service.dto.MPrequalificationEvalCriteria;
import com.bhp.opusb.service.dto.MPrequalificationEvalDTO;
import com.bhp.opusb.service.mapper.MPrequalificationEvalMapper;

/**
 * Service for executing complex queries for {@link MPrequalificationEval} entities in the database.
 * The main input is a {@link MPrequalificationEvalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalificationEvalDTO} or a {@link Page} of {@link MPrequalificationEvalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalificationEvalQueryService extends QueryService<MPrequalificationEval> {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationEvalQueryService.class);

    private final MPrequalificationEvalRepository mPrequalificationEvalRepository;

    private final MPrequalificationEvalMapper mPrequalificationEvalMapper;

    public MPrequalificationEvalQueryService(MPrequalificationEvalRepository mPrequalificationEvalRepository, MPrequalificationEvalMapper mPrequalificationEvalMapper) {
        this.mPrequalificationEvalRepository = mPrequalificationEvalRepository;
        this.mPrequalificationEvalMapper = mPrequalificationEvalMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalificationEvalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalificationEvalDTO> findByCriteria(MPrequalificationEvalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalificationEval> specification = createSpecification(criteria);
        return mPrequalificationEvalMapper.toDto(mPrequalificationEvalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalificationEvalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationEvalDTO> findByCriteria(MPrequalificationEvalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalificationEval> specification = createSpecification(criteria);
        return mPrequalificationEvalRepository.findAll(specification, page)
            .map(mPrequalificationEvalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalificationEvalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalificationEval> specification = createSpecification(criteria);
        return mPrequalificationEvalRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalificationEvalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalificationEval> createSpecification(MPrequalificationEvalCriteria criteria) {
        Specification<MPrequalificationEval> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalificationEval_.id));
            }
            if (criteria.getAnswer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnswer(), MPrequalificationEval_.answer));
            }
            if (criteria.getDocumentEvaluation() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentEvaluation(), MPrequalificationEval_.documentEvaluation));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MPrequalificationEval_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MPrequalificationEval_.documentStatus));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), MPrequalificationEval_.notes));
            }
            if (criteria.getEvaluation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluation(), MPrequalificationEval_.evaluation));
            }
            if (criteria.getAverageScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAverageScore(), MPrequalificationEval_.averageScore));
            }
            if (criteria.getPassFail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassFail(), MPrequalificationEval_.passFail));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalificationEval_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalificationEval_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalificationEval_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getPrequalificationSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationSubmissionId(),
                    root -> root.join(MPrequalificationEval_.prequalificationSubmission, JoinType.LEFT).get(MPrequalificationSubmission_.id)));
            }
            if (criteria.getBiddingSubCriteriaLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaLineId(),
                    root -> root.join(MPrequalificationEval_.biddingSubCriteriaLine, JoinType.LEFT).get(CBiddingSubCriteriaLine_.id)));
            }
        }
        return specification;
    }
}
