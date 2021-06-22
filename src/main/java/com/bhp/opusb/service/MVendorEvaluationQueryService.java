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

import com.bhp.opusb.domain.MVendorEvaluation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVendorEvaluationRepository;
import com.bhp.opusb.service.dto.MVendorEvaluationCriteria;
import com.bhp.opusb.service.dto.MVendorEvaluationDTO;
import com.bhp.opusb.service.mapper.MVendorEvaluationMapper;

/**
 * Service for executing complex queries for {@link MVendorEvaluation} entities in the database.
 * The main input is a {@link MVendorEvaluationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVendorEvaluationDTO} or a {@link Page} of {@link MVendorEvaluationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVendorEvaluationQueryService extends QueryService<MVendorEvaluation> {

    private final Logger log = LoggerFactory.getLogger(MVendorEvaluationQueryService.class);

    private final MVendorEvaluationRepository mVendorEvaluationRepository;

    private final MVendorEvaluationMapper mVendorEvaluationMapper;

    public MVendorEvaluationQueryService(MVendorEvaluationRepository mVendorEvaluationRepository, MVendorEvaluationMapper mVendorEvaluationMapper) {
        this.mVendorEvaluationRepository = mVendorEvaluationRepository;
        this.mVendorEvaluationMapper = mVendorEvaluationMapper;
    }

    /**
     * Return a {@link List} of {@link MVendorEvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVendorEvaluationDTO> findByCriteria(MVendorEvaluationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVendorEvaluation> specification = createSpecification(criteria);
        return mVendorEvaluationMapper.toDto(mVendorEvaluationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVendorEvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorEvaluationDTO> findByCriteria(MVendorEvaluationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVendorEvaluation> specification = createSpecification(criteria);
        return mVendorEvaluationRepository.findAll(specification, page)
            .map(mVendorEvaluationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVendorEvaluationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVendorEvaluation> specification = createSpecification(criteria);
        return mVendorEvaluationRepository.count(specification);
    }

    /**
     * Function to convert {@link MVendorEvaluationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVendorEvaluation> createSpecification(MVendorEvaluationCriteria criteria) {
        Specification<MVendorEvaluation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVendorEvaluation_.id));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), MVendorEvaluation_.score));
            }
            if (criteria.getEvaluationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEvaluationDate(), MVendorEvaluation_.evaluationDate));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MVendorEvaluation_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MVendorEvaluation_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MVendorEvaluation_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MVendorEvaluation_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MVendorEvaluation_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MVendorEvaluation_.processed));
            }
            if (criteria.getDateApprove() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateApprove(), MVendorEvaluation_.dateApprove));
            }
            if (criteria.getDateReject() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReject(), MVendorEvaluation_.dateReject));
            }
            if (criteria.getRejectedReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectedReason(), MVendorEvaluation_.rejectedReason));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVendorEvaluation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVendorEvaluation_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVendorEvaluation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getContractId() != null) {
                specification = specification.and(buildSpecification(criteria.getContractId(),
                    root -> root.join(MVendorEvaluation_.contract, JoinType.LEFT).get(MContract_.id)));
            }
            if (criteria.getReviewerId() != null) {
                specification = specification.and(buildSpecification(criteria.getReviewerId(),
                    root -> root.join(MVendorEvaluation_.reviewer, JoinType.LEFT).get(AdUser_.id)));
            }
        }
        return specification;
    }
}
