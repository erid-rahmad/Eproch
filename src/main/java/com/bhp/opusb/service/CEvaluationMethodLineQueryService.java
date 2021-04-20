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

import com.bhp.opusb.domain.CEvaluationMethodLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CEvaluationMethodLineRepository;
import com.bhp.opusb.service.dto.CEvaluationMethodLineCriteria;
import com.bhp.opusb.service.dto.CEvaluationMethodLineDTO;
import com.bhp.opusb.service.mapper.CEvaluationMethodLineMapper;

/**
 * Service for executing complex queries for {@link CEvaluationMethodLine} entities in the database.
 * The main input is a {@link CEvaluationMethodLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CEvaluationMethodLineDTO} or a {@link Page} of {@link CEvaluationMethodLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CEvaluationMethodLineQueryService extends QueryService<CEvaluationMethodLine> {

    private final Logger log = LoggerFactory.getLogger(CEvaluationMethodLineQueryService.class);

    private final CEvaluationMethodLineRepository cEvaluationMethodLineRepository;

    private final CEvaluationMethodLineMapper cEvaluationMethodLineMapper;

    public CEvaluationMethodLineQueryService(CEvaluationMethodLineRepository cEvaluationMethodLineRepository, CEvaluationMethodLineMapper cEvaluationMethodLineMapper) {
        this.cEvaluationMethodLineRepository = cEvaluationMethodLineRepository;
        this.cEvaluationMethodLineMapper = cEvaluationMethodLineMapper;
    }

    /**
     * Return a {@link List} of {@link CEvaluationMethodLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CEvaluationMethodLineDTO> findByCriteria(CEvaluationMethodLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CEvaluationMethodLine> specification = createSpecification(criteria);
        return cEvaluationMethodLineMapper.toDto(cEvaluationMethodLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CEvaluationMethodLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CEvaluationMethodLineDTO> findByCriteria(CEvaluationMethodLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CEvaluationMethodLine> specification = createSpecification(criteria);
        return cEvaluationMethodLineRepository.findAll(specification, page)
            .map(cEvaluationMethodLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CEvaluationMethodLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CEvaluationMethodLine> specification = createSpecification(criteria);
        return cEvaluationMethodLineRepository.count(specification);
    }

    /**
     * Function to convert {@link CEvaluationMethodLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CEvaluationMethodLine> createSpecification(CEvaluationMethodLineCriteria criteria) {
        Specification<CEvaluationMethodLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CEvaluationMethodLine_.id));
            }
            if (criteria.getEvaluation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluation(), CEvaluationMethodLine_.evaluation));
            }
            if (criteria.getEvaluationType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluationType(), CEvaluationMethodLine_.evaluationType));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), CEvaluationMethodLine_.weight));
            }
            if (criteria.getPassingGrade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassingGrade(), CEvaluationMethodLine_.passingGrade));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CEvaluationMethodLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CEvaluationMethodLine_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CEvaluationMethodLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getEvaluationMethodId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationMethodId(),
                    root -> root.join(CEvaluationMethodLine_.evaluationMethod, JoinType.LEFT).get(CEvaluationMethod_.id)));
            }
        }
        return specification;
    }
}
