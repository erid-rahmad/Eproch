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

import com.bhp.opusb.domain.MVendorScoringLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVendorScoringLineRepository;
import com.bhp.opusb.service.dto.MVendorScoringLineCriteria;
import com.bhp.opusb.service.dto.MVendorScoringLineDTO;
import com.bhp.opusb.service.mapper.MVendorScoringLineMapper;

/**
 * Service for executing complex queries for {@link MVendorScoringLine} entities in the database.
 * The main input is a {@link MVendorScoringLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVendorScoringLineDTO} or a {@link Page} of {@link MVendorScoringLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVendorScoringLineQueryService extends QueryService<MVendorScoringLine> {

    private final Logger log = LoggerFactory.getLogger(MVendorScoringLineQueryService.class);

    private final MVendorScoringLineRepository mVendorScoringLineRepository;

    private final MVendorScoringLineMapper mVendorScoringLineMapper;

    public MVendorScoringLineQueryService(MVendorScoringLineRepository mVendorScoringLineRepository, MVendorScoringLineMapper mVendorScoringLineMapper) {
        this.mVendorScoringLineRepository = mVendorScoringLineRepository;
        this.mVendorScoringLineMapper = mVendorScoringLineMapper;
    }

    /**
     * Return a {@link List} of {@link MVendorScoringLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVendorScoringLineDTO> findByCriteria(MVendorScoringLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVendorScoringLine> specification = createSpecification(criteria);
        return mVendorScoringLineMapper.toDto(mVendorScoringLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVendorScoringLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorScoringLineDTO> findByCriteria(MVendorScoringLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVendorScoringLine> specification = createSpecification(criteria);
        return mVendorScoringLineRepository.findAll(specification, page)
            .map(mVendorScoringLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVendorScoringLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVendorScoringLine> specification = createSpecification(criteria);
        return mVendorScoringLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MVendorScoringLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVendorScoringLine> createSpecification(MVendorScoringLineCriteria criteria) {
        Specification<MVendorScoringLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVendorScoringLine_.id));
            }
            if (criteria.getEvaluation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluation(), MVendorScoringLine_.evaluation));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVendorScoringLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVendorScoringLine_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVendorScoringLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getEvaluationMethodLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationMethodLineId(),
                    root -> root.join(MVendorScoringLine_.evaluationMethodLine, JoinType.LEFT).get(CEvaluationMethodLine_.id)));
            }
            if (criteria.getVendorScoringId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorScoringId(),
                    root -> root.join(MVendorScoringLine_.vendorScoring, JoinType.LEFT).get(MVendorScoring_.id)));
            }
        }
        return specification;
    }
}
