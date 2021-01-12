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

import com.bhp.opusb.domain.MPurchaseOrderLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPurchaseOrderLineRepository;
import com.bhp.opusb.service.dto.MPurchaseOrderLineCriteria;
import com.bhp.opusb.service.dto.MPurchaseOrderLineDTO;
import com.bhp.opusb.service.mapper.MPurchaseOrderLineMapper;

/**
 * Service for executing complex queries for {@link MPurchaseOrderLine} entities in the database.
 * The main input is a {@link MPurchaseOrderLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPurchaseOrderLineDTO} or a {@link Page} of {@link MPurchaseOrderLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPurchaseOrderLineQueryService extends QueryService<MPurchaseOrderLine> {

    private final Logger log = LoggerFactory.getLogger(MPurchaseOrderLineQueryService.class);

    private final MPurchaseOrderLineRepository mPurchaseOrderLineRepository;

    private final MPurchaseOrderLineMapper mPurchaseOrderLineMapper;

    public MPurchaseOrderLineQueryService(MPurchaseOrderLineRepository mPurchaseOrderLineRepository, MPurchaseOrderLineMapper mPurchaseOrderLineMapper) {
        this.mPurchaseOrderLineRepository = mPurchaseOrderLineRepository;
        this.mPurchaseOrderLineMapper = mPurchaseOrderLineMapper;
    }

    /**
     * Return a {@link List} of {@link MPurchaseOrderLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPurchaseOrderLineDTO> findByCriteria(MPurchaseOrderLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPurchaseOrderLine> specification = createSpecification(criteria);
        return mPurchaseOrderLineMapper.toDto(mPurchaseOrderLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPurchaseOrderLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPurchaseOrderLineDTO> findByCriteria(MPurchaseOrderLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPurchaseOrderLine> specification = createSpecification(criteria);
        return mPurchaseOrderLineRepository.findAll(specification, page)
            .map(mPurchaseOrderLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPurchaseOrderLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPurchaseOrderLine> specification = createSpecification(criteria);
        return mPurchaseOrderLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MPurchaseOrderLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPurchaseOrderLine> createSpecification(MPurchaseOrderLineCriteria criteria) {
        Specification<MPurchaseOrderLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPurchaseOrderLine_.id));
            }
            if (criteria.getTaxId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxId(), MPurchaseOrderLine_.taxId));
            }
            if (criteria.getDocumentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDocumentDate(), MPurchaseOrderLine_.documentDate));
            }
            if (criteria.getDocumentRequired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDocumentRequired(), MPurchaseOrderLine_.documentRequired));
            }
            if (criteria.getOrderAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderAmount(), MPurchaseOrderLine_.orderAmount));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), MPurchaseOrderLine_.quantity));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), MPurchaseOrderLine_.unitPrice));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), MPurchaseOrderLine_.remark));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPurchaseOrderLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPurchaseOrderLine_.active));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPurchaseOrderId(),
                    root -> root.join(MPurchaseOrderLine_.purchaseOrder, JoinType.LEFT).get(MPurchaseOrder_.id)));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(MPurchaseOrderLine_.requisition, JoinType.LEFT).get(MRequisition_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPurchaseOrderLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(MPurchaseOrderLine_.product, JoinType.LEFT).get(CProduct_.id)));
            }
            if (criteria.getWarehouseId() != null) {
                specification = specification.and(buildSpecification(criteria.getWarehouseId(),
                    root -> root.join(MPurchaseOrderLine_.warehouse, JoinType.LEFT).get(CWarehouse_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MPurchaseOrderLine_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
            if (criteria.getUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomId(),
                    root -> root.join(MPurchaseOrderLine_.uom, JoinType.LEFT).get(CUnitOfMeasure_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MPurchaseOrderLine_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
