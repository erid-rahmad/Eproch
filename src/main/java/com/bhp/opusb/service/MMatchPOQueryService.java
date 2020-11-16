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

import com.bhp.opusb.domain.MMatchPO;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MMatchPORepository;
import com.bhp.opusb.service.dto.MMatchPOCriteria;
import com.bhp.opusb.service.dto.MMatchPODTO;
import com.bhp.opusb.service.mapper.MMatchPOMapper;

/**
 * Service for executing complex queries for {@link MMatchPO} entities in the database.
 * The main input is a {@link MMatchPOCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MMatchPODTO} or a {@link Page} of {@link MMatchPODTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MMatchPOQueryService extends QueryService<MMatchPO> {

    private final Logger log = LoggerFactory.getLogger(MMatchPOQueryService.class);

    private final MMatchPORepository mMatchPORepository;

    private final MMatchPOMapper mMatchPOMapper;

    public MMatchPOQueryService(MMatchPORepository mMatchPORepository, MMatchPOMapper mMatchPOMapper) {
        this.mMatchPORepository = mMatchPORepository;
        this.mMatchPOMapper = mMatchPOMapper;
    }

    /**
     * Return a {@link List} of {@link MMatchPODTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MMatchPODTO> findByCriteria(MMatchPOCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MMatchPO> specification = createSpecification(criteria);
        return mMatchPOMapper.toDto(mMatchPORepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MMatchPODTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MMatchPODTO> findByCriteria(MMatchPOCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MMatchPO> specification = createSpecification(criteria);
        return mMatchPORepository.findAll(specification, page)
            .map(mMatchPOMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MMatchPOCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MMatchPO> specification = createSpecification(criteria);
        return mMatchPORepository.count(specification);
    }

    /**
     * Function to convert {@link MMatchPOCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MMatchPO> createSpecification(MMatchPOCriteria criteria) {
        Specification<MMatchPO> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MMatchPO_.id));
            }
            if (criteria.getcDocType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcDocType(), MMatchPO_.cDocType));
            }
            if (criteria.getcVendor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcVendor(), MMatchPO_.cVendor));
            }
            if (criteria.getcElement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcElement(), MMatchPO_.cElement));
            }
            if (criteria.getcCostCenter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcCostCenter(), MMatchPO_.cCostCenter));
            }
            if (criteria.getPoNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPoNo(), MMatchPO_.poNo));
            }
            if (criteria.getPoDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPoDate(), MMatchPO_.poDate));
            }
            if (criteria.getReceiptNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceiptNo(), MMatchPO_.receiptNo));
            }
            if (criteria.getReceiptDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceiptDate(), MMatchPO_.receiptDate));
            }
            if (criteria.getDeliveryNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliveryNo(), MMatchPO_.deliveryNo));
            }
            if (criteria.getmProductCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getmProductCode(), MMatchPO_.mProductCode));
            }
            if (criteria.getmProductName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getmProductName(), MMatchPO_.mProductName));
            }
            if (criteria.getmProductDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getmProductDesc(), MMatchPO_.mProductDesc));
            }
            if (criteria.getcUOM() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcUOM(), MMatchPO_.cUOM));
            }
            if (criteria.getQty() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQty(), MMatchPO_.qty));
            }
            if (criteria.getcCurrency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcCurrency(), MMatchPO_.cCurrency));
            }
            if (criteria.getcConversionRate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcConversionRate(), MMatchPO_.cConversionRate));
            }
            if (criteria.getOpenQty() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOpenQty(), MMatchPO_.openQty));
            }
            if (criteria.getPriceActual() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriceActual(), MMatchPO_.priceActual));
            }
            if (criteria.getForeignActual() != null) {
                specification = specification.and(buildStringSpecification(criteria.getForeignActual(), MMatchPO_.foreignActual));
            }
            if (criteria.getOpenAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOpenAmount(), MMatchPO_.openAmount));
            }
            if (criteria.getOpenForeignAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOpenForeignAmount(), MMatchPO_.openForeignAmount));
            }
            if (criteria.getTotalLines() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalLines(), MMatchPO_.totalLines));
            }
            if (criteria.getForeignTotalLines() != null) {
                specification = specification.and(buildStringSpecification(criteria.getForeignTotalLines(), MMatchPO_.foreignTotalLines));
            }
            if (criteria.getcTax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcTax(), MMatchPO_.cTax));
            }
            if (criteria.getTaxAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxAmount(), MMatchPO_.taxAmount));
            }
            if (criteria.getForeignTaxAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getForeignTaxAmount(), MMatchPO_.foreignTaxAmount));
            }
            if (criteria.getmLocator() != null) {
                specification = specification.and(buildStringSpecification(criteria.getmLocator(), MMatchPO_.mLocator));
            }
            if (criteria.getAdOrganization() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdOrganization(), MMatchPO_.adOrganization));
            }
            if (criteria.getDateAccount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateAccount(), MMatchPO_.dateAccount));
            }
            if (criteria.getcDocTypeMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcDocTypeMr(), MMatchPO_.cDocTypeMr));
            }
            if (criteria.getOrderSuffix() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderSuffix(), MMatchPO_.orderSuffix));
            }
            if (criteria.getLineNoPo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLineNoPo(), MMatchPO_.lineNoPo));
            }
            if (criteria.getLineNoMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLineNoMr(), MMatchPO_.lineNoMr));
            }
            if (criteria.getIsTaxable() != null) {
                specification = specification.and(buildSpecification(criteria.getIsTaxable(), MMatchPO_.isTaxable));
            }
            if (criteria.getcTaxCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcTaxCode(), MMatchPO_.cTaxCode));
            }
            if (criteria.getcTaxName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcTaxName(), MMatchPO_.cTaxName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MMatchPO_.description));
            }
            if (criteria.getmMatchType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getmMatchType(), MMatchPO_.mMatchType));
            }
            if (criteria.getmWarehouse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getmWarehouse(), MMatchPO_.mWarehouse));
            }
        }
        return specification;
    }
}
