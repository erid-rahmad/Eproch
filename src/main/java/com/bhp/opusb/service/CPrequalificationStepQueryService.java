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

import com.bhp.opusb.domain.CPrequalificationStep;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CPrequalificationStepRepository;
import com.bhp.opusb.service.dto.CPrequalificationStepCriteria;
import com.bhp.opusb.service.dto.CPrequalificationStepDTO;
import com.bhp.opusb.service.mapper.CPrequalificationStepMapper;

/**
 * Service for executing complex queries for {@link CPrequalificationStep} entities in the database.
 * The main input is a {@link CPrequalificationStepCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPrequalificationStepDTO} or a {@link Page} of {@link CPrequalificationStepDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPrequalificationStepQueryService extends QueryService<CPrequalificationStep> {

    private final Logger log = LoggerFactory.getLogger(CPrequalificationStepQueryService.class);

    private final CPrequalificationStepRepository cPrequalificationStepRepository;

    private final CPrequalificationStepMapper cPrequalificationStepMapper;

    public CPrequalificationStepQueryService(CPrequalificationStepRepository cPrequalificationStepRepository, CPrequalificationStepMapper cPrequalificationStepMapper) {
        this.cPrequalificationStepRepository = cPrequalificationStepRepository;
        this.cPrequalificationStepMapper = cPrequalificationStepMapper;
    }

    /**
     * Return a {@link List} of {@link CPrequalificationStepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPrequalificationStepDTO> findByCriteria(CPrequalificationStepCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CPrequalificationStep> specification = createSpecification(criteria);
        return cPrequalificationStepMapper.toDto(cPrequalificationStepRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPrequalificationStepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalificationStepDTO> findByCriteria(CPrequalificationStepCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CPrequalificationStep> specification = createSpecification(criteria);
        return cPrequalificationStepRepository.findAll(specification, page)
            .map(cPrequalificationStepMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CPrequalificationStepCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CPrequalificationStep> specification = createSpecification(criteria);
        return cPrequalificationStepRepository.count(specification);
    }

    /**
     * Function to convert {@link CPrequalificationStepCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CPrequalificationStep> createSpecification(CPrequalificationStepCriteria criteria) {
        Specification<CPrequalificationStep> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CPrequalificationStep_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CPrequalificationStep_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CPrequalificationStep_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), CPrequalificationStep_.type));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CPrequalificationStep_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CPrequalificationStep_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CPrequalificationStep_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAdFormId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdFormId(),
                    root -> root.join(CPrequalificationStep_.adForm, JoinType.LEFT).get(AdForm_.id)));
            }
        }
        return specification;
    }
}
