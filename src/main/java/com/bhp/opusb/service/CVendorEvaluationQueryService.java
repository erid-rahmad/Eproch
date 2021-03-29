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

import com.bhp.opusb.domain.CVendorEvaluation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CVendorEvaluationRepository;
import com.bhp.opusb.service.dto.CVendorEvaluationCriteria;
import com.bhp.opusb.service.dto.CVendorEvaluationDTO;
import com.bhp.opusb.service.mapper.CVendorEvaluationMapper;

/**
 * Service for executing complex queries for {@link CVendorEvaluation} entities in the database.
 * The main input is a {@link CVendorEvaluationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CVendorEvaluationDTO} or a {@link Page} of {@link CVendorEvaluationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CVendorEvaluationQueryService extends QueryService<CVendorEvaluation> {

    private final Logger log = LoggerFactory.getLogger(CVendorEvaluationQueryService.class);

    private final CVendorEvaluationRepository cVendorEvaluationRepository;

    private final CVendorEvaluationMapper cVendorEvaluationMapper;

    public CVendorEvaluationQueryService(CVendorEvaluationRepository cVendorEvaluationRepository, CVendorEvaluationMapper cVendorEvaluationMapper) {
        this.cVendorEvaluationRepository = cVendorEvaluationRepository;
        this.cVendorEvaluationMapper = cVendorEvaluationMapper;
    }

    /**
     * Return a {@link List} of {@link CVendorEvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CVendorEvaluationDTO> findByCriteria(CVendorEvaluationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CVendorEvaluation> specification = createSpecification(criteria);
        return cVendorEvaluationMapper.toDto(cVendorEvaluationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CVendorEvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorEvaluationDTO> findByCriteria(CVendorEvaluationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CVendorEvaluation> specification = createSpecification(criteria);
        return cVendorEvaluationRepository.findAll(specification, page)
            .map(cVendorEvaluationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CVendorEvaluationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CVendorEvaluation> specification = createSpecification(criteria);
        return cVendorEvaluationRepository.count(specification);
    }

    /**
     * Function to convert {@link CVendorEvaluationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CVendorEvaluation> createSpecification(CVendorEvaluationCriteria criteria) {
        Specification<CVendorEvaluation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CVendorEvaluation_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CVendorEvaluation_.name));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CVendorEvaluation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CVendorEvaluation_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CVendorEvaluation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
