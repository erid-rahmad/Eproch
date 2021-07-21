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

import com.bhp.opusb.domain.MPreqRegistEvaluation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPreqRegistEvaluationRepository;
import com.bhp.opusb.service.dto.MPreqRegistEvaluationCriteria;
import com.bhp.opusb.service.dto.MPreqRegistEvaluationDTO;
import com.bhp.opusb.service.mapper.MPreqRegistEvaluationMapper;

/**
 * Service for executing complex queries for {@link MPreqRegistEvaluation} entities in the database.
 * The main input is a {@link MPreqRegistEvaluationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPreqRegistEvaluationDTO} or a {@link Page} of {@link MPreqRegistEvaluationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPreqRegistEvaluationQueryService extends QueryService<MPreqRegistEvaluation> {

    private final Logger log = LoggerFactory.getLogger(MPreqRegistEvaluationQueryService.class);

    private final MPreqRegistEvaluationRepository mPreqRegistEvaluationRepository;

    private final MPreqRegistEvaluationMapper mPreqRegistEvaluationMapper;

    public MPreqRegistEvaluationQueryService(MPreqRegistEvaluationRepository mPreqRegistEvaluationRepository, MPreqRegistEvaluationMapper mPreqRegistEvaluationMapper) {
        this.mPreqRegistEvaluationRepository = mPreqRegistEvaluationRepository;
        this.mPreqRegistEvaluationMapper = mPreqRegistEvaluationMapper;
    }

    /**
     * Return a {@link List} of {@link MPreqRegistEvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPreqRegistEvaluationDTO> findByCriteria(MPreqRegistEvaluationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPreqRegistEvaluation> specification = createSpecification(criteria);
        return mPreqRegistEvaluationMapper.toDto(mPreqRegistEvaluationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPreqRegistEvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPreqRegistEvaluationDTO> findByCriteria(MPreqRegistEvaluationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPreqRegistEvaluation> specification = createSpecification(criteria);
        return mPreqRegistEvaluationRepository.findAll(specification, page)
            .map(mPreqRegistEvaluationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPreqRegistEvaluationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPreqRegistEvaluation> specification = createSpecification(criteria);
        return mPreqRegistEvaluationRepository.count(specification);
    }

    /**
     * Function to convert {@link MPreqRegistEvaluationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPreqRegistEvaluation> createSpecification(MPreqRegistEvaluationCriteria criteria) {
        Specification<MPreqRegistEvaluation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPreqRegistEvaluation_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPreqRegistEvaluation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPreqRegistEvaluation_.active));
            }
            if (criteria.getEvaluation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluation(), MPreqRegistEvaluation_.evaluation));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPreqRegistEvaluation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MPreqRegistEvaluation_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getPrequalificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationId(),
                    root -> root.join(MPreqRegistEvaluation_.prequalification, JoinType.LEFT).get(MPrequalificationInformation_.id)));
            }
        }
        return specification;
    }
}
