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

import com.bhp.opusb.domain.CBudgetPlanLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CBudgetPlanLineRepository;
import com.bhp.opusb.service.dto.CBudgetPlanLineCriteria;
import com.bhp.opusb.service.dto.CBudgetPlanLineDTO;
import com.bhp.opusb.service.mapper.CBudgetPlanLineMapper;

/**
 * Service for executing complex queries for {@link CBudgetPlanLine} entities in the database.
 * The main input is a {@link CBudgetPlanLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CBudgetPlanLineDTO} or a {@link Page} of {@link CBudgetPlanLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CBudgetPlanLineQueryService extends QueryService<CBudgetPlanLine> {

    private final Logger log = LoggerFactory.getLogger(CBudgetPlanLineQueryService.class);

    private final CBudgetPlanLineRepository cBudgetPlanLineRepository;

    private final CBudgetPlanLineMapper cBudgetPlanLineMapper;

    public CBudgetPlanLineQueryService(CBudgetPlanLineRepository cBudgetPlanLineRepository, CBudgetPlanLineMapper cBudgetPlanLineMapper) {
        this.cBudgetPlanLineRepository = cBudgetPlanLineRepository;
        this.cBudgetPlanLineMapper = cBudgetPlanLineMapper;
    }

    /**
     * Return a {@link List} of {@link CBudgetPlanLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CBudgetPlanLineDTO> findByCriteria(CBudgetPlanLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CBudgetPlanLine> specification = createSpecification(criteria);
        return cBudgetPlanLineMapper.toDto(cBudgetPlanLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CBudgetPlanLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CBudgetPlanLineDTO> findByCriteria(CBudgetPlanLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CBudgetPlanLine> specification = createSpecification(criteria);
        return cBudgetPlanLineRepository.findAll(specification, page)
            .map(cBudgetPlanLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CBudgetPlanLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CBudgetPlanLine> specification = createSpecification(criteria);
        return cBudgetPlanLineRepository.count(specification);
    }

    /**
     * Function to convert {@link CBudgetPlanLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CBudgetPlanLine> createSpecification(CBudgetPlanLineCriteria criteria) {
        Specification<CBudgetPlanLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CBudgetPlanLine_.id));
            }
            if (criteria.getTotalDebit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalDebit(), CBudgetPlanLine_.totalDebit));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CBudgetPlanLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CBudgetPlanLine_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CBudgetPlanLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCCurrencyId(),
                    root -> root.join(CBudgetPlanLine_.cCurrency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getCDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getCDocumentTypeId(),
                    root -> root.join(CBudgetPlanLine_.cDocumentType, JoinType.LEFT).get(CDocumentType_.id)));
            }
            if (criteria.getMBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getMBiddingId(),
                    root -> root.join(CBudgetPlanLine_.mBidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getMPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getMPurchaseOrderId(),
                    root -> root.join(CBudgetPlanLine_.mPurchaseOrder, JoinType.LEFT).get(MPurchaseOrder_.id)));
            }
            if (criteria.getMRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getMRequisitionId(),
                    root -> root.join(CBudgetPlanLine_.mRequisition, JoinType.LEFT).get(MRequisition_.id)));
            }
        }
        return specification;
    }
}
