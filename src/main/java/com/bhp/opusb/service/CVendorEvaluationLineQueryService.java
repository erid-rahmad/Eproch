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

import com.bhp.opusb.domain.CVendorEvaluationLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CVendorEvaluationLineRepository;
import com.bhp.opusb.service.dto.CVendorEvaluationLineCriteria;
import com.bhp.opusb.service.dto.CVendorEvaluationLineDTO;
import com.bhp.opusb.service.mapper.CVendorEvaluationLineMapper;

/**
 * Service for executing complex queries for {@link CVendorEvaluationLine} entities in the database.
 * The main input is a {@link CVendorEvaluationLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CVendorEvaluationLineDTO} or a {@link Page} of {@link CVendorEvaluationLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CVendorEvaluationLineQueryService extends QueryService<CVendorEvaluationLine> {

    private final Logger log = LoggerFactory.getLogger(CVendorEvaluationLineQueryService.class);

    private final CVendorEvaluationLineRepository cVendorEvaluationLineRepository;

    private final CVendorEvaluationLineMapper cVendorEvaluationLineMapper;

    public CVendorEvaluationLineQueryService(CVendorEvaluationLineRepository cVendorEvaluationLineRepository, CVendorEvaluationLineMapper cVendorEvaluationLineMapper) {
        this.cVendorEvaluationLineRepository = cVendorEvaluationLineRepository;
        this.cVendorEvaluationLineMapper = cVendorEvaluationLineMapper;
    }

    /**
     * Return a {@link List} of {@link CVendorEvaluationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CVendorEvaluationLineDTO> findByCriteria(CVendorEvaluationLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CVendorEvaluationLine> specification = createSpecification(criteria);
        return cVendorEvaluationLineMapper.toDto(cVendorEvaluationLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CVendorEvaluationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorEvaluationLineDTO> findByCriteria(CVendorEvaluationLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CVendorEvaluationLine> specification = createSpecification(criteria);
        return cVendorEvaluationLineRepository.findAll(specification, page)
            .map(cVendorEvaluationLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CVendorEvaluationLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CVendorEvaluationLine> specification = createSpecification(criteria);
        return cVendorEvaluationLineRepository.count(specification);
    }

    /**
     * Function to convert {@link CVendorEvaluationLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CVendorEvaluationLine> createSpecification(CVendorEvaluationLineCriteria criteria) {
        Specification<CVendorEvaluationLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CVendorEvaluationLine_.id));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), CVendorEvaluationLine_.weight));
            }
            if (criteria.getUserWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserWeight(), CVendorEvaluationLine_.userWeight));
            }
            if (criteria.getProcurementWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcurementWeight(), CVendorEvaluationLine_.procurementWeight));
            }
            if (criteria.getQuestion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion(), CVendorEvaluationLine_.question));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CVendorEvaluationLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CVendorEvaluationLine_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CVendorEvaluationLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCQuestionCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCQuestionCategoryId(),
                    root -> root.join(CVendorEvaluationLine_.cQuestionCategory, JoinType.LEFT).get(CQuestionCategory_.id)));
            }
        }
        return specification;
    }
}
