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

import com.bhp.opusb.domain.CEvaluationMethod;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CEvaluationMethodRepository;
import com.bhp.opusb.service.dto.CEvaluationMethodCriteria;
import com.bhp.opusb.service.dto.CEvaluationMethodDTO;
import com.bhp.opusb.service.mapper.CEvaluationMethodMapper;

/**
 * Service for executing complex queries for {@link CEvaluationMethod} entities in the database.
 * The main input is a {@link CEvaluationMethodCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CEvaluationMethodDTO} or a {@link Page} of {@link CEvaluationMethodDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CEvaluationMethodQueryService extends QueryService<CEvaluationMethod> {

    private final Logger log = LoggerFactory.getLogger(CEvaluationMethodQueryService.class);

    private final CEvaluationMethodRepository cEvaluationMethodRepository;

    private final CEvaluationMethodMapper cEvaluationMethodMapper;

    public CEvaluationMethodQueryService(CEvaluationMethodRepository cEvaluationMethodRepository, CEvaluationMethodMapper cEvaluationMethodMapper) {
        this.cEvaluationMethodRepository = cEvaluationMethodRepository;
        this.cEvaluationMethodMapper = cEvaluationMethodMapper;
    }

    /**
     * Return a {@link List} of {@link CEvaluationMethodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CEvaluationMethodDTO> findByCriteria(CEvaluationMethodCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CEvaluationMethod> specification = createSpecification(criteria);
        return cEvaluationMethodMapper.toDto(cEvaluationMethodRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CEvaluationMethodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CEvaluationMethodDTO> findByCriteria(CEvaluationMethodCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CEvaluationMethod> specification = createSpecification(criteria);
        return cEvaluationMethodRepository.findAll(specification, page)
            .map(cEvaluationMethodMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CEvaluationMethodCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CEvaluationMethod> specification = createSpecification(criteria);
        return cEvaluationMethodRepository.count(specification);
    }

    /**
     * Function to convert {@link CEvaluationMethodCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CEvaluationMethod> createSpecification(CEvaluationMethodCriteria criteria) {
        Specification<CEvaluationMethod> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CEvaluationMethod_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CEvaluationMethod_.name));
            }
            if (criteria.getPriceLimit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceLimit(), CEvaluationMethod_.priceLimit));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CEvaluationMethod_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CEvaluationMethod_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CEvaluationMethod_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingTypeId(),
                    root -> root.join(CEvaluationMethod_.biddingType, JoinType.LEFT).get(CBiddingType_.id)));
            }
            if (criteria.getEventTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEventTypeId(),
                    root -> root.join(CEvaluationMethod_.eventType, JoinType.LEFT).get(CEventType_.id)));
            }
        }
        return specification;
    }
}
