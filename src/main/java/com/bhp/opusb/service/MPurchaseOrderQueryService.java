package com.bhp.opusb.service;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.JoinType;

import com.bhp.opusb.util.MapperJSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bhp.opusb.domain.MPurchaseOrder;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPurchaseOrderRepository;
import com.bhp.opusb.service.dto.MPurchaseOrderCriteria;
import com.bhp.opusb.service.dto.MPurchaseOrderDTO;
import com.bhp.opusb.service.mapper.MPurchaseOrderMapper;

/**
 * Service for executing complex queries for {@link MPurchaseOrder} entities in the database.
 * The main input is a {@link MPurchaseOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPurchaseOrderDTO} or a {@link Page} of {@link MPurchaseOrderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPurchaseOrderQueryService extends QueryService<MPurchaseOrder> {

    private final Logger log = LoggerFactory.getLogger(MPurchaseOrderQueryService.class);

    private final MPurchaseOrderRepository mPurchaseOrderRepository;

    private final MPurchaseOrderMapper mPurchaseOrderMapper;

    public MPurchaseOrderQueryService(MPurchaseOrderRepository mPurchaseOrderRepository, MPurchaseOrderMapper mPurchaseOrderMapper) {
        this.mPurchaseOrderRepository = mPurchaseOrderRepository;
        this.mPurchaseOrderMapper = mPurchaseOrderMapper;
    }

    /**
     * Return a {@link List} of {@link MPurchaseOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPurchaseOrderDTO> findByCriteria(MPurchaseOrderCriteria criteria) {
        log.debug("find by criteria 11 : {}", criteria);
        final Specification<MPurchaseOrder> specification = createSpecification(criteria);
        List<MPurchaseOrderDTO> pages ;
        pages = mPurchaseOrderMapper.toDto(mPurchaseOrderRepository.findAll(specification));
//        return mPurchaseOrderMapper.toDto(mPurchaseOrderRepository.findAll(specification));
        log.debug("this pages {}", MapperJSONUtil.prettyLog(pages));
        return pages;
    }

    /**
     * Return a {@link Page} of {@link MPurchaseOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPurchaseOrderDTO> findByCriteria(MPurchaseOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        log.debug("this criteria 1");
        final Specification<MPurchaseOrder> specification = createSpecification(criteria);
        Page<MPurchaseOrderDTO> pages;

        pages = mPurchaseOrderRepository.findAll(specification, page)
            .map(mPurchaseOrderMapper::toDto);

//        return mPurchaseOrderRepository.findAll(specification, page)
//            .map(mPurchaseOrderMapper::toDto);

        log.info("this maper {}",MapperJSONUtil.prettyLog(pages));
        return pages;
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPurchaseOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPurchaseOrder> specification = createSpecification(criteria);
        return mPurchaseOrderRepository.count(specification);
    }

    /**
     * Function to convert {@link MPurchaseOrderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPurchaseOrder> createSpecification(MPurchaseOrderCriteria criteria) {
        Specification<MPurchaseOrder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPurchaseOrder_.id));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MPurchaseOrder_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MPurchaseOrder_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MPurchaseOrder_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MPurchaseOrder_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MPurchaseOrder_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MPurchaseOrder_.processed));
            }
            if (criteria.getGrandTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrandTotal(), MPurchaseOrder_.grandTotal));
            }
            if (criteria.getTax() != null) {
                specification = specification.and(buildSpecification(criteria.getTax(), MPurchaseOrder_.tax));
            }
            if (criteria.getDatePromised() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePromised(), MPurchaseOrder_.datePromised));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MPurchaseOrder_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPurchaseOrder_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPurchaseOrder_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPurchaseOrder_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeId(),
                    root -> root.join(MPurchaseOrder_.documentType, JoinType.LEFT).get(CDocumentType_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MPurchaseOrder_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(MPurchaseOrder_.currency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getWarehouseId() != null) {
                specification = specification.and(buildSpecification(criteria.getWarehouseId(),
                    root -> root.join(MPurchaseOrder_.warehouse, JoinType.LEFT).get(CWarehouse_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MPurchaseOrder_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
        }
        return specification;
    }
}
