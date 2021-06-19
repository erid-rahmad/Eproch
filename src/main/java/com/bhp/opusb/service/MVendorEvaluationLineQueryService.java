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

import com.bhp.opusb.domain.MVendorEvaluationLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVendorEvaluationLineRepository;
import com.bhp.opusb.service.dto.MVendorEvaluationLineCriteria;
import com.bhp.opusb.service.dto.MVendorEvaluationLineDTO;
import com.bhp.opusb.service.mapper.MVendorEvaluationLineMapper;

/**
 * Service for executing complex queries for {@link MVendorEvaluationLine} entities in the database.
 * The main input is a {@link MVendorEvaluationLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVendorEvaluationLineDTO} or a {@link Page} of {@link MVendorEvaluationLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVendorEvaluationLineQueryService extends QueryService<MVendorEvaluationLine> {

    private final Logger log = LoggerFactory.getLogger(MVendorEvaluationLineQueryService.class);

    private final MVendorEvaluationLineRepository mVendorEvaluationLineRepository;

    private final MVendorEvaluationLineMapper mVendorEvaluationLineMapper;

    public MVendorEvaluationLineQueryService(MVendorEvaluationLineRepository mVendorEvaluationLineRepository, MVendorEvaluationLineMapper mVendorEvaluationLineMapper) {
        this.mVendorEvaluationLineRepository = mVendorEvaluationLineRepository;
        this.mVendorEvaluationLineMapper = mVendorEvaluationLineMapper;
    }

    /**
     * Return a {@link List} of {@link MVendorEvaluationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVendorEvaluationLineDTO> findByCriteria(MVendorEvaluationLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVendorEvaluationLine> specification = createSpecification(criteria);
        return mVendorEvaluationLineMapper.toDto(mVendorEvaluationLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVendorEvaluationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorEvaluationLineDTO> findByCriteria(MVendorEvaluationLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVendorEvaluationLine> specification = createSpecification(criteria);
        return mVendorEvaluationLineRepository.findAll(specification, page)
            .map(mVendorEvaluationLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVendorEvaluationLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVendorEvaluationLine> specification = createSpecification(criteria);
        return mVendorEvaluationLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MVendorEvaluationLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVendorEvaluationLine> createSpecification(MVendorEvaluationLineCriteria criteria) {
        Specification<MVendorEvaluationLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVendorEvaluationLine_.id));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), MVendorEvaluationLine_.score));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), MVendorEvaluationLine_.remark));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVendorEvaluationLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVendorEvaluationLine_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVendorEvaluationLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getVendorEvaluationId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorEvaluationId(),
                    root -> root.join(MVendorEvaluationLine_.vendorEvaluation, JoinType.LEFT).get(MVendorEvaluation_.id)));
            }
            if (criteria.getEvaluationLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationLineId(),
                    root -> root.join(MVendorEvaluationLine_.evaluationLine, JoinType.LEFT).get(CVendorEvaluationLine_.id)));
            }
        }
        return specification;
    }
}
