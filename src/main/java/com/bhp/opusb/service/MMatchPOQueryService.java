package com.bhp.opusb.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

// for static metamodels
import com.bhp.opusb.domain.ADOrganization_;
import com.bhp.opusb.domain.CCostCenter_;
import com.bhp.opusb.domain.CCurrency_;
import com.bhp.opusb.domain.CLocator_;
import com.bhp.opusb.domain.CProduct_;
import com.bhp.opusb.domain.CTaxCategory_;
import com.bhp.opusb.domain.CTax_;
import com.bhp.opusb.domain.CUnitOfMeasure_;
import com.bhp.opusb.domain.CVendor_;
import com.bhp.opusb.domain.CWarehouse_;
import com.bhp.opusb.domain.MMatchPO;
import com.bhp.opusb.domain.MMatchPO_;
import com.bhp.opusb.repository.MMatchPORepository;
import com.bhp.opusb.service.dto.MMatchPOCriteria;
import com.bhp.opusb.service.dto.MMatchPODTO;
import com.bhp.opusb.service.mapper.MMatchPOMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

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
     * Return a {@link Page} of {@link MMatchPODTO} which are not already invoiced.
     * @param vendorId
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    public Page<MMatchPODTO> findNewReceivedItems(MMatchPOCriteria criteria, Pageable page) {
        log.debug("find new received items by criteria : {}, page: {}", criteria, page);
        return mMatchPORepository.findNonInvoicedMatchPOs(criteria, page)
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
            if (criteria.getDeliveryNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliveryNo(), MMatchPO_.deliveryNo));
            }
            if (criteria.getcDocType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getcDocType(), MMatchPO_.cDocType));
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
            if (criteria.getQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQty(), MMatchPO_.qty));
            }
            if (criteria.getcConversionRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getcConversionRate(), MMatchPO_.cConversionRate));
            }
            if (criteria.getOpenQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOpenQty(), MMatchPO_.openQty));
            }
            if (criteria.getPriceActual() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceActual(), MMatchPO_.priceActual));
            }
            if (criteria.getForeignActual() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getForeignActual(), MMatchPO_.foreignActual));
            }
            if (criteria.getOpenAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOpenAmount(), MMatchPO_.openAmount));
            }
            if (criteria.getOpenForeignAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOpenForeignAmount(), MMatchPO_.openForeignAmount));
            }
            if (criteria.getTotalLines() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalLines(), MMatchPO_.totalLines));
            }
            if (criteria.getForeignTotalLines() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getForeignTotalLines(), MMatchPO_.foreignTotalLines));
            }
            if (criteria.getTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxAmount(), MMatchPO_.taxAmount));
            }
            if (criteria.getForeignTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getForeignTaxAmount(), MMatchPO_.foreignTaxAmount));
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
                specification = specification.and(buildRangeSpecification(criteria.getLineNoPo(), MMatchPO_.lineNoPo));
            }
            if (criteria.getLineNoMr() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNoMr(), MMatchPO_.lineNoMr));
            }
            if (criteria.getTaxable() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxable(), MMatchPO_.taxable));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MMatchPO_.description));
            }
            if (criteria.getmMatchType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getmMatchType(), MMatchPO_.mMatchType));
            }
            if (criteria.getItemDesc1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemDesc1(), MMatchPO_.itemDesc1));
            }
            if (criteria.getItemDesc2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemDesc2(), MMatchPO_.itemDesc2));
            }
            if (criteria.getInvoiced() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiced(), MMatchPO_.invoiced));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MMatchPO_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCCostCenterId(),
                    root -> root.join(MMatchPO_.cCostCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MMatchPO_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getCCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCCurrencyId(),
                    root -> root.join(MMatchPO_.cCurrency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getCTaxCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCTaxCategoryId(),
                    root -> root.join(MMatchPO_.cTaxCategory, JoinType.LEFT).get(CTaxCategory_.id)));
            }
            if (criteria.getCTaxId() != null) {
                specification = specification.and(buildSpecification(criteria.getCTaxId(),
                    root -> root.join(MMatchPO_.cTax, JoinType.LEFT).get(CTax_.id)));
            }
            if (criteria.getCUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getCUomId(),
                    root -> root.join(MMatchPO_.cUom, JoinType.LEFT).get(CUnitOfMeasure_.id)));
            }
            if (criteria.getMProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getMProductId(),
                    root -> root.join(MMatchPO_.mProduct, JoinType.LEFT).get(CProduct_.id)));
            }
            if (criteria.getMWarehouseId() != null) {
                specification = specification.and(buildSpecification(criteria.getMWarehouseId(),
                    root -> root.join(MMatchPO_.mWarehouse, JoinType.LEFT).get(CWarehouse_.id)));
            }
            if (criteria.getMLocatorId() != null) {
                specification = specification.and(buildSpecification(criteria.getMLocatorId(),
                    root -> root.join(MMatchPO_.mLocator, JoinType.LEFT).get(CLocator_.id)));
            }
        }
        return specification;
    }
}
