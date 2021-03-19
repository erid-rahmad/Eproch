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

import com.bhp.opusb.domain.MRequisitionLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MRequisitionLineRepository;
import com.bhp.opusb.service.dto.MRequisitionLineCriteria;
import com.bhp.opusb.service.dto.MRequisitionLineDTO;
import com.bhp.opusb.service.mapper.MRequisitionLineMapper;

/**
 * Service for executing complex queries for {@link MRequisitionLine} entities in the database.
 * The main input is a {@link MRequisitionLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MRequisitionLineDTO} or a {@link Page} of {@link MRequisitionLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MRequisitionLineQueryService extends QueryService<MRequisitionLine> {

    private final Logger log = LoggerFactory.getLogger(MRequisitionLineQueryService.class);

    private final MRequisitionLineRepository mRequisitionLineRepository;

    private final MRequisitionLineMapper mRequisitionLineMapper;

    public MRequisitionLineQueryService(MRequisitionLineRepository mRequisitionLineRepository, MRequisitionLineMapper mRequisitionLineMapper) {
        this.mRequisitionLineRepository = mRequisitionLineRepository;
        this.mRequisitionLineMapper = mRequisitionLineMapper;
    }

    /**
     * Return a {@link List} of {@link MRequisitionLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MRequisitionLineDTO> findByCriteria(MRequisitionLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MRequisitionLine> specification = createSpecification(criteria);
        return mRequisitionLineMapper.toDto(mRequisitionLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MRequisitionLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MRequisitionLineDTO> findByCriteria(MRequisitionLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MRequisitionLine> specification = createSpecification(criteria);
        return mRequisitionLineRepository.findAll(specification, page)
            .map(mRequisitionLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MRequisitionLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MRequisitionLine> specification = createSpecification(criteria);
        return mRequisitionLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MRequisitionLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MRequisitionLine> createSpecification(MRequisitionLineCriteria criteria) {
        Specification<MRequisitionLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MRequisitionLine_.id));
            }
            if (criteria.getLineNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNo(), MRequisitionLine_.lineNo));
            }
            if (criteria.getDocumentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDocumentDate(), MRequisitionLine_.documentDate));
            }
            if (criteria.getDatePromised() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePromised(), MRequisitionLine_.datePromised));
            }
            if (criteria.getDateRequired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRequired(), MRequisitionLine_.dateRequired));
            }
            if (criteria.getRequisitionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequisitionAmount(), MRequisitionLine_.requisitionAmount));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), MRequisitionLine_.quantity));
            }
            if (criteria.getQuantityOrdered() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantityOrdered(), MRequisitionLine_.quantityOrdered));
            }
            if (criteria.getQuantityBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantityBalance(), MRequisitionLine_.quantityBalance));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), MRequisitionLine_.unitPrice));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), MRequisitionLine_.remark));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MRequisitionLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MRequisitionLine_.active));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(MRequisitionLine_.requisition, JoinType.INNER).get(MRequisition_.id)));
            }
            if (criteria.getRequisitionNo() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionNo(),
                    root -> root.join(MRequisitionLine_.requisition, JoinType.INNER).get(MRequisition_.documentNo)));
            }
            if (criteria.getRequisitionApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionApproved(),
                    root -> root.join(MRequisitionLine_.requisition, JoinType.INNER).get(MRequisition_.approved)));
            }
            if (criteria.getRequisitionProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionProcessed(),
                    root -> root.join(MRequisitionLine_.requisition, JoinType.INNER).get(MRequisition_.processed)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MRequisitionLine_.adOrganization, JoinType.INNER).get(ADOrganization_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(MRequisitionLine_.product, JoinType.INNER).get(CProduct_.id)));
            }
            if (criteria.getWarehouseId() != null) {
                specification = specification.and(buildSpecification(criteria.getWarehouseId(),
                    root -> root.join(MRequisitionLine_.warehouse, JoinType.INNER).get(CWarehouse_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MRequisitionLine_.costCenter, JoinType.INNER).get(CCostCenter_.id)));
            }
            if (criteria.getUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomId(),
                    root -> root.join(MRequisitionLine_.uom, JoinType.INNER).get(CUnitOfMeasure_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MRequisitionLine_.vendor, JoinType.INNER).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
