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

import com.bhp.opusb.domain.CPaymentTerm;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CPaymentTermRepository;
import com.bhp.opusb.service.dto.CPaymentTermCriteria;
import com.bhp.opusb.service.dto.CPaymentTermDTO;
import com.bhp.opusb.service.mapper.CPaymentTermMapper;

/**
 * Service for executing complex queries for {@link CPaymentTerm} entities in the database.
 * The main input is a {@link CPaymentTermCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPaymentTermDTO} or a {@link Page} of {@link CPaymentTermDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPaymentTermQueryService extends QueryService<CPaymentTerm> {

    private final Logger log = LoggerFactory.getLogger(CPaymentTermQueryService.class);

    private final CPaymentTermRepository cPaymentTermRepository;

    private final CPaymentTermMapper cPaymentTermMapper;

    public CPaymentTermQueryService(CPaymentTermRepository cPaymentTermRepository, CPaymentTermMapper cPaymentTermMapper) {
        this.cPaymentTermRepository = cPaymentTermRepository;
        this.cPaymentTermMapper = cPaymentTermMapper;
    }

    /**
     * Return a {@link List} of {@link CPaymentTermDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPaymentTermDTO> findByCriteria(CPaymentTermCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CPaymentTerm> specification = createSpecification(criteria);
        return cPaymentTermMapper.toDto(cPaymentTermRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPaymentTermDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPaymentTermDTO> findByCriteria(CPaymentTermCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CPaymentTerm> specification = createSpecification(criteria);
        return cPaymentTermRepository.findAll(specification, page)
            .map(cPaymentTermMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CPaymentTermCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CPaymentTerm> specification = createSpecification(criteria);
        return cPaymentTermRepository.count(specification);
    }

    /**
     * Function to convert {@link CPaymentTermCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CPaymentTerm> createSpecification(CPaymentTermCriteria criteria) {
        Specification<CPaymentTerm> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CPaymentTerm_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CPaymentTerm_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CPaymentTerm_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CPaymentTerm_.description));
            }
            if (criteria.getAfterDelivery() != null) {
                specification = specification.and(buildSpecification(criteria.getAfterDelivery(), CPaymentTerm_.afterDelivery));
            }
            if (criteria.getAsDefault() != null) {
                specification = specification.and(buildSpecification(criteria.getAsDefault(), CPaymentTerm_.asDefault));
            }
            if (criteria.getCalculateBusinessDay() != null) {
                specification = specification.and(buildSpecification(criteria.getCalculateBusinessDay(), CPaymentTerm_.calculateBusinessDay));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), CPaymentTerm_.discount));
            }
            if (criteria.getDiscount2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount2(), CPaymentTerm_.discount2));
            }
            if (criteria.getDiscountDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountDays(), CPaymentTerm_.discountDays));
            }
            if (criteria.getDiscountDays2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountDays2(), CPaymentTerm_.discountDays2));
            }
            if (criteria.getDocumentNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNote(), CPaymentTerm_.documentNote));
            }
            if (criteria.getFixMonthCutOff() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFixMonthCutOff(), CPaymentTerm_.fixMonthCutOff));
            }
            if (criteria.getFixMonthDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFixMonthDay(), CPaymentTerm_.fixMonthDay));
            }
            if (criteria.getFixMonthOffset() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFixMonthOffset(), CPaymentTerm_.fixMonthOffset));
            }
            if (criteria.getFixedDueDate() != null) {
                specification = specification.and(buildSpecification(criteria.getFixedDueDate(), CPaymentTerm_.fixedDueDate));
            }
            if (criteria.getGraceDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGraceDays(), CPaymentTerm_.graceDays));
            }
            if (criteria.getNetDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNetDay(), CPaymentTerm_.netDay));
            }
            if (criteria.getNetDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNetDays(), CPaymentTerm_.netDays));
            }
            if (criteria.getOnNextBusinessDay() != null) {
                specification = specification.and(buildSpecification(criteria.getOnNextBusinessDay(), CPaymentTerm_.onNextBusinessDay));
            }
            if (criteria.getTransactionType() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionType(), CPaymentTerm_.transactionType));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), CPaymentTerm_.valid));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CPaymentTerm_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CPaymentTerm_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CPaymentTerm_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
