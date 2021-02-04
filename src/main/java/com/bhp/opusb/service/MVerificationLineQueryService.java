package com.bhp.opusb.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

// for static metamodels
import com.bhp.opusb.domain.ADOrganization_;
import com.bhp.opusb.domain.CCostCenter_;
import com.bhp.opusb.domain.CCurrency_;
import com.bhp.opusb.domain.CProduct_;
import com.bhp.opusb.domain.CTaxCategory_;
import com.bhp.opusb.domain.CTax_;
import com.bhp.opusb.domain.CUnitOfMeasure_;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.domain.MVerificationLine;
import com.bhp.opusb.domain.MVerificationLine_;
import com.bhp.opusb.domain.MVerification_;
import com.bhp.opusb.repository.MVerificationLineRepository;
import com.bhp.opusb.service.dto.MVerificationLineCriteria;
import com.bhp.opusb.service.dto.MVerificationLineDTO;
import com.bhp.opusb.service.mapper.MVerificationLineMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.LongFilter;

/**
 * Service for executing complex queries for {@link MVerificationLine} entities in the database.
 * The main input is a {@link MVerificationLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVerificationLineDTO} or a {@link Page} of {@link MVerificationLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVerificationLineQueryService extends QueryService<MVerificationLine> {

    private final Logger log = LoggerFactory.getLogger(MVerificationLineQueryService.class);

    private final MVerificationLineRepository mVerificationLineRepository;

    private final MVerificationLineMapper mVerificationLineMapper;

    public MVerificationLineQueryService(MVerificationLineRepository mVerificationLineRepository, MVerificationLineMapper mVerificationLineMapper) {
        this.mVerificationLineRepository = mVerificationLineRepository;
        this.mVerificationLineMapper = mVerificationLineMapper;
    }

    /**
     * Return a {@link List} of {@link MVerificationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVerificationLineDTO> findByCriteria(MVerificationLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVerificationLine> specification = createSpecification(criteria);
        return mVerificationLineMapper.toDto(mVerificationLineRepository.findAll(specification));
    }

    /**
     * Return a {@link List} of {@link MVerificationLineDTO} of a specific MVerificationDTO.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVerificationLineDTO> findByHeader(MVerification mVerification) {
        log.debug("find by header : {}", mVerification.getId());
        MVerificationLineCriteria criteria = new MVerificationLineCriteria();
        LongFilter idFilter = new LongFilter();

        idFilter.setEquals(mVerification.getId());
        criteria.setVerificationId(idFilter);
        return findByCriteria(criteria);
    }

    /**
     * Return a {@link Page} of {@link MVerificationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVerificationLineDTO> findByCriteria(MVerificationLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVerificationLine> specification = createSpecification(criteria);
        return mVerificationLineRepository.findAll(specification, page)
            .map(mVerificationLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVerificationLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVerificationLine> specification = createSpecification(criteria);
        return mVerificationLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MVerificationLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVerificationLine> createSpecification(MVerificationLineCriteria criteria) {
        Specification<MVerificationLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVerificationLine_.id));
            }
            if (criteria.getVerificationNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVerificationNo(), MVerificationLine_.verificationNo));
            }
            if (criteria.getMatchType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatchType(), MVerificationLine_.matchType));
            }
            if (criteria.getPoNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPoNo(), MVerificationLine_.poNo));
            }
            if (criteria.getReceiveNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceiveNo(), MVerificationLine_.receiveNo));
            }
            if (criteria.getDeliveryNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliveryNo(), MVerificationLine_.deliveryNo));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MVerificationLine_.description));
            }
            if (criteria.getOrderSuffix() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderSuffix(), MVerificationLine_.orderSuffix));
            }
            if (criteria.getQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQty(), MVerificationLine_.qty));
            }
            if (criteria.getPriceActual() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceActual(), MVerificationLine_.priceActual));
            }
            if (criteria.getForeignActual() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getForeignActual(), MVerificationLine_.foreignActual));
            }
            if (criteria.getTotalLines() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalLines(), MVerificationLine_.totalLines));
            }
            if (criteria.getForeignTotalLines() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getForeignTotalLines(), MVerificationLine_.foreignTotalLines));
            }
            if (criteria.getTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxAmount(), MVerificationLine_.taxAmount));
            }
            if (criteria.getForeignTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getForeignTaxAmount(), MVerificationLine_.foreignTaxAmount));
            }
            if (criteria.getLineNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNo(), MVerificationLine_.lineNo));
            }
            if (criteria.getLineNoPo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNoPo(), MVerificationLine_.lineNoPo));
            }
            if (criteria.getLineNoMr() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNoMr(), MVerificationLine_.lineNoMr));
            }
            if (criteria.getItemDesc1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemDesc1(), MVerificationLine_.itemDesc1));
            }
            if (criteria.getItemDesc2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemDesc2(), MVerificationLine_.itemDesc2));
            }
            if (criteria.getConversionRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConversionRate(), MVerificationLine_.conversionRate));
            }
            if (criteria.getReceiveDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceiveDate(), MVerificationLine_.receiveDate));
            }
            if (criteria.getPayStat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPayStat(), MVerificationLine_.payStat));
            }
            if (criteria.getTaxable() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxable(), MVerificationLine_.taxable));
            }
            if (criteria.getcDocType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcDocType(), MVerificationLine_.cDocType));
            }
            if (criteria.getcDocTypeMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcDocTypeMr(), MVerificationLine_.cDocTypeMr));
            }
            if (criteria.getReceiptReversed() != null) {
                specification = specification.and(buildSpecification(criteria.getReceiptReversed(), MVerificationLine_.receiptReversed));
            }
            if (criteria.getApReversed() != null) {
                specification = specification.and(buildSpecification(criteria.getApReversed(), MVerificationLine_.apReversed));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVerificationLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVerificationLine_.active));
            }
            if (criteria.getVerificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getVerificationId(),
                    root -> root.join(MVerificationLine_.verification, JoinType.LEFT).get(MVerification_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVerificationLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(MVerificationLine_.product, JoinType.LEFT).get(CProduct_.id)));
            }
            if (criteria.getUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomId(),
                    root -> root.join(MVerificationLine_.uom, JoinType.LEFT).get(CUnitOfMeasure_.id)));
            }
            if (criteria.getCCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCCostCenterId(),
                    root -> root.join(MVerificationLine_.cCostCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
            if (criteria.getCCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCCurrencyId(),
                    root -> root.join(MVerificationLine_.cCurrency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getCTaxCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCTaxCategoryId(),
                    root -> root.join(MVerificationLine_.cTaxCategory, JoinType.LEFT).get(CTaxCategory_.id)));
            }
            if (criteria.getCTaxId() != null) {
                specification = specification.and(buildSpecification(criteria.getCTaxId(),
                    root -> root.join(MVerificationLine_.cTax, JoinType.LEFT).get(CTax_.id)));
            }
        }
        return specification;
    }
}
