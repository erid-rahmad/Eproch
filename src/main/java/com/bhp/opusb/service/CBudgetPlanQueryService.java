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

import com.bhp.opusb.domain.CBudgetPlan;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CBudgetPlanRepository;
import com.bhp.opusb.service.dto.CBudgetPlanCriteria;
import com.bhp.opusb.service.dto.CBudgetPlanDTO;
import com.bhp.opusb.service.mapper.CBudgetPlanMapper;

/**
 * Service for executing complex queries for {@link CBudgetPlan} entities in the database.
 * The main input is a {@link CBudgetPlanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CBudgetPlanDTO} or a {@link Page} of {@link CBudgetPlanDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CBudgetPlanQueryService extends QueryService<CBudgetPlan> {

    private final Logger log = LoggerFactory.getLogger(CBudgetPlanQueryService.class);

    private final CBudgetPlanRepository cBudgetPlanRepository;

    private final CBudgetPlanMapper cBudgetPlanMapper;

    public CBudgetPlanQueryService(CBudgetPlanRepository cBudgetPlanRepository, CBudgetPlanMapper cBudgetPlanMapper) {
        this.cBudgetPlanRepository = cBudgetPlanRepository;
        this.cBudgetPlanMapper = cBudgetPlanMapper;
    }

    /**
     * Return a {@link List} of {@link CBudgetPlanDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CBudgetPlanDTO> findByCriteria(CBudgetPlanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CBudgetPlan> specification = createSpecification(criteria);
        return cBudgetPlanMapper.toDto(cBudgetPlanRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CBudgetPlanDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CBudgetPlanDTO> findByCriteria(CBudgetPlanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CBudgetPlan> specification = createSpecification(criteria);
        return cBudgetPlanRepository.findAll(specification, page)
            .map(cBudgetPlanMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CBudgetPlanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CBudgetPlan> specification = createSpecification(criteria);
        return cBudgetPlanRepository.count(specification);
    }

    /**
     * Function to convert {@link CBudgetPlanCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CBudgetPlan> createSpecification(CBudgetPlanCriteria criteria) {
        Specification<CBudgetPlan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CBudgetPlan_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CBudgetPlan_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CBudgetPlan_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CBudgetPlan_.description));
            }
            if (criteria.getBudgetDeduction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBudgetDeduction(), CBudgetPlan_.budgetDeduction));
            }
            if (criteria.getBudgetAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBudgetAmount(), CBudgetPlan_.budgetAmount));
            }
            if (criteria.getAmountAvailable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountAvailable(), CBudgetPlan_.amountAvailable));
            }
            if (criteria.getDocumentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDocumentDate(), CBudgetPlan_.documentDate));
            }
            if (criteria.getPreventOverBudget() != null) {
                specification = specification.and(buildSpecification(criteria.getPreventOverBudget(), CBudgetPlan_.preventOverBudget));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CBudgetPlan_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CBudgetPlan_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CBudgetPlan_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCCostCenterId(),
                    root -> root.join(CBudgetPlan_.cCostCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
            if (criteria.getCCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCCurrencyId(),
                    root -> root.join(CBudgetPlan_.cCurrency, JoinType.LEFT).get(CCurrency_.id)));
            }
        }
        return specification;
    }
}
